package com.bangkit.capstone.nutri_a.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivityHomeBinding
import com.bangkit.capstone.nutri_a.response.GetUserResponse
import com.bangkit.capstone.nutri_a.ui.profile.ProfileFragment
import com.bangkit.capstone.nutri_a.ui.search_calories.SearchCaloriesActivity
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()

        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        setupWithNavController(bottomNavigationView, navController)

        binding.fbSearchRecipes.setOnClickListener {
            val intent = Intent(this, SearchCaloriesActivity::class.java)
            startActivity(intent)
        }

        getUser()



    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    private fun getUser() {
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService().getUser("Bearer " + it.token)
                client.enqueue(object : Callback<GetUserResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<GetUserResponse>,
                        response: Response<GetUserResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d(TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {

                            val data = responseBody.data

                            val name = data?.nama.toString()
                            val genderBoolean = data?.jeniskelamin
                            val heightInput = data?.tinggi.toString()
                            val weightInput = data?.berat.toString()

                            var gender = ""
                            if (genderBoolean == true) {
                                gender = "Pria"
                            } else if (genderBoolean == false) {
                                gender = "Wanita"
                            }

                        } else {
                            Log.e(TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(
                                this@HomeActivity,
                                getString(R.string.failed_get_data_user),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@HomeActivity,
                            getString(R.string.failed_get_data_user),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    companion object {

        const val TAG = "SearchCaloriesActivity"
    }
}