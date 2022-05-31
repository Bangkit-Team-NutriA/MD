package com.bangkit.capstone.nutri_a.api

import com.bangkit.capstone.nutri_a.response.LoginResponse
import com.bangkit.capstone.nutri_a.response.SearchCaloriesResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("authentications")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @FormUrlEncoded
    @POST("predict")
    fun searchCalories(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
    ) : Call<SearchCaloriesResponse>


}