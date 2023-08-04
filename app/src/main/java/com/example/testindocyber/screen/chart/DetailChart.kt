package com.example.testindocyber.screen.chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.IntentCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityDetailChartBinding
import com.example.testindocyber.model.network.DataDonutChart
import com.example.testindocyber.model.network.DetailDataDonut

class DetailChart : AppCompatActivity() {
    private lateinit var binding: ActivityDetailChartBinding
    private lateinit var adapterListChart: AdapterListChart

    private val dataIntent by lazy {
        IntentCompat.getParcelableExtra(intent, BaseChart.DATA_CHART, DataDonutChart::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        adapterListChart = AdapterListChart(this@DetailChart)

        binding.apply {
            rvDataTrans.apply {
                layoutManager = LinearLayoutManager(this@DetailChart, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = adapterListChart
            }

            adapterListChart.setDataChart(dataIntent?.listDataDonut as ArrayList<DetailDataDonut>)
        }
    }
}