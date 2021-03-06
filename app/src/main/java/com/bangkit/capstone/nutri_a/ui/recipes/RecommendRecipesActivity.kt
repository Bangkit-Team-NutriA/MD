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

    private lateinit var dataBahan: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRecommendRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()
        dataBahan = resources.getStringArray(R.array.input_ingredients).toList()

        val spinner1: Spinner = findViewById(R.id.inputIngredients_1)
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }

        val spinner2: Spinner = findViewById(R.id.inputIngredients_2)
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

        val spinner3: Spinner = findViewById(R.id.inputIngredients_3)
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner3.adapter = adapter
        }

        val spinner4: Spinner = findViewById(R.id.inputIngredients_4)
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner4.adapter = adapter
        }

        val spinner5: Spinner = findViewById(R.id.inputIngredients_5)
        ArrayAdapter.createFromResource(
            this,
            R.array.input_ingredients,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner5.adapter = adapter
        }
        val spinnerAll: List<Spinner> = listOf(spinner1, spinner2, spinner3, spinner4, spinner5)

        binding.btnSearch.setOnClickListener {
            val ingredient: List<Int> = getIngredientsIndex(spinnerAll)
            if(ingredient.size > 1){
                getRecommendRecipes(ingredient)
            } else {
                Toast.makeText(
                    this@RecommendRecipesActivity,
                    getString(R.string.ingredients_rule),
                    Toast.LENGTH_SHORT
                ).show()
            }
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

    private fun getIngredientsIndex(spinner: List<Spinner>) : List<Int> {
        var ingredient: List<Int> = listOf()
        for (item in spinner) {
            if (item.selectedItem.toString() != "-") {
                ingredient += dataBahan.indexOf((item.selectedItem.toString()))-1
            }
        }
        return ingredient
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
                                getString(R.string.success_calculate_recipes),
                                Toast.LENGTH_SHORT
                            ).show()

                            val dataRecipes = object {
                                val data = responseBody.data
                            }
                            Log.e("data1",dataRecipes.data.toString())
                            val intent = Intent(this@RecommendRecipesActivity, ListRecommendRecipesActivity::class.java)

                            val gson = Gson()
                            intent.putExtra("dataRecipes", gson.toJson(dataRecipes))


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