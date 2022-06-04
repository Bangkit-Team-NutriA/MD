package com.bangkit.capstone.nutri_a.ui.recipes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivityRecommendRecipesBinding
import com.bangkit.capstone.nutri_a.response.DataItem
import com.bangkit.capstone.nutri_a.response.RecommendRecipesResponse
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class RecommendRecipesActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel

    private lateinit var binding: ActivityRecommendRecipesBinding

    private lateinit var dataRecipes: List<DataItem>

    private lateinit var dataBahan: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRecommendRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Rekomendasi Resep"
        setupViewModel()
        dataBahan = resources.getStringArray(R.array.input_ingredients).toList()

        val spinner1: Spinner = findViewById(R.id.inputIngredients_1)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner1.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.inputIngredients_2)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner2.adapter = adapter
        }

        val spinner3: Spinner = findViewById(R.id.inputIngredients_3)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner3.adapter = adapter
        }

        val spinner4: Spinner = findViewById(R.id.inputIngredients_4)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner4.adapter = adapter
        }

        val spinner5: Spinner = findViewById(R.id.inputIngredients_5)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner5.adapter = adapter
        }

        val ingredient: List<Int> = listOf(0, 2, 3, 4, 5)

        binding.btnSearch.setOnClickListener {
            getRecommendRecipes(ingredient)
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


    private fun getRecommendRecipes(ingredient: List<Int>){
        showLoading(true)
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getRecommendRecipes(
                    "Bearer " + it.token, ingredient
                )
                client.enqueue(object : Callback<RecommendRecipesResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<RecommendRecipesResponse>,
                        response: Response<RecommendRecipesResponse>
                    ) {
                        showLoading(false)
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@RecommendRecipesActivity,
                                getString(R.string.success_calculate_food),
                                Toast.LENGTH_SHORT
                            ).show()

                            val dataRecipes = responseBody.data
                            val intent = Intent(this@RecommendRecipesActivity, ListRecommendRecipesActivity::class.java)

                            val gson = Gson()
                            intent.putExtra("dataRecipes", gson.toJson(dataRecipes))

                            Toast.makeText(
                                this@RecommendRecipesActivity,
                                "$dataRecipes",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(intent)
                            finish()

                        } else {
                            showLoading(false)
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            val message = response.message().toString()
                            Toast.makeText(
                                this@RecommendRecipesActivity, message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<RecommendRecipesResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@RecommendRecipesActivity,
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
        private const val TAG = "RecommendRecipes"
    }
}