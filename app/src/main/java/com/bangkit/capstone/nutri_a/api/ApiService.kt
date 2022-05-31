package com.bangkit.capstone.nutri_a.api

import com.bangkit.capstone.nutri_a.response.LoginResponse
import com.bangkit.capstone.nutri_a.response.RegisterResponse
import com.bangkit.capstone.nutri_a.response.SearchCaloriesResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {

    @FormUrlEncoded
    @POST("authentications")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun searchCalories(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
    ) : Call<SearchCaloriesResponse>


    @FormUrlEncoded
    @POST("users")
    fun createAccount(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("birthOfDate") birthOfDate: String,
        @Field("sex") sex: Boolean,
        @Field("weight") weight: Int,
        @Field("height") height: Int,
        @Field("timesOfExercise") timesOfExercise: Int
    ) : Call<RegisterResponse>

}