package com.example.myapplication.API



import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {



    private val BASE_URL = "http://192.168.1.108:5000/"

    private lateinit var apiService: API

        val instance: API by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())


            .build()
        retrofit.create(API::class.java)

    }

//    private fun okhttpClient(context: Context): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(AuthenticationInterceptor(context))
//            .build()




    }