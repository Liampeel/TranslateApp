package com.example.myapplication.API

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(context: Context) : Interceptor {
    private val sessionManager = SharedPrefManager(context)


    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = ("Token " + sessionManager.fetchAuthToken())
        System.out.println("Token interceptor")
        System.out.println(token)
        // If token has been saved, add it to the request
        sessionManager.fetchAuthToken()?.let {
            requestBuilder.addHeader("Authorization", token)
        }

        return chain.proceed(requestBuilder.build())
    }
}