package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class Home : AppCompatActivity() {

    private val url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q="
    var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        process_btn.setOnClickListener {
            val input: String = usr_input.text.toString()

            if (input.isEmpty()) {
                usr_input.error = "Item is required"
                usr_input.requestFocus()
                return@setOnClickListener
            }


            val otherToken =
                "Bearer v^1.1#i^1#f^0#I^3#p^3#r^0#t^H4sIAAAAAAAAAOVYa2wUVRTu9oUILYaI2KJ2nYpGcXbvzO7M7kzYjQst6WrfuwWhErg7c2c7dnZmmDvbdvGR0ghqwERIMAIhacQfRk2wWh8JRn6YiC" +
                        "QoCQaVaJqIGEASVCSRYIje2T7Y1rT0QbSJ+2cz557X991zzty5oLt47kPbarb9UeKak9/bDbrzXS5mHphbXLSstCC/vCgP5Ci4ervv6y7sKTi3HMOUZorNCJuGjpG7K6XpWMwKQ1Ta0kUDYhWLOkwhLNqSGIvU" +
                        "1YqsB4imZdiGZGiUO1oVogSIFOSDCgSKIAR4mUj1YZ9xI0RxMuL8AT+CCAb9AuDJOsZpFNWxDXU7RLGABTTgaCDEGZ8IeJHlPQwXXEe5VyMLq4ZOVDyACmfTFbO2Vk6uE6cKMUaWTZxQ4WhkVawhEq2qro8v9+" +
                        "b4Cg/xELOhncajn1YaMnKvhloaTRwGZ7XFWFqSEMaUNzwYYbRTMTKczDTSz1Ltg4hlAM/7WDYRAEHhplC5yrBS0J44D0eiyrSSVRWRbqt25kaMEjYSTyLJHnqqJy6iVW7nrykNNVVRkRWiqldE1rbEqpspd6y" +
                        "x0TI6VBnJDlKW8fsFzhfgglQYSwzN8EMRBt0M8TsmxEpDl1WHLeyuN+wViKSLRpPCilwOKUSpQW+wIortpJKrxw2T5yd63uHtS9tturOhKEUYcGcfb0z9cC1c3/2bVQ0sDEDIAYnhgwwny+M2ltPrU6mIsLMp" +
                        "kcZGr5MLSsAMnYJWO7JNDUqIlgi96RSyVFn0cQrrCyqIlnlBof2CotAJTuZpRkEIIJRISELwf1EYtm2pibSNRopj7EIWXYhyyBRVqIi20Y70eMZE1FjN7LAZqoguHKLabNsUvd7Ozk5Pp89jWEkvCwDjfbyuN" +
                        "ia1oRSkRnTVGyvTarY2JESssCraJIEQ1UVKjwTXk1S4uXpVc3WsZkO84bHq+uGyHZVZeKx0HKQxJFnInl3oVKVLNxkmnRDkR3mOxXWZZbGgpElJnFRWxzcznW1KfcsmsxY2JEMzAy8ZJmo0NFXK/NsMOL0+M" +
                        "Qs+S26Elp2JIU0jghkBxQ7Q2bXJjj0mDqCpepx280hGymtAMqsd0YZsxu7JKHkxIcgzOPmIZ4+FoGzoWmY6xlOwUfUOMj8MKzOdgCPGU7CBkmSkdXs64YZMp2ChpDVF1TRnRE4nYI75VNLUoZaxVQlPK6SqO" +
                        "9WGp2BiwkwWoKxi0+mVSVkSGXmpSshDXnTZ49VIsuN2qNPrk+nSiGlGU6m0DRMaisqzq139DBvkwYyGkANvlqEi5w3dsCIyXWckVA1FTLqxuYoOSpyfU3yCQAckhCQkBGaEuy6pzjLYDPn+CwhsMEjM+Blh" +
                        "q0Ids21PmYDkh4ws0Ame8dF+cgylg37eR/MKlwBSEEkK4maEeaWmks4f51BYuOXMf4i9xsA2kieLbowg51D8jw8h7+griHBe9sf0uN4HPa6+fJcLeMFSphLcW1zQUlgwvxyrNpmQUPFgNamTL2sLedpRxoSq" +
                        "lV/sMlvgz0tzLj1614M7R6495hYw83LuQMBd11eKmAWLSwghHBAYH+BZfh2ovL5ayNxRePs991d82fz2F6WhsuJNwTX80r3zumRQMqLkchXlFfa48h7ur9i1/zhb3LS9/zhvnWnid2wv7Wjqdi+6fFo4qv" +
                        "Gr77Qf/W11r4Tj6ztazIuRXqPtj94qCE68N6BFcljDzxz9lp/2e4ll05cuPviZrO9fGDnwKFrtZ+8s0f89PBAIF0dCb25sci8/IvOBdbvUha3Woc3BC6vfWtvT8nLl57aUloX2AaTCz7I2/zG2ZY94eaPd25" +
                        "ouL7V9orLy4s29f6biD009c1ry+hTyYvnLy1OXNkcev5ir5lR5ILC54+9tfv4v6Vi573CzsGbtl3sGJnCfvRqYMba9u/bfnzWevFsh+uvdQwJ/Rj5flXz+2e89mONeVfHfz18wOnvnOfjs/fetuHZmXbb76t" +
                        "V7557uiVgcHt+xtD1ZTCjhIAAA=="


            val request = Request.Builder().url(url + input)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", otherToken)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val jsonBody = JSONObject(body.toString())


                    if (response.isSuccessful) {
                        this@Home.runOnUiThread(Runnable {
                            val output = jsonBody.getJSONArray("itemSummaries").getJSONObject(1)
                                .getString("title").toString() + "\n\n" +
                                    jsonBody.getJSONArray("itemSummaries").getJSONObject(1)
                                        .getJSONObject("price").getString("value") +
                                    " " +
                                    jsonBody.getJSONArray("itemSummaries").getJSONObject(1)
                                        .getJSONObject("price").getString("currency")


                            product_output.text = output
                            println(jsonBody.getJSONArray("itemSummaries").length())
                            println(jsonBody)
                        })
                    } else {
                        println(response.networkResponse)
                    }

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


