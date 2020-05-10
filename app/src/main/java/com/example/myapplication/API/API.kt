package com.example.myapplication.API


import com.example.myapplication.Models.DefaultResponse
import com.example.myapplication.Models.createUserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {


    @POST("login/")
    fun loginUser(
        @Field("username") username:String,
        @Field("password") password:String

    ):Call<DefaultResponse>

    @POST("register/")
    fun createUser(@Body createUserData: createUserData):Call<createUserData>
}