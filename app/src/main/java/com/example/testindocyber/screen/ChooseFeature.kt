package com.example.testindocyber.screen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityChooseFeatureBinding
import com.example.testindocyber.screen.chart.BaseChart
import com.example.testindocyber.screen.promo.MainActivity
import com.example.testindocyber.screen.qr.HistoryPayment
import com.example.testindocyber.screen.qr.HomeViewUser
import com.example.testindocyber.screen.qr.QrScanner

class ChooseFeature : AppCompatActivity() {
    private lateinit var binding: ActivityChooseFeatureBinding
    private val isFromTrans: Boolean by lazy {
        intent.getBooleanExtra(IS_FROM_TRANS, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            btnPromo.setOnClickListener {
                startActivity(Intent(this@ChooseFeature, MainActivity::class.java))
            }

            btnQr.setOnClickListener {
                startActivity(HomeViewUser.newIntent(this@ChooseFeature, !isFromTrans))
            }

            btnHistory.setOnClickListener {
                startActivity(Intent(this@ChooseFeature, HistoryPayment::class.java))
            }

            btnChart.setOnClickListener {
                startActivity(Intent(this@ChooseFeature, BaseChart::class.java))
            }
        }
    }

    companion object{
        const val IS_FROM_TRANS = "IS_FROM_TRANS"
        fun newIntent(context: Context, isFromTrans: Boolean =false): Intent{
            val intent = Intent(context, ChooseFeature::class.java).putExtra(IS_FROM_TRANS, isFromTrans)
            if (isFromTrans)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK)

            return intent
        }
    }
}