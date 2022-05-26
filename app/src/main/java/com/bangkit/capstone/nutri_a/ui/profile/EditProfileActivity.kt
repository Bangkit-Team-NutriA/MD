package com.bangkit.capstone.nutri_a.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}