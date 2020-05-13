package com.example.myapplication

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.API.RetrofitClient
import com.example.myapplication.API.SharedPrefManager
import com.example.myapplication.Models.languageResponse
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_graph.*
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

        var count = 1
        for(element in numberList) {
            println(element)
            entries.add(BarEntry(count.toFloat(), element.toFloat()))
            count++
        }

        val barDataSet = BarDataSet(entries, " ")


        val xAxis: XAxis = bargraph.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE

        val formatter: ValueFormatter =
            object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return languageList.get(value.toInt() - 1)
                }
            }

        xAxis.granularity = 1f // minimum axis-step (interval) is 1

        xAxis.valueFormatter = formatter


       // xAxis.valueFormatter = IAxisValueFormatter() { value, axis -> labels.get(value.toInt()) } as ValueFormatter?


        val data = BarData(barDataSet)
        bargraph.data = data // set the data and list of lables into chart
        barDataSet.color = resources.getColor(R.color.colorPrimary)
        bargraph.setFitBars(true)
        bargraph.description.text = " "
//        bargraph.axisLeft.textColor = R.color.login_form_details; // left y-axis
//        bargraph.xAxis.textColor = R.color.login_form_details;
//        bargraph.legend.textColor = R.color.login_form_details;
        bargraph.animateY(5000)
    }

}



