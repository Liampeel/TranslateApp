package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class Home : AppCompatActivity(){

    private val url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q="
    var client = OkHttpClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        process_btn.setOnClickListener {
            val input: String = usr_input.text.toString()

            if(input.isEmpty()) {
                usr_input.error = "Item is required"
                usr_input.requestFocus()
                return@setOnClickListener
            }

            val token = "Bearer v^1.1#i^1#f^0#r^0#p^3#I^3#t^H4sIAAAAAAAAAOVYa2wUVRTu9kUKVk0gVLDAOl" +
                    "BRyuzOY3d2Z8JuXPqwW/qi2xaoMXX2zp126OzMMHe27ZKQloL8gAj+ExAiCYlBjeADlFfENEqCISQoWh" +
                    "IIalKNghhjBAmRxDvbB9ualj6INnH/bObc8/q+c86dO5fqys5Ztq1s25+5jhnpB7qornSHg55F5WRnFT6" +
                    "akT4/K41KUXAc6FrSldmd8dMKJMZUQ6iFyNA1BJ0dMVVDQlIYIOKmJugiUpCgiTGIBAsIkVBlhcC4KMEwdU" +
                    "sHuko4w8UBgqOgz0tJIpQBC3gPwFJt0GedHiBoL5Q5Hyd5OcD5ol4WryMUh2ENWaJmBQiGYiiS8pIUX0dTA" +
                    "uMRWNblo7lGwtkATaToGlZxUUQwma6QtDVTch07VREhaFrYCREMh0oj1aFwcUlV3Qp3iq/gAA8RS7TiaPhT" +
                    "kS5BZ4OoxuHYYVBSW4jEAYAIEe5gf4ThToXQYDKTSD9JNUNBkfKJfg+QWBqz+lCoLNXNmGiNnYctUSRSTqoKUL" +
                    "MUK/EgRjEb0fUQWANPVdhFuNhp/62Oi6oiK9AMECUrQ+vqIyW1hDNSU2PqbYoEpSRS2uPhvazP6yeCCNAkzQ1E6" +
                    "HczwO+IEEW6Jik2W8hZpVsrIU4XjiSFTiEFK1Vr1WZItuxUUvWYQfIof6Ndzf7yxa0WzS4ojGEGnMnHB1M/2Av3q" +
                    "/+wuoH1iR7gYTyQYfyynxdH6wZ71ifSEUG7KKGaGredC4yKCTImmq3QMlQRQBJgeuMxaCqSwHplhvXLkJQ4XiY9" +
                    "vCyTUa/EkbQMIQVhNAp4//+iMSzLVKJxCw41x8iFJLoAYZMpKKIsWHor1OoSBiRGaiY3m4GO6EABosWyDMHtbm9v" +
                    "d7WzLt1sdjMURbvXVlZEQAuM4aIP6ioPViaVZG8AiK2QIlg4gQDRgVsPB9eaiWBtSWltSaSsqa56VUnVYNsOyyw4Uj" +
                    "oK0ggEJrSmFzpF7tAMmo5Heamc8zKoMlEY8QMVNKNmuaFuI93eIlfVbzAqxOrmwNTAA92ANbqqgMS/zYA962OzwJpSj" +
                    "WhaiQhUVSyYElBkA51eRbbtEXYgGorLHjcX0GNuXcR7tS1qSmbsHI+SG2GCXP07H/bsMqEo6ZqamIzxBGwUrQ3vH7q" +
                    "ZmEzAIeMJ2IgA6HHNmky4AdMJWMhxVVZU1d4iJxMwxXwiaWqimrAUgCYVUtHsbkMTMDHERBKgpCDDnpVxWWIZfqkC6M" +
                    "IvuuTxaijZUSfUnvXxTGnIMMKxWNwSoyoMS9NrXD004+eoKW1CNrxphgqfNzTdDElkpR5VVBgyyJraYtIPvB6vzPI86Q" +
                    "MQAsj7poS7slmZZrBpnvf5eMbvx2bclLAVw7bpVlPaBzwiLfFklKNZ0oOPoaTfw7EkJ3ujFPBDIEPvlDAXqQqe/FEOhZm" +
                    "b+/5D7GU6sqA0XnQjBCmH4n98CLmHX0EE05I/uttxjOp2vJ/ucFBuqoBeTD2VnVGfmfHIfKRYeIcUZRdSmjX8ZW1CV" +
                    "ytMGKJipmc7jHrxekHKpceBF6knhq49cjLoWSl3IFT+/ZUs+rG8XEyIl+JpivGwbCO1+P5qJj03c87Rk5fvMW+fvnhm" +
                    "jXy7/a5v45btfcup3CElhyMrLbPbkVZ344u+VZ8vPfjptY8Wvfv9zjntr93Zxl7qfWZewaaX5izljv961vHz/syL+eapH" +
                    "W/WPOsu+mNh7PKy089dPz87Z03f/rQCsnNrz8tNJxZ849hTPn/TB/xX926dezLn7uIbrWdeyb5zogpcaS7c90nG0pmh4/" +
                    "lXy/eVh9+ie9euuDtz0bxd5zpXvbfu2vmPc9/IZz/MbbrQ03tky6uHELNg7y9HjsV/+P12/c09r3euPLS75zhZCnvOfteQ9" +
                    "9eM/AVPp3+pNq6+Ovvg81mR35Ysj+6/Q3dWBA+37H7n8b5v9xpbC2O7LvV2VnCbd29ccmX73As3jVM/vvD1+rTPTK7jQvGtv" +
                    "J2H2hYe3bHhZFNZnh473F++vwGuj1QkjhIAAA=="

            val request = Request.Builder().url(url+input)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    val jsonBody = JSONObject(body.toString())


                    this@Home.runOnUiThread(Runnable {
//                        product_output.text = body
//                        println(body)
                        val output = jsonBody.getJSONArray("itemSummaries").getJSONObject(1).getString("title").toString() + "\n\n" +
                                jsonBody.getJSONArray("itemSummaries").getJSONObject(1).getJSONObject("price").getString("value") +
                                 " " +
                                jsonBody.getJSONArray("itemSummaries").getJSONObject(1).getJSONObject("price").getString("currency")


                        product_output.text = output
                        println(jsonBody)
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


