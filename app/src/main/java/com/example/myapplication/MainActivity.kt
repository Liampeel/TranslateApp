package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_login.setOnClickListener {
            val intent = Intent(this, OCRActivity::class.java)
            startActivity(intent)
        }
    }


//        btn_login.setOnClickListener {
//
//
//            btn_login.setOnClickListener {
//
//                val intent = Intent(this, Home::class.java)
//                startActivity(intent);
//            }
//            val email = editTextEmail.text.toString().trim()
//            val password = editTextPassword.text.toString().trim()
//            val name = editTextName.text.toString().trim()
//
//            if(email.isEmpty()) {
//                editTextEmail.error = "Email required"
//                editTextEmail.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(password.isEmpty()) {
//                editTextPassword.error = "Email required"
//                editTextPassword.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(email.isEmpty()){
//                editTextName.error = "Email required"
//                editTextName.requestFocus()
//                return@setOnClickListener
//            }
//
//            RetrofitClient.instance.createUser(email, name, password)
//                .enqueue(object: Callback<DefaultResponse>{
//                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onResponse(
//                        call: Call<DefaultResponse>,
//                        response: Response<DefaultResponse>
//                    ) {
//
//                        if (response.code() == 200) {
//                            if (response.body() != null) {
//
//                                Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                        else{
//                            Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                })
        }


//            val email = editTextEmail.text.toString().trim()
//            val password = editTextPassword.text.toString().trim()
//            val name = editTextName.text.toString().trim()
//
//            if(email.isEmpty()) {
//                editTextEmail.error = "Email required"
//                editTextEmail.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(password.isEmpty()) {
//                editTextPassword.error = "Email required"
//                editTextPassword.requestFocus()
//                return@setOnClickListener
//            }
//
//            if(email.isEmpty()){
//                editTextName.error = "Email required"
//                editTextName.requestFocus()
//                return@setOnClickListener
//            }





