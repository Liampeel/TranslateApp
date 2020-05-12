package com.example.myapplication.API

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(var token: String) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        System.out.println("Authinterceptor")
        System.out.println("Authinterceptor" + token)
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", token).build()
        return chain.proceed(authenticatedRequest)
    }


}