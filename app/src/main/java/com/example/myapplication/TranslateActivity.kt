package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.StrictMode
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.neovisionaries.i18n.LanguageCode
import kotlinx.android.synthetic.main.activity_translate.*
import java.io.IOException
import java.util.*


class TranslateActivity : AppCompatActivity() {

    private var translate: Translate? = null
    lateinit var input: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        input = findViewById(R.id.inputToTranslate)

        val translate: String? = intent.getStringExtra("translate")
        input.text = translate

        val languageCodes = arrayListOf("en", "fr", "es", "it", "de", "pt", "nl", "pl", "el", "bg", "hu",
            "id", "ja", "ru", "sv", "tr", "th", "vi")

        val fullLanguageText = arrayListOf<String>()

        for(lan in languageCodes) {
            fullLanguageText.add(Locale(lan).displayLanguage)
        }


        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, fullLanguageText)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        language_selector.adapter = aa


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

}