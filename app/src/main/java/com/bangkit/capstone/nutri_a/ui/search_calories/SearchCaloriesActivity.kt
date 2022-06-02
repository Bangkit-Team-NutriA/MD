package com.bangkit.capstone.nutri_a.ui.search_calories

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivitySearchCaloriesBinding
import com.bangkit.capstone.nutri_a.response.SearchCaloriesResponse
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.utils.reduceFileImage
import com.bangkit.capstone.nutri_a.utils.rotateBitmap
import com.bangkit.capstone.nutri_a.utils.uriToFile
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SearchCaloriesActivity : AppCompatActivity() {
    private var getFile: File? = null

    private lateinit var binding: ActivitySearchCaloriesBinding

    private lateinit var viewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchCaloriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()


        supportActionBar?.title = "Find out your food calories!"


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnGallery.setOnClickListener {
            startGallery()
        }

        binding.btnUpload.setOnClickListener {
            uploadImage()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }




    private fun uploadImage() {
        showLoading(true)

        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            viewModel.getUser().observe(this) {
                if (it != null) {
                    val client = ApiConfig.getApiService()
                        .searchCalories( "Bearer " + it.token, imageMultipart)
                    client.enqueue(object : Callback<SearchCaloriesResponse> {
                        override fun onResponse(
                            call: Call<SearchCaloriesResponse>,
                            response: Response<SearchCaloriesResponse>
                        ) {
                            showLoading(false)
                            val responseBody = response.body()
                            val name = responseBody?.name;
                            Log.d(TAG, "onResponseNama: $name")
                            if (response.isSuccessful && responseBody?.status == "success") {
                                Toast.makeText(
                                    this@SearchCaloriesActivity,
                                    getString(R.string.upload_success),
                                    Toast.LENGTH_SHORT
                                ).show()

                                Toast.makeText(
                                    this@SearchCaloriesActivity,
                                    getString(R.string.success_predict_picture),
                                    Toast.LENGTH_SHORT
                                ).show()

                                val dataFood = responseBody.informationCalories
                                val intent = Intent(this@SearchCaloriesActivity, ResultCaloriesActivity::class.java)

                                intent.putExtra("nameFood", name)

                                val gson = Gson()
                                intent.putExtra("dataFood", gson.toJson(dataFood))

                                intent.putExtra("imageFood", getFile)
                                startActivity(intent)
                                finish()
                            } else if (responseBody?.status == "fail"){
                                Toast.makeText(
                                    this@SearchCaloriesActivity,
                                    getString(R.string.cannot_predict_picture),
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent =
                                    Intent(this@SearchCaloriesActivity, ResultCaloriesActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.e(TAG, "onFailure1: ${response.message()}")
                                Toast.makeText(
                                    this@SearchCaloriesActivity,
                                    getString(R.string.upload_failed),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<SearchCaloriesResponse>, t: Throwable) {
                            showLoading(false)
                            Log.e(TAG, "onFailure2: ${t.message}")
                            Toast.makeText(
                                this@SearchCaloriesActivity,
                                getString(R.string.upload_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }

        } else {
            showLoading(false)
            Toast.makeText(
                this@SearchCaloriesActivity,
                getString(R.string.upload_warning),
                Toast.LENGTH_SHORT
            ).show()
        }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                    .show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = myFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            binding.imgPreview.setImageBitmap(result)

        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@SearchCaloriesActivity)
            getFile = myFile
            binding.imgPreview.setImageURI(selectedImg)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {

        const val TAG = "SearchCaloriesActivity"
        const val CAMERA_X_RESULT = 200

        internal val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        internal const val REQUEST_CODE_PERMISSIONS = 10
    }
}