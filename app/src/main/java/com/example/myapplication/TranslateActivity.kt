package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.StrictMode
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.Models.queryData
import com.example.myapplication.Models.queryResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.neovisionaries.i18n.LanguageCode
import kotlinx.android.synthetic.main.activity_translate.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*


class TranslateActivity : AppCompatActivity() {

    private var translate: Translate? = null
    lateinit var input: TextView
    lateinit var langDetected: TextView
    lateinit var postQuery: Button
    lateinit var logoutButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        input = findViewById(R.id.inputToTranslate)
        langDetected = findViewById(R.id.langDetected)
        postQuery = findViewById(R.id.postQuery)
        logoutButton = findViewById(R.id.logoutButton)


        inputToTranslate.movementMethod = ScrollingMovementMethod()

        val translate: String? = intent.getStringExtra("translate")
        input.text = translate

        if (translate != null) {
            detectLang(translate)
        }

        val languageCodes = arrayListOf("en", "fr", "es", "it", "de", "pt", "nl", "pl", "el", "bg", "hu",
            "ar", "fa", "id", "ja", "ru", "sv", "tr", "th", "vi")

        val fullLanguageText = arrayListOf<String>()

        for(lan in languageCodes) {
            fullLanguageText.add(Locale(lan).displayLanguage)
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fullLanguageText)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        language_selector.adapter = adapter


        language_selector.background.setColorFilter(resources.getColor(R.color.login_form_details), PorterDuff.Mode.SRC_ATOP);

        language_selector.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                position: Int, id: Long) {
                (parent.getChildAt(0) as TextView).setTextColor(Color.WHITE)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        postQuery.setOnClickListener {
            postData()
        }

        logoutButton.setOnClickListener {
            logout()
        }




        translateButton.setOnClickListener {

            val chosenLanguage = LanguageCode.findByName(language_selector.selectedItem.toString())[0].name
            if(chosenLanguage.isEmpty()) {
                language_selector.prompt = "Field is empty"
                language_selector.requestFocus()
                return@setOnClickListener
            }

            if (checkInternetConnection()) {
                //If there is internet connection, get translate service and start translation:
                getTranslateService()
                translate(chosenLanguage)

            } else {

                //If not, display "no connection" warning:
                translatedTv!!.text = resources.getString(R.string.no_connection)
            }
        }
    }

    private fun logout() {


        var token = ("Token "+ SharedPrefManager.getInstance(applicationContext).fetchAuthToken())



        System.out.println(token)

        com.example.myapplication.API.RetrofitClient.getInstanceToken(token)?.api?.logout()

            ?.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    println("No response from server")
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    println("got response ")
                    if (response.code() == 200) {
                        println("respomnse code is 201")
                        if (response.body() != null) {
                            println("sending translation")
                            SharedPrefManager.getInstance(this@TranslateActivity).clear()
                            val intent = Intent(this@TranslateActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext, "Error", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }










    @SuppressLint("SetTextI18n")
    private fun detectLang(output: String){
        val languageIdentifier = FirebaseNaturalLanguage.getInstance().languageIdentification

        println(output)
        languageIdentifier.identifyLanguage(output)
            .addOnSuccessListener { lang ->
                if (lang !== "und") {
                    val displayLanguage = Locale(lang).displayLanguage
                    langDetected.text = "Language detected = $displayLanguage"
                    println("Language = $displayLanguage")
                } else {
                    Toast.makeText(this, "Can't Detect Language", Toast.LENGTH_LONG).show()

                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun getTranslateService() {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            resources.openRawResource(R.raw.narhwal2ddb1459490f).use { `is` ->
                val myCredentials = GoogleCredentials.fromStream(`is`)
                val translateOptions =
                    TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                translate = translateOptions.service
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()

        }

    }

    private fun translate(language : String) {
        //Get input text to be translated:
        val originalText: String = inputToTranslate!!.text.toString()
        val translation = translate!!.translate(
            originalText,
            Translate.TranslateOption.targetLanguage(language),
            Translate.TranslateOption.model("base")
        )

        //Translated text and original text are set to TextViews:
        translatedTv!!.text = translation.translatedText

    }


    private fun checkInternetConnection(): Boolean {

        //Check internet connection:
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        //Means that we are connected to a network (mobile or wi-fi)
        return activeNetwork?.isConnected == true

    }


    private fun postData(){
        System.out.println("Post Data method")
        val initialText = translatedTv.text.toString().trim()
        val language = language_selector.selectedItem.toString()
        val translatedText = inputToTranslate.text.toString().trim()



        val RetrofitClient = RetrofitClient()



        var token = ("Token "+ SharedPrefManager.getInstance(applicationContext).fetchAuthToken())
        System.out.println(token)

        com.example.myapplication.API.RetrofitClient.getInstanceToken(token)?.api?.queries(queryData(translatedText, language, initialText)
        )
            ?.enqueue(object: Callback<queryResponse> {
                override fun onFailure(call: Call<queryResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    println("No response from server")
                }

                override fun onResponse(call: Call<queryResponse>, response: Response<queryResponse>
                ) {
                    println("got response ")
                    if (response.code() == 201) {
                        println("respomnse code is 201")
                        if (response.body() != null) {
                            println("sending translation")
                            val intent = Intent(this@TranslateActivity, OCRActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext, "Error", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }

    }

private fun <T> Call<T>.enqueue(callback: Callback<T>, function: () -> Unit) {

}


