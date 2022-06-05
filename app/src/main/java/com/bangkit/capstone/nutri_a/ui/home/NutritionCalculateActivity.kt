package com.bangkit.capstone.nutri_a.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivityNutritionCalculateBinding
import com.bangkit.capstone.nutri_a.response.CalculatorResponse
import com.bangkit.capstone.nutri_a.ui.signup.LoginActivity
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NutritionCalculateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNutritionCalculateBinding

    private lateinit var viewModel: SharedViewModel

    private var male = "Male"
    private var female = "Female"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNutritionCalculateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setupViewModel()

        binding.etAge.addTextChangedListener(calculate)
        binding.etHeight.addTextChangedListener(calculate)
        binding.etWeight.addTextChangedListener(calculate)
        binding.etExercise.addTextChangedListener(calculate)


        binding.btnCalculate.setOnClickListener {
            var sex = true
            val selectedId: Int = binding.rbGender.checkedRadioButtonId
            val radioButton = findViewById<View>(selectedId) as RadioButton
            val selectedRB = radioButton.text.toString()

            if (selectedRB == male) {
                sex = true
            } else if (selectedRB == female) {
                sex = false
            }

            val inputWeight = binding.etWeight.text.toString()
            val weight = inputWeight.toInt()
            val inputHeight = binding.etHeight.text.toString()
            val height = inputHeight.toInt()
            val inputTimesOfExercise = binding.etExercise.text.toString()
            val timesOfExercise = inputTimesOfExercise.toInt()
            val inputAge = binding.etAge.text.toString()
            val age = inputAge.toInt()

            calculate(sex, weight, height, timesOfExercise, age)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    private fun calculate(sex: Boolean, weight: Int, height: Int, timesOfExercise: Int, age: Int) {
        showLoading(true)
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().calculateNutrition(
                    "Bearer " + it.token,
                    sex,
                    weight,
                    height,
                    timesOfExercise,
                    age
                )
                client.enqueue(object : Callback<CalculatorResponse> {
                    @SuppressLint("LongLogTag", "SetTextI18n")
                    override fun onResponse(
                        call: Call<CalculatorResponse>,
                        response: Response<CalculatorResponse>
                    ) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@NutritionCalculateActivity,
                                getString(R.string.calculate_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.nutritionLayout.visibility = View.VISIBLE
                            val dataNutrition = responseBody.information

                            val rawCalories = dataNutrition?.calories
                            val calories = String.format("%.2f", rawCalories)
                            binding.tvCaloriesValue.text = "$calories kcl"

                            val rawCarbohydrate = dataNutrition?.carbo
                            val carbohydrate = String.format("%.2f", rawCarbohydrate)
                            binding.tvCarbohydrateValue.text = "$carbohydrate gr"

                            val rawProtein = dataNutrition?.protein
                            val protein = String.format("%.2f", rawProtein)
                            binding.tvProteinValue.text = "$protein gr"

                            val rawFat = dataNutrition?.fat
                            val fat = String.format("%.2f", rawFat)
                            binding.tvFatValue.text = "$fat gr"

                        } else {
                            showLoading(false)
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(
                                this@NutritionCalculateActivity,
                                getString(R.string.calculate_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<CalculatorResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@NutritionCalculateActivity,
                            getString(R.string.calculate_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private val calculate: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val age: String = binding.etAge.text.toString().trim()
            val weight: String = binding.etWeight.text.toString().trim()
            val height: String = binding.etHeight.text.toString().trim()
            val exercise: String = binding.etExercise.text.toString().trim()

            binding.btnCalculate.isEnabled = age.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty() && exercise.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "NutritionCalculate"
    }


}