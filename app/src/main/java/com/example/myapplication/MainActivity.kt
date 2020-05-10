package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import android.widget.Toast
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.Models.DefaultResponse
import kotlinx.android.synthetic.main.activity_main.editTextEmail
import kotlinx.android.synthetic.main.activity_main.editTextPassword
import kotlinx.android.synthetic.main.register_user.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerPage.setOnClickListener {
            println("Before val intent")
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            println("in register page listener")
        }

        btn_login.setOnClickListener {

            val intent = Intent(this, OCRActivity::class.java)
            startActivity(intent)

            val username = editTextName.text.toString()
            val password = editTextPassword.text.toString().trim()



            if (password.isEmpty()) {
                editTextPassword.error = "Email required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }



            RetrofitClient.instance.loginUser(username, password)
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {

                        if (response.code() == 200) {
                            if (response.body() != null) {

                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "error logging in",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }
    }
}










