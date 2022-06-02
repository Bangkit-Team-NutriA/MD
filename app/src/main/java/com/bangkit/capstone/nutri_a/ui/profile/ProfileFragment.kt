package com.bangkit.capstone.nutri_a.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bangkit.capstone.nutri_a.databinding.FragmentProfileBinding
import com.bangkit.capstone.nutri_a.response.DataUser
import com.bangkit.capstone.nutri_a.ui.settings.SettingsActivity

class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val name = arguments?.getString("dataName")
        var sex = arguments?.getString("dataSex")
        val weight = arguments?.getString("dataWeight")
        val height = arguments?.getString("dataHeight")

        binding.tvUsername.text = name
        binding.tvGender.text = sex
        binding.tvWeight.text = weight
        binding.tvHeight.text = height

        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }



    companion object {

    }
}