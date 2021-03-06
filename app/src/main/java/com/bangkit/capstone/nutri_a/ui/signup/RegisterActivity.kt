package com.bangkit.capstone.nutri_a.ui.signup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivityRegisterBinding
import com.bangkit.capstone.nutri_a.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var male = "Male"
    private var female = "Female"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDP(calendar)
        }

        binding.btnDpBirth.setOnClickListener {
            DatePickerDialog(this, datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.etEmailValue.addTextChangedListener(register)
        binding.etPasswordValue.addTextChangedListener(register)
        binding.etFullNameValue.addTextChangedListener(register)
        binding.btnDpBirth.addTextChangedListener(register)
        binding.etHeightValue.addTextChangedListener(register)
        binding.etWeightValue.addTextChangedListener(register)
        binding.etExerciseValue.addTextChangedListener(register)


        binding.btnRegister.setOnClickListener {
            val inputName = binding.etFullNameValue.text.toString()
            val inputEmail = binding.etEmailValue.text.toString()
            val inputPassword = binding.etPasswordValue.text.toString()

            val inputBirtOfDate = binding.btnDpBirth.text.toString()

            var inputGender = true
            val selectedId: Int = binding.rbGender.checkedRadioButtonId
            val radioButton = findViewById<View>(selectedId) as RadioButton
            val selectedRB = radioButton.text.toString()
            if (selectedRB == male) {
                inputGender = true
            } else if (selectedRB == female) {
                inputGender = false
            }
            val weight = binding.etWeightValue.text.toString()
            val inputWeight = weight.toInt()
            val height = binding.etHeightValue.text.toString()
            val inputHeight = height.toInt()
            val timesOfExercise = binding.etExerciseValue.text.toString()
            val inputTimesOfExercise = timesOfExercise.toInt()

            createAccount(inputEmail, inputPassword, inputName, inputBirtOfDate,
                inputGender,inputWeight, inputHeight, inputTimesOfExercise )

        }

    }

    private val register: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val name: String = binding.etFullNameValue.text.toString().trim()
            val email: String = binding.etEmailValue.text.toString().trim()
            val password: String = binding.etPasswordValue.text.toString().trim()
            val birthOfDate: String = binding.btnDpBirth.text.toString().trim()
            val weight: String = binding.etWeightValue.text.toString().trim()
            val height: String = binding.etHeightValue.text.toString().trim()
            val exercise: String = binding.etExerciseValue.text.toString().trim()

            binding.btnRegister.isEnabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && birthOfDate.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty() && exercise.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun createAccount(email: String, password: String, name: String, birtOfDate: String, gender: Boolean, weight: Int, height: Int, timesOfExercise: Int) {

        showLoading(true)

        val client = ApiConfig.getApiService().createAccount(email, password, name, birtOfDate, gender, weight , height, timesOfExercise)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                Log.d(TAG, "onResponse: $responseBody")
                if(response.isSuccessful && responseBody?.status == "success") {
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    Toast.makeText(this@RegisterActivity, getString(R.string.register_fail), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure2: ${t.message}")
                Toast.makeText(this@RegisterActivity, getString(R.string.register_fail), Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun updateLabelDP(calendar: Calendar) {
        val format = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(format, Locale.UK)
        binding.btnDpBirth.text = sdf.format(calendar.time)
    }


    companion object {
        private const val TAG = "RegisterActivity"
    }

}