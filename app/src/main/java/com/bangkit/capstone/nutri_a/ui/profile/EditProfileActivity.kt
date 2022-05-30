package com.bangkit.capstone.nutri_a.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.utils.uriToFile
import com.bangkit.capstone.nutri_a.databinding.ActivityEditProfileBinding

import java.io.File

class EditProfileActivity : AppCompatActivity() {


    private var getFile: File? = null

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                EditProfileActivity.REQUIRED_PERMISSIONS,
                EditProfileActivity.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.iconPhoto.setOnClickListener {
            startGallery()
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == EditProfileActivity.REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_LONG)
                    .show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = EditProfileActivity.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@EditProfileActivity)
            getFile = myFile
            binding.imagePhoto.setImageURI(selectedImg)
        }
    }

    companion object {

        internal val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        internal const val REQUEST_CODE_PERMISSIONS = 10
    }


}