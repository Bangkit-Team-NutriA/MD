package com.bangkit.capstone.nutri_a.ui.recommend

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.nutri_a.adapter.FoodAdapter
import com.bangkit.capstone.nutri_a.databinding.ActivityResultRecommendFoodBinding
import com.bangkit.capstone.nutri_a.model.DataFood
import com.bangkit.capstone.nutri_a.response.InformationParam
import com.bangkit.capstone.nutri_a.response.MalamItemParam
import com.bangkit.capstone.nutri_a.response.PagiItemParam
import com.bangkit.capstone.nutri_a.response.SiangItemParam
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.gson.Gson

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ResultRecommendFoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultRecommendFoodBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultRecommendFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()

        val layoutManagerMorning = LinearLayoutManager(this)
        binding.rvMorning.layoutManager = layoutManagerMorning

        val layoutManagerDay = LinearLayoutManager(this)
        binding.rvDay.layoutManager = layoutManagerDay

        val layoutManagerNight = LinearLayoutManager(this)
        binding.rvNight.layoutManager = layoutManagerNight

        getDataParam()

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    private fun getDataParam(){

        val gson = Gson()
        val data = gson.fromJson(intent.getStringExtra("dataFood"), InformationParam::class.java)

        setDataMorning(data.pagi as List<PagiItemParam>)
        setDataAfternoon(data.siang as List<SiangItemParam>)
        setDataNight(data.malam as List<MalamItemParam>)


    }

    private fun setDataMorning(items: List<PagiItemParam>) {
        val listFood = ArrayList<DataFood>()
        for(item in items) {
            val morning = DataFood(
                item.nama,
                item.energiEnergy,
                item.karbohidratCHO,
                item.lemakFat,
                item.proteinProtein
            )
            listFood.add(morning)
        }

        val adapter = FoodAdapter(listFood)
        binding.rvMorning.adapter = adapter
    }

    private fun setDataAfternoon(items: List<SiangItemParam>) {
        val listFood = ArrayList<DataFood>()
        for(item in items) {
            val day = DataFood(
                item.nama,
                item.energiEnergy,
                item.karbohidratCHO,
                item.lemakFat,
                item.proteinProtein
            )
            listFood.add(day)
        }

        val adapter = FoodAdapter(listFood)
        binding.rvDay.adapter = adapter
    }

    private fun setDataNight(items: List<MalamItemParam>) {
        val listFood = ArrayList<DataFood>()
        for(item in items) {
            val night = DataFood(
                item.nama,
                item.energiEnergy,
                item.karbohidratCHO,
                item.lemakFat,
                item.proteinProtein
            )
            listFood.add(night)
        }

        val adapter = FoodAdapter(listFood)
        binding.rvNight.adapter = adapter
    }
}