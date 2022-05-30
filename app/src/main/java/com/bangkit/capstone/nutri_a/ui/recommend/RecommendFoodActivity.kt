package com.bangkit.capstone.nutri_a.ui.recommend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.bangkit.capstone.nutri_a.R

class RecommendFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_food)


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}