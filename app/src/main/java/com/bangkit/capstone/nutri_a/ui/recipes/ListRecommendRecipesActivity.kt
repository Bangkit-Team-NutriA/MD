package com.bangkit.capstone.nutri_a.ui.recipes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.nutri_a.adapter.RecipesAdapter
import com.bangkit.capstone.nutri_a.databinding.ActivityListRecommendRecipesBinding
import com.bangkit.capstone.nutri_a.model.DataRecipes
import com.bangkit.capstone.nutri_a.response.*
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ListRecommendRecipesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListRecommendRecipesBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityListRecommendRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)

        setupViewModel()

        val layoutManager = LinearLayoutManager(this)
        binding.rvRecipes.layoutManager = layoutManager

        getData()

    }

    private fun getData(){

        val gson = Gson()
        val data = gson.fromJson(intent.getStringExtra("dataRecipes"), Item::class.java)
        Log.e("data",data.toString())
        setData(data.data as List<DataItem>)

    }

    private fun setData(items: List<DataItem>){
        val listRecipes = ArrayList<DataRecipes>()
        for(item in items) {
            val recipes = DataRecipes(
                item.cara,
                item.nama,
                item.nutrisi,
                item.deskripsi,
                item.bahan,
                item.bahan2,
                item.url
            )

            listRecipes.add(recipes)
        }

        val adapter = RecipesAdapter(listRecipes)
        binding.rvRecipes.adapter = adapter
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }
}