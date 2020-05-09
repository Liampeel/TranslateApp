package com.example.myapplication.API


import com.example.myapplication.Models.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface API {

    @FormUrlEncoded
    @POST("users")
    fun createUser(
        @Field("email") email:String,
        @Field("name") name:String,
        @Field("password") password:String
    ):Call<DefaultResponse>
}