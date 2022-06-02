package com.bangkit.capstone.nutri_a.ui.search_calories

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.databinding.ActivityResultCaloriesBinding
import com.bangkit.capstone.nutri_a.response.InformationCalories
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import java.io.File

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
        val name = intent.getStringExtra("nameFood")
        binding.tvName.text = name

        val gson = Gson()
        val data = gson.fromJson(intent.getStringExtra("dataFood"), InformationCalories::class.java)

        val picture: File? = intent.getSerializableExtra("imageFood") as File?


        Glide.with(this)
            .load(picture)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgPhoto)

        binding.tvCaloriesValue.text = data.kalori
        binding.tvCarbohydrateValue.text = data.karbohidrat
        binding.tvProteinValue.text = data.protein
        binding.tvFatValue.text = data.lemak


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