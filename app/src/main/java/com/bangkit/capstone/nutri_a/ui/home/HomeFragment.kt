package com.bangkit.capstone.nutri_a.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.capstone.nutri_a.databinding.FragmentHomeBinding
import com.bangkit.capstone.nutri_a.ui.recipes.RecommendRecipesActivity
import com.bangkit.capstone.nutri_a.ui.recommend.RecommendFoodActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.calculatorLayout.setOnClickListener {
            val intent = Intent(activity, NutritionCalculateActivity::class.java)
            startActivity(intent)
        }

        binding.foodLayout.setOnClickListener {
            val intent =  Intent(activity, RecommendFoodActivity::class.java)
            startActivity(intent)

        }

        binding.recipesLayout.setOnClickListener {
            val intent = Intent(activity, RecommendRecipesActivity::class.java)
            startActivity(intent)
        }


        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}