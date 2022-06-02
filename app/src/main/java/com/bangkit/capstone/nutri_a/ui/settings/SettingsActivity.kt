package com.bangkit.capstone.nutri_a.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.ActivitySettingsBinding
import com.bangkit.capstone.nutri_a.response.LogoutResponse
import com.bangkit.capstone.nutri_a.ui.search_calories.SearchCaloriesActivity
import com.bangkit.capstone.nutri_a.ui.signup.LoginActivity
import com.bangkit.capstone.nutri_a.utils.UserPreference
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import com.bangkit.capstone.nutri_a.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViewModel()

        binding.layoutFaq.setOnClickListener {
            val intent = Intent(this, FAQActivity::class.java)
            startActivity(intent)
        }

        binding.layoutAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.layoutBahasa.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        binding.layoutLogout.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        viewModel.getUser().observe(this) {
            if (it != null) {
                val client = ApiConfig.getApiService()
                    .logout("Bearer " + it.token)
                client.enqueue(object : Callback<LogoutResponse> {
                    override fun onResponse(
                        call: Call<LogoutResponse>,
                        response: Response<LogoutResponse>
                    ) {
                        val responseBody = response.body()
                        val status = responseBody?.status;
                        Log.d(TAG, "onResponse: $status")
                        if (response.isSuccessful && responseBody?.status == "success") {
                            Toast.makeText(
                                this@SettingsActivity,
                                getString(R.string.logout_success),
                                Toast.LENGTH_SHORT
                            ).show()

                            viewModel.logout()
                            val intent =
                                Intent(applicationContext, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            Toast.makeText(
                                this@SettingsActivity,
                                getString(R.string.logout_success),
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Log.e(
                                SearchCaloriesActivity.TAG,
                                "onFailure1: ${response.message()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                        Log.e(TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            this@SettingsActivity,
                            getString(R.string.logout_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SharedViewModel::class.java]
    }

    companion object {
        private const val TAG = "Settings"
    }

}