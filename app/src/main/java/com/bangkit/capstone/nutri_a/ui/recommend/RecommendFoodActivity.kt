package com.bangkit.capstone.nutri_a.ui.recommend

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
import com.bangkit.capstone.nutri_a.databinding.ActivityRecommendFoodBinding
import com.bangkit.capstone.nutri_a.response.InformationParam
import com.bangkit.capstone.nutri_a.response.RecommendFoodResponse
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RecommendFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendFoodBinding
    private lateinit var viewModel: SharedViewModel

    private var male = "Male"
    private var female = "Female"

    private lateinit var dataFood: InformationParam

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        binding.etAge.addTextChangedListener(recommendParam)
        binding.etHeight.addTextChangedListener(recommendParam)
        binding.etWeight.addTextChangedListener(recommendParam)
        binding.etExercise.addTextChangedListener(recommendParam)

        binding.btnRecommend.setOnClickListener {

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
            val age = binding.etAge.text.toString()
            val inputAge = age.toInt()
            val exercise = binding.etExercise.text.toString()
            val inputExercise = exercise.toInt()

            getRecommendParam(inputGender, inputWeight, inputHeight, inputAge, inputExercise)
        }

        binding.btnMyData.setOnClickListener {
            getRecommendNonParam()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    private val recommendParam: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val age: String = binding.etAge.text.toString().trim()
            val weight: String = binding.etWeight.text.toString().trim()
            val height: String = binding.etHeight.text.toString().trim()
            val exercise: String = binding.etExercise.text.toString().trim()

            binding.btnRecommend.isEnabled = age.isNotEmpty() && weight.isNotEmpty() && height.isNotEmpty() && exercise.isNotEmpty()
        }

        override fun afterTextChanged(s: Editable) {}
    }


    private fun getRecommendParam(sex: Boolean, weight: Int, height: Int, timesOfExercise: Int, age: Int){
        showLoading(true)
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getRecommendParam(
                    "Bearer " + it.token,
                    sex,
                    weight,
                    height,
                    timesOfExercise,
                    age
                )
                client.enqueue(object : Callback<RecommendFoodResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<RecommendFoodResponse>,
                        response: Response<RecommendFoodResponse>
                    ) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@RecommendFoodActivity,
                                getString(R.string.success_calculate_food),
                                Toast.LENGTH_SHORT
                            ).show()


                            dataFood = responseBody.information!!
                            val intent = Intent(this@RecommendFoodActivity, ResultRecommendFoodActivity::class.java)

                            val gson = Gson()
                            intent.putExtra("dataFood", gson.toJson(dataFood))

                            startActivity(intent)
                            finish()

                        } else {
                            showLoading(false)
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            val message = response.message().toString()
                            Toast.makeText(
                                this@RecommendFoodActivity, message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<RecommendFoodResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@RecommendFoodActivity,
                            getString(R.string.failed_calculate_food),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }

    }

    private fun getRecommendNonParam(){
        showLoading(true)
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getRecommendNonParam(
                    "Bearer " + it.token
                )
                client.enqueue(object : Callback<RecommendFoodResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<RecommendFoodResponse>,
                        response: Response<RecommendFoodResponse>
                    ) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@RecommendFoodActivity,
                                getString(R.string.success_calculate_food),
                                Toast.LENGTH_SHORT
                            ).show()


                            dataFood = responseBody.information!!
                            val intent = Intent(this@RecommendFoodActivity, ResultRecommendFoodActivity::class.java)

                            val gson = Gson()
                            intent.putExtra("dataFood", gson.toJson(dataFood))

                            startActivity(intent)
                            finish()


                        } else {
                            showLoading(false)
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            val message = response.message().toString()
                            Toast.makeText(
                                this@RecommendFoodActivity, message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<RecommendFoodResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@RecommendFoodActivity,
                            getString(R.string.failed_calculate_food),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
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
        private const val TAG = "RecommendFood"
    }
}