package com.bangkit.capstone.nutri_a.ui.profile


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivityEditProfileBinding
import com.bangkit.capstone.nutri_a.response.CalculatorResponse
import com.bangkit.capstone.nutri_a.response.DataUser
import com.bangkit.capstone.nutri_a.response.EditUserResponse
import com.bangkit.capstone.nutri_a.response.GetUserResponse
import com.bangkit.capstone.nutri_a.ui.home.HomeActivity
import com.bangkit.capstone.nutri_a.ui.home.NutritionCalculateActivity
import com.bangkit.capstone.nutri_a.ui.search_calories.ResultCaloriesActivity
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var viewModel: SharedViewModel

    private var male = R.string.male.toString()
    private var female = R.string.female.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()

        getDataUser()

        binding.etName.addTextChangedListener(editProfile)
        binding.etWeight.addTextChangedListener(editProfile)
        binding.etHeight.addTextChangedListener(editProfile)

        binding.btnSave.setOnClickListener {

            val inputName = binding.etName.text.toString()


            var inputGender = true

            val selectedId: Int = binding.rbGender.checkedRadioButtonId
            val radioButton = findViewById<View>(selectedId) as RadioButton
            val selectedRB = radioButton.text.toString()

            if (selectedRB ==  male) {
                inputGender = true
            } else if (selectedRB == female) {
                inputGender = false
            }

            val weight = binding.etWeight.text.toString()
            val inputWeight = weight.toInt()
            val height = binding.etHeight.text.toString()
            val inputHeight = height.toInt()

            editUser(inputName, inputGender, inputWeight, inputHeight)

        }

    }

    private fun editUser(name: String, gender: Boolean ,weight: Int, height: Int) {
        showLoading(true)
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().editUser(
                    "Bearer " + it.token,
                    name,
                    gender,
                    weight,
                    height
                )
                client.enqueue(object : Callback<EditUserResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<EditUserResponse>,
                        response: Response<EditUserResponse>
                    ) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@EditProfileActivity,
                                getString(R.string.edit_profile_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@EditProfileActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            showLoading(false)
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(
                                this@EditProfileActivity,
                                getString(R.string.edit_profile_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<EditUserResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@EditProfileActivity,
                            getString(R.string.edit_profile_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private fun getDataUser() {
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getUser("Bearer " + it.token)
                client.enqueue(object : Callback<GetUserResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<GetUserResponse>,
                        response: Response<GetUserResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {

                            val data = responseBody.data

                            if (data != null) {
                                filledData(data)
                            }


                        } else {
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(
                                this@EditProfileActivity,
                                getString(R.string.failed_get_data_user),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        Log.e(HomeActivity.TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@EditProfileActivity,
                            getString(R.string.failed_get_data_user),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private fun filledData(dataUser: DataUser) {
        val name = dataUser.nama.toString()
        val height = dataUser.tinggi.toString()
        val weight = dataUser.berat.toString()

        binding.etName.hint = name
        binding.etWeight.hint = weight
        binding.etHeight.hint = height
    }

    private val editProfile: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val nameInput: String = binding.etName.text.toString().trim()
            val weightInput: String = binding.etWeight.text.toString().trim()
            val heightInput: String = binding.etHeight.text.toString().trim()
            binding.btnSave.isEnabled =
                nameInput.isNotEmpty() && weightInput.isNotEmpty() && heightInput.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val TAG = "EditProfileActivity"
    }


}