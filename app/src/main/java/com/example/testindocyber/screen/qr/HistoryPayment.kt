package com.example.testindocyber.screen.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testindocyber.databinding.ActivityHistoryPaymentBinding
import com.example.testindocyber.model.network.room.Payment
import com.example.testindocyber.screen.qr.util.AdapterHistory
import com.example.testindocyber.screen.qr.util.ViewModelDataPayment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPayment : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryPaymentBinding
    private val viewModelPayment : ViewModelDataPayment by viewModels()
    private lateinit var adapterHistory : AdapterHistory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            adapterHistory = AdapterHistory(this@HistoryPayment)

            rvHistory.apply {
                layoutManager = LinearLayoutManager(this@HistoryPayment, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = adapterHistory
            }

            viewModelPayment.dataPayment.observe(this@HistoryPayment){
                if (it.isEmpty()){
                    tvNoData.visibility= View.VISIBLE
                    rvHistory.visibility = View.GONE
                }else{
                    tvNoData.visibility = View.GONE
                    rvHistory.visibility= View.VISIBLE

                    adapterHistory.setDataHistory(it as ArrayList<Payment>)
                }
            }
        }
    }
}