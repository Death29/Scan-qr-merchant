package com.example.testindocyber.screen.promo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityDetailPromoBinding
import com.example.testindocyber.model.network.ResponsePromoItem

class DetailPromo : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPromoBinding
    private lateinit var dataPromo: ResponsePromoItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPromoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBundle()
        initView()
    }

    private fun initBundle() {
        dataPromo = IntentCompat.getParcelableExtra(intent,"DATA", ResponsePromoItem::class.java)!!
    }

    private fun initView() {
        binding.apply {

            tvCountCoupons.text = String.format(getString(R.string.count_coupons), dataPromo.count)
            Glide.with(this@DetailPromo)
                .load(dataPromo.img?.url)
                .into(ivBanner)
            desc.text = dataPromo.desc
            tvNamePromo.text = dataPromo.nama

            ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}