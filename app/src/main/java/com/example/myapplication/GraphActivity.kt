package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.Models.languageResponse
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class GraphActivity : AppCompatActivity() {

    private val url = "http://kieronhushon.pythonanywhere.com/languages/"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        getLanguages()
        setBarChart()
    }


    private fun getLanguages(){
        var token = ("Token "+ SharedPrefManager.getInstance(applicationContext).fetchAuthToken())
        RetrofitClient.getInstanceToken(token)?.api?.getQueries()?.enqueue(object: Callback<languageResponse>{
            override fun onFailure(call: Call<languageResponse>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(
                call: Call<languageResponse>,
                response: Response<languageResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()

                    val arrayOfOccurence = mutableListOf<Int>()
                    val arrayOfLanguages = mutableListOf<String>()

                    if (body != null) {
                        for(i in 0 until body.listoflangs.size()) {
                            val format: String = body.listoflangs[i].toString().replace("{\"","").replace("\":"," ").replace("}","")

                            val language = format.replace("[^A-Za-z]".toRegex(), "")
                            arrayOfLanguages.add(language)

                            val num = format.replace("[^0-9]".toRegex(), "")
                            arrayOfOccurence.add(num.toInt())
                        }
                        for(i in 0 until arrayOfLanguages.size) {
                            println(arrayOfLanguages[i])
                            println(arrayOfOccurence[i])
                        }

                        setBarChart(arrayOfOccurence, arrayOfLanguages)
                    }

               }    else {
                    println("response not succesfull")
                  }

            }





        })

    }

    private fun setBarChart(numberList : List<Int>, languageList : List<String>) {
        val entries = ArrayList<BarEntry>()
//        entries.add(BarEntry(10f, 0.toFloat()))
//        entries.add(BarEntry(2f, 1.toFloat()))
//        entries.add(BarEntry(5f, 2.toFloat()))
//        entries.add(BarEntry(20f, 3.toFloat()))
//        entries.add(BarEntry(15f, 4.toFloat()))
//        entries.add(BarEntry(19f, 5.toFloat()))


        for(element in numberList) {
            entries.add(BarEntry(10f, element.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Cells")

        val labels = ArrayList<String>()
        labels.add("18-Jan")
        labels.add("19-Jan")
        labels.add("20-Jan")
        labels.add("21-Jan")
        labels.add("22-Jan")
        labels.add("23-Jan")

        val xAxis: XAxis = bargraph.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)


       // xAxis.valueFormatter = IAxisValueFormatter() { value, axis -> labels.get(value.toInt()) } as ValueFormatter?


        val data = BarData(barDataSet)
        bargraph.data = data // set the data and list of lables into chart

        bargraph.description.text = "Languages"  // set the description
        barDataSet.color = resources.getColor(R.color.colorAccent)

        bargraph.animateY(5000)
    }

}



