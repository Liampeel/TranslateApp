package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import android.widget.Toast
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.API.loginClient
import com.example.myapplication.Models.DefaultResponse
import com.example.myapplication.Models.loginData
import com.example.myapplication.Models.loginResponse

import kotlinx.android.synthetic.main.register_user.*
import okhttp3.ResponseBody


lateinit var username: EditText
    lateinit var password: EditText

class MainActivity : AppCompatActivity() {

    override fun onDestroy(){
        super.onDestroy()
        System.out.println("destroy")
        RetrofitClient.instance?.api?.logout()?.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    applicationContext, "Failed", Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    println("respomnse code is 200")
                    if (response.body() != null) {
                        println("logging out")
                        Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Error", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        username = findViewById(R.id.usernameText)
        password = findViewById(R.id.editTextPassword)


        registerPage.setOnClickListener {
            println("Before val intent")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            println("in register page listener")
        }

        btn_login.setOnClickListener {
            userLogin()
        }
    }


    private fun userLogin() {
        val intent = Intent(this, OCRActivity::class.java)
        startActivity(intent)

        val username = usernameText.text.toString()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty()) {
            usernameText.error = "Username required"
            usernameText.requestFocus()

        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is empty"
            editTextPassword.requestFocus()

        }





        val sessionManager = SharedPrefManager(this)

        loginClient.RetrofitClient.instance.loginUser(loginData(username, password))
            .enqueue(object : Callback<loginResponse> {
                override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<loginResponse>,
                    response: Response<loginResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            val loginResponse = response.body()
                            Toast.makeText(applicationContext, loginResponse!!.token, Toast.LENGTH_SHORT)
                                .show()
                            sessionManager.saveAuthToken(loginResponse!!.token)

                        }
                    } else {
                        Toast.makeText(applicationContext, "error logging in", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }

    }











