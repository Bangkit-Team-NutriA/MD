package com.bangkit.capstone.nutri_a.ui.recommend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.databinding.FragmentHomeBinding
import com.bangkit.capstone.nutri_a.databinding.FragmentRecommendBinding
import com.bangkit.capstone.nutri_a.ui.home.NutritionCalculateActivity

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)

        return binding.root
    }


}