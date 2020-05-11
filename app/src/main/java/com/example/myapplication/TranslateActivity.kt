package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.StrictMode
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.neovisionaries.i18n.LanguageCode
import kotlinx.android.synthetic.main.activity_translate.*
import java.io.IOException
import java.util.*


class TranslateActivity : AppCompatActivity() {

    private var translate: Translate? = null
    lateinit var input: TextView
    lateinit var langDetected: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        input = findViewById(R.id.inputToTranslate)
        langDetected = findViewById(R.id.langDetected)

        inputToTranslate.movementMethod = ScrollingMovementMethod()

        val translate: String? = intent.getStringExtra("translate")
        input.text = translate

        if (translate != null) {
            detectLang(translate)
        }

        val languageCodes = arrayListOf("en", "fr", "es", "it", "de", "pt", "nl", "pl", "fi", "bg", "hu",
            "ar", "fa", "id", "hi", "ja", "ru", "sv", "tr", "th", "vi")

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
}