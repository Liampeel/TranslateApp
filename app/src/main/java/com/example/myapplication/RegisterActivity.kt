package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.Models.DefaultResponse
import com.example.myapplication.Models.createUserData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.register_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        println("before on create super")
        super.onCreate(savedInstanceState)
        println("Im here")
        setContentView(R.layout.register_user)


        btn_register.setOnClickListener{


            val email = editTextEmail2.text.toString().trim()
            val username = editUsername.text.toString().trim()
            val password = editTextPassword3.text.toString().trim()
            val password2 = editTextPasswordConfirm.text.toString().trim()

            if (email.isEmpty()) {
                editTextEmail2.error = "Email required"
                editTextEmail2.requestFocus()
            }

            if (password.isEmpty()) {
                editTextPassword.error = "password required"
                editTextPassword.requestFocus()

            }

            if (username.isEmpty()) {
                editUsername.error = "Username required"
                editUsername.requestFocus()
            }

            if (password != password2) {
                editTextPassword.error = "passwords must match"
                editTextPassword.requestFocus()
            }



            val RetrofitClient = RetrofitClient()

           RetrofitClient.getApiService(this).createUser(createUserData(email, username, password, password2))
               .enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                println("No response from server")
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>
            ) {
                println("got response ")
                if (response.code() == 200) {
                    println("respomnse code is 200")
                    if (response.body() != null) {
                        println("Going to register page")
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)

                        startActivity(intent)
                        Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })


        }
    }
}

