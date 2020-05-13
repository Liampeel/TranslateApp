package com.example.myapplication.TEST

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.OCRActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_query_details.*
import kotlinx.android.synthetic.main.activity_query_list.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class queryDetails : AppCompatActivity() {



    var IdText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_details)

        val initText = intent.getStringExtra("initialText")
        val langText = intent.getStringExtra("langText")
        val TransText = intent.getStringExtra("transText")
        val DateCreatedText = intent.getStringExtra("dateCreate")
        IdText = intent.getStringExtra("idText")

        initialView.setText(initText)
        langView.setText(langText)
        transView.setText(TransText)
        idView.setText(IdText)
        dateView.setText(DateCreatedText)


        deleteButton.setOnClickListener {
            deleteRecord()
        }



    }

    private fun deleteRecord() {
        var token = ("Token "+ SharedPrefManager.getInstance(applicationContext).fetchAuthToken())
        println("deleteRecord")
        println(IdText)

        RetrofitClient.getInstanceToken(token)?.api?.deleteRecord(IdText)?.enqueue(object :
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                System.out.println(t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                        val intent = Intent(this@queryDetails, OCRActivity::class.java)
                        startActivity(intent)
                    } else {
                        System.out.println("Failed")
                    }
                }


        })

    }


}
