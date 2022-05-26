package com.bangkit.capstone.nutri_a.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.databinding.ActivityLoginBinding
import com.bangkit.capstone.nutri_a.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.etEmailLogin.type = "email"
        binding.etPasswordLogin.type = "password"

        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.etEmailLogin.text.toString()
            val inputPassword = binding.etPasswordLogin.text.toString()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}