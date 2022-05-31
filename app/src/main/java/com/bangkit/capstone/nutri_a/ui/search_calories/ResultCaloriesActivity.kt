package com.bangkit.capstone.nutri_a.ui.search_calories

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.databinding.ActivityResultCaloriesBinding
import com.bangkit.capstone.nutri_a.model.InformationCalories
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class ResultCaloriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultCaloriesBinding

    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCaloriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        getCalories()

        supportActionBar?.title = "Result Calories!"

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    private fun getCalories() {
        var informationCalories = InformationCalories()


        binding.tvCaloriesValue.text = informationCalories.kalori
        binding.tvCarbohydrateValue.text = informationCalories.karbohidrat
        binding.tvProteinValue.text = informationCalories.protein
        binding.tvFatValue.text = informationCalories.lemak

    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "ResultSearchCaloriesActivity"
    }
}