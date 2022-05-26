package com.bangkit.capstone.nutri_a.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.ui.search_calories.SearchCaloriesActivity
import com.bangkit.capstone.nutri_a.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();

        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        setupWithNavController(bottomNavigationView, navController)

        binding.fbSearchRecipes.setOnClickListener {
            val intent = Intent(this, SearchCaloriesActivity::class.java)
            startActivity(intent)
        }
    }
}