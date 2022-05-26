package com.bangkit.capstone.nutri_a.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}