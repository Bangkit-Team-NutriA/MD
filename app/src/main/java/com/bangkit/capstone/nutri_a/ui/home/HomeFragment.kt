package com.bangkit.capstone.nutri_a.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.FragmentHomeBinding
import com.bangkit.capstone.nutri_a.response.GetUserResponse
import com.bangkit.capstone.nutri_a.ui.recipes.RecommendRecipesActivity
import com.bangkit.capstone.nutri_a.ui.recommend.RecommendFoodActivity
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: SharedViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[SharedViewModel::class.java]

        getUser()

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
    }

    private fun getUser() {
        viewModel.getUser().observe(requireActivity()) {
            if (it != null) {
                val client = ApiConfig.getApiService().getUser("Bearer " + it.token)
                client.enqueue(object : Callback<GetUserResponse> {
                    @SuppressLint("LongLogTag")
                    override fun onResponse(
                        call: Call<GetUserResponse>,
                        response: Response<GetUserResponse>
                    ) {
                        val responseBody = response.body()
                        Log.d(HomeActivity.TAG, "onResponse: $responseBody")
                        if (response.isSuccessful && responseBody?.status == "success") {

                            val data = responseBody.data
                            val name = data?.nama.toString()
                            setViewProfile(name)

                        } else {
                            Log.e(HomeActivity.TAG, "onFailure1: ${response.message()}")
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.failed_get_data_user),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    @SuppressLint("LongLogTag")
                    override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                        Log.e(HomeActivity.TAG, "onFailure2: ${t.message}")
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.failed_get_data_user),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private fun setViewProfile(name: String) {
        binding.apply {
            username.text = name
        }
    }
}