package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import java.io.IOException


class Home : AppCompatActivity(){

    private val url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=ipad"
    var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        process_btn.setOnClickListener {
            val input: String = usr_input.text.toString()
//            product_output.text = input

            val request = Request.Builder().url(url)
                .addHeader("Content-Type", "application/json")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()

                    this@Home.runOnUiThread(Runnable {
                        product_output.text = body
                        println(body)
                        })

                }

                override fun onFailure(call: Call, e: IOException) {
                    println("Error")
                    e.printStackTrace()
                    println(call.toString())
                }
            })
        }

    }
}


