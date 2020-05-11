package com.example.myapplication.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class loginClient {

    object RetrofitClient {


        private val BASE_URL = "http://192.168.1.108:5000/"


        val instance: API by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

                .build()
            retrofit.create(API::class.java)

        }
    }
}

