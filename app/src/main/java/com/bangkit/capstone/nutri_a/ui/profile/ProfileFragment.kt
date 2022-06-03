package com.bangkit.capstone.nutri_a.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.nutri_a.R
import com.bangkit.capstone.nutri_a.api.ApiConfig
import com.bangkit.capstone.nutri_a.databinding.FragmentProfileBinding
import com.bangkit.capstone.nutri_a.response.GetUserResponse
import com.bangkit.capstone.nutri_a.ui.home.HomeActivity
import com.bangkit.capstone.nutri_a.ui.settings.SettingsActivity
import com.bangkit.capstone.nutri_a.viewmodel.SharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.text.StringBuilder


class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private lateinit var viewModel: SharedViewModel
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[SharedViewModel::class.java]

        getUser()

        binding.btnEdit.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnSetting.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
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
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.success_get_data_user),
                                Toast.LENGTH_SHORT
                            ).show()

                            val data = responseBody.data

                            val name = data?.nama.toString()
                            val genderBoolean = data?.jeniskelamin
                            val heightInput = data?.tinggi.toString()
                            val weightInput = data?.berat

                            var gender = ""
                            if (genderBoolean == true) {
                                gender = "Pria"
                            } else if (genderBoolean == false) {
                                gender = "Perempuan"
                            }


                            Toast.makeText(
                                requireContext(), "$gender",
                                Toast.LENGTH_SHORT
                            ).show()

                            setViewProfile(name, gender,heightInput,weightInput)

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

    private fun setViewProfile(username: String, gender : String,  height: String, weight : Int?) {
        binding.apply {
            tvGender.text = gender
            tvUsername.text = username
            tvHeight.text = StringBuilder(height +" cm")
            tvWeight.text = StringBuilder(weight.toString() + " kg")
        }

    }

}