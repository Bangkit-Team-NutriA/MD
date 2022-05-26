package com.bangkit.capstone.nutri_a.ui.signup

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.nutri_a.databinding.ActivityRegisterBinding
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabelDP(calendar)
        }

        binding.btnDpBirth.setOnClickListener {
            DatePickerDialog(this, datePicker,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun updateLabelDP(calendar: Calendar) {
        val format = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        binding.btnDpBirth.text = sdf.format(calendar.time)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}