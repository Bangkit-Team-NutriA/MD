package com.bangkit.capstone.nutri_a.api

import com.bangkit.capstone.nutri_a.response.*
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
    ): Call<LoginResponse>

    @Multipart
    @POST("predict")
    fun searchCalories(
        @Header("Authorization") header: String,
        @Part file: MultipartBody.Part,
    ): Call<SearchCaloriesResponse>


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
    ): Call<RegisterResponse>

    @GET("users")
    fun getUser(
        @Header("Authorization") header: String
    ):Call<GetUserResponse>


    @GET("calculators")
    fun calculateNutrition(
        @Header("Authorization") header: String,
        @Query("sex") sex: Boolean,
        @Query("weight") weight: Int,
        @Query("height") height: Int,
        @Query("timesOfExercise") timesOfExercise: Int,
        @Query("age") age: Int
    ): Call<CalculatorResponse>

    @GET("recipes")
    fun getRecommendRecipes(
        @Header("Authorization") header: String,
    ):Call<RecommendRecipesResponse>

    @GET("meals")
    fun getRecommendParam(
        @Header("Authorization") header: String,
        @Query("sex") sex: Boolean,
        @Query("weight") weight: Int,
        @Query("height") height: Int,
        @Query("timesOfExercise") timesOfExercise: Int,
        @Query("age") age: Int
    ):Call<RecommendParamResponse>

    @GET("meals")
    fun getRecommendNonParam(
        @Header("Authorization") header: String
    ):Call<RecommendNonParamResponse>

    @FormUrlEncoded
    @PUT("users")
    fun editUser(
        @Header("Authorization") header: String,
        @Field("name") name: String,
        @Field("sex") sex: Boolean,
        @Field("weight") weight: Int,
        @Field("height") height: Int,
    ):Call<EditUserResponse>

    @DELETE("authentications")
    fun logout(
        @Header("Authorization") header: String
    ): Call<LogoutResponse>


}