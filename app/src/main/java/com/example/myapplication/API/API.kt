package com.example.myapplication.API


import com.example.myapplication.Models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface API {

    @POST("login/")
    fun loginUser(@Body loginData: loginData): Call<loginResponse>

    @POST("register/")
    fun createUser(@Body createUserData: createUserData): Call<DefaultResponse>

    @POST("queries/")
    fun queries(@Body queryData: queryData): Call<queryResponse>

    @POST("logout/")
    fun logout(): Call<ResponseBody>

    @GET("languages/")
    fun getQueries(): Call<languageResponse>
}