package com.bangkit.capstone.nutri_a.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.R

class RecommendRecipesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_recipes)

        actionBar?.title = "Rekomendasi Resep"
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}