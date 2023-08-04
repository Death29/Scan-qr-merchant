package com.example.testindocyber.screen.qr

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.testindocyber.databinding.ActivityHomeViewUserBinding
import com.example.testindocyber.model.network.room.InformationUser
import com.example.testindocyber.screen.qr.util.ViewModelDataUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewUser : AppCompatActivity() {
    private lateinit var binding: ActivityHomeViewUserBinding
    private val isFromChoose: Boolean by lazy {
        intent.getBooleanExtra(IS_FROM_CHOOSE, true)
    }

    private val viewModelUser: ViewModelDataUser by viewModels()
    private var saving: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeViewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {

            if (isFromChoose){
                val dataEntryUser = InformationUser(id=0, saving = 1000000.0)
                viewModelUser.insertDataUser(dataEntryUser)
            }

            viewModelUser.dataUser.observe(this@HomeViewUser){
                tvNominal.text = it.saving.toString()
                saving = it.saving
            }

            btnScan.setOnClickListener {
                startActivity(Intent(this@HomeViewUser, QrScanner::class.java).putExtra(DATA_SAVING, saving))
            }
        }
    }

    companion object{
        const val IS_FROM_CHOOSE = "IS_FROM_CHOOSE"
        const val DATA_SAVING = "DATA_SAVINGS"

        fun newIntent(context: Context, isFromChoose: Boolean = true): Intent {
            return Intent(context, HomeViewUser::class.java).putExtra(IS_FROM_CHOOSE, isFromChoose)
        }
    }
}