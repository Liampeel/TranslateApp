package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class home : AppCompatActivity(){

    val url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=ipad"
    var client = OkHttpClient()
//    val request = Request.Builder().url(url).build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        process_btn.setOnClickListener {
            val str: String = usr_input.text.toString()
            product_output.text = str
//            getStuff()
//            run()



            val thread = Thread(Runnable {
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()

                val response = client.newCall(request).execute()
                val jsonDataString = response.body.toString()

                val json = JSONObject(jsonDataString)
                if (!response.isSuccessful) {
                    val errors = json.getJSONArray("errors").join(", ")
                    throw Exception(errors)
                }
                val rawUrl = json.getJSONObject("urls").getString("raw")

                println(rawUrl)

            })
            thread.start()


            println("TEST")

        }

    }



}


