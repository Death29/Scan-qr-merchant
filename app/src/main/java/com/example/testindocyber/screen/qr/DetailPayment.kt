package com.example.testindocyber.screen.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.IntentCompat
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityDetailPaymentBinding
import com.example.testindocyber.model.network.room.InformationUser
import com.example.testindocyber.model.network.room.Payment
import com.example.testindocyber.screen.ChooseFeature
import com.example.testindocyber.screen.qr.HomeViewUser.Companion.DATA_SAVING
import com.example.testindocyber.screen.qr.util.ViewModelDataPayment
import com.example.testindocyber.screen.qr.util.ViewModelDataUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPayment : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPaymentBinding

    private val viewModelUser: ViewModelDataUser by viewModels()
    private val viewModelListPayment: ViewModelDataPayment by viewModels()
    private var dataPaymentList = arrayListOf<Payment>()

    lateinit var dataUser: InformationUser

    private val dataIntent by lazy {
        intent.getStringExtra(QrScanner.DATA_SCANNER)
    }
    private val dataSaving by lazy {
        intent.getDoubleExtra(DATA_SAVING, 0.0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            val nominal= dataIntent?.substringAfter("TEST.")
            tvSavings.text = dataSaving.toString()
            tvValueBank.text = dataIntent?.subSequence(0, 3).toString()
            tvValueId.text = dataIntent?.subSequence(4, 13).toString()
            tvValueMerchant.text = dataIntent?.subSequence(15, 33).toString()
            tvValueNominal.text = nominal

            viewModelListPayment.dataPayment.observe(this@DetailPayment){
                dataPaymentList =it as ArrayList<Payment>

                Log.d("data list", "first: null, data: $it")
            }

            viewModelUser.dataUser.observe(this@DetailPayment) {
                dataUser = it
            }

            btnPay.setOnClickListener {
                dataPaymentList.add(Payment(id = 0, nameTransaction = "Transfer", amount = dataIntent?.substringAfter("TEST.")?.toDouble()!!))
                viewModelListPayment.insertPayment(dataPaymentList)

                val updateDataUser = InformationUser(id = dataUser.id, saving = dataUser.saving.minus(dataIntent?.substringAfter("TEST.")?.toDouble()!!))
                viewModelUser.updateDataUser(updateDataUser)
                Log.d("Update Data user", "$updateDataUser")

                Toast.makeText(this@DetailPayment,"Success Transaction", Toast.LENGTH_LONG).show()
                startActivity(ChooseFeature.newIntent(this@DetailPayment, true))
            }
        }
    }
}