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

            var request = Request.Builder().url(url).build()

            var test = ""

            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                   e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful) {
                        val myResponse = response.body.toString()

                        this@home.runOnUiThread(Runnable {
                            product_output.text = myResponse
                            test = myResponse
                        })
                    }
                }
            })


            println(test)
            println("TEST")

        }

    }



}


