package com.bangkit.capstone.nutri_a.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.R

class NutritionCalculateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nutrition_calculate)

        actionBar?.title = "Calculator Nutrition";
    }
}