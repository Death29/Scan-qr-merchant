package com.example.testindocyber.screen.chart

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityBaseChartBinding
import com.example.testindocyber.model.network.ChartDataDeserializer
import com.example.testindocyber.model.network.DataChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.InputStream
import kotlin.random.Random

class BaseChart : AppCompatActivity() {
    private lateinit var binding: ActivityBaseChartBinding
    private lateinit var gson: Gson
    private lateinit var dataList: List<DataChart>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initGson()
        initView()
    }

    private fun initGson() {

        val inputStream: InputStream = resources.openRawResource(R.raw.jsonformatter)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        gson = GsonBuilder()
            .registerTypeAdapter(DataChart::class.java, ChartDataDeserializer())
            .create()

        dataList = gson.fromJson(jsonString, Array<DataChart>::class.java).toList()
    }

    private fun initView() {
        for (dataChart in dataList) {
            when (dataChart) {
                is DataChart.DonutChartData -> setupDataDonut(dataChart)
                is DataChart.LineChartData -> setupDataLine(dataChart)
            }
        }
    }

    private fun setupDataLine(dataChart: DataChart.LineChartData) {
        binding.apply {
            val entries = ArrayList<Entry>()

            for (i in 0 until dataChart.data.month?.size!!) {
                entries.add(Entry(i.plus(1).toFloat(), dataChart.data.month[i].toFloat()))
            }
            Log.d("entries Line", "list: $entries")

            val dataSet = LineDataSet(entries, "Data Month")
            val dataChart = LineData(dataSet)
            lineChart.apply {
                data = dataChart
                invalidate()
            }

            lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    Log.d("Data Line", "Data: $e")
                }

                override fun onNothingSelected() {
                    /*NOTHING TO DO HERE*/
                }

            })
        }
    }

    private fun setupDataDonut(dataChart: DataChart.DonutChartData) {
        binding.apply {
            val entries = ArrayList<PieEntry>()

            dataChart.data?.forEach { it ->
                entries.add(PieEntry(it.percentage.toFloat(), it.label))
            }

            Log.d("entries Donut", "list: $entries")

            val dataSet = PieDataSet(entries, "Donut Chart")

            val colorsChart = ArrayList<String>()
            for (i in 0 until dataChart.data?.size!!) {
                val red = Random.nextInt(256)
                val blue = Random.nextInt(256)
                val green = Random.nextInt(256)
                val randomColors = String.format("#%02x%02x%02x", red, green, blue)

                colorsChart.add(randomColors)
            }

            val colors = colorsChart.map { Color.parseColor(it) }
            dataSet.colors = colors

            //Set Data from dataSet to widget chart
            val dataToChart = PieData(dataSet)
            donutChart.apply {
                data = dataToChart
                holeRadius = 50f
                invalidate()
            }

            donutChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    Log.d("Data Entry Donut", e.toString())

                    dataChart.data.map {
                        if (it.percentage.toFloat() == e?.y){
                            startActivity(Intent(this@BaseChart, DetailChart::class.java)
                                .putExtra(DATA_CHART, it))
                        }
                    }
                }

                override fun onNothingSelected() {
                    /*NOTHING TO DO HERE*/
                }

            })
        }
    }

    companion object{
        const val DATA_CHART = "DATA_CHART"
    }
}