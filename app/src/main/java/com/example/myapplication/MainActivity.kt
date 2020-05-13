package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import com.example.myapplication.Models.loginData
import com.example.myapplication.Models.loginResponse
import kotlinx.coroutines.delay
import okhttp3.ResponseBody


lateinit var username: EditText
lateinit var password: EditText

class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        println("destroy")
        val token = ("Token " + SharedPrefManager.getInstance(applicationContext).fetchAuthToken())

        println(token)
        RetrofitClient.getInstanceToken(token)?.api?.logout()

            ?.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    println("No response from server")
                }

                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    println("got response ")
                    if (response.code() == 200) {
                        println("Response code is: ${response.code()}")
                        if (response.body() != null) {
                            println("Sending translation")
                            SharedPrefManager.getInstance(this@MainActivity).clear()
                            val intent = Intent(this@MainActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT)
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

        icon.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=ykwqXuMPsoc"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.google.android.youtube")
            startActivity(intent)
        }

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

        var loggedIn: Boolean

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
        if (password.length < 5) {
            editTextPassword.error = "Password length is insufficient"
            editTextPassword.requestFocus()

        } else {

            val intent = Intent(this, OCRActivity::class.java)
            loginClient.RetrofitClient.instance.loginUser(loginData(username, password))
                .enqueue(object : Callback<loginResponse> {
                    override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<loginResponse>,
                        response: Response<loginResponse>
                    ) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                val loginResponse = response.body()

                                loggedIn = true

                                Toast.makeText(
                                    applicationContext,
                                    R.string.loginSuccess,
                                    Toast.LENGTH_SHORT
                                ).show()

                                println("Token generated: " + loginResponse!!.token)

                                if (loggedIn) {
                                    startActivity(intent)
                                    usernameText.setText("")
                                    editTextPassword.setText("")
                                }

                                SharedPrefManager.getInstance(applicationContext).saveAuthToken(
                                    loginResponse.token
                                )
                            }

                        } else {
                            Toast.makeText(
                                applicationContext, "Error logging in", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }
    }

}









