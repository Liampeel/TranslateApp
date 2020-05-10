package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.Models.DefaultResponse
import com.example.myapplication.Models.createUserResponse
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
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editTextPassword.error = "password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                editTextName.error = "Username required"
                editTextName.requestFocus()
                return@setOnClickListener
            }

            if (password != password2) {
                editTextPassword.error = "passwords must match"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(email, username, password, password2)
                .enqueue(object : Callback<createUserResponse> {
                    override fun onFailure(call: Call<createUserResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        println("No response from server")
                    }

                    override fun onResponse(
                        call: Call<createUserResponse>,
                        response: Response<createUserResponse>
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
                                applicationContext,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })


        }
    }
}

