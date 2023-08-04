package com.example.testindocyber.screen.promo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testindocyber.databinding.ActivityMainBinding
import com.example.testindocyber.model.network.ResponsePromoItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapterPromo by lazy {
        AdapterListPromo(this@MainActivity) { item -> setToDetail(item) }
    }

    private val viewModelPromo: ViewModelListPromo by viewModels()

    private fun setToDetail(item: ResponsePromoItem) {
        startActivity(Intent(this@MainActivity, DetailPromo::class.java).putExtra("DATA", item))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            rvListPromo.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = adapterPromo
                setHasFixedSize(true)
            }

            viewModelPromo.getDataPromo()
            viewModelPromo.listPromo.observe(this@MainActivity) {
                adapterPromo.setDataPromo(it)

                if (it.isNotEmpty()) rvListPromo.isVisible
                else rvListPromo.isGone
            }

            viewModelPromo.loading.observe(this@MainActivity) {
                if (it) {
                    Log.d("loading", "Show")
                    loadingBar.isVisible
                } else {
                    Log.d("loading", "Gone")
                    loadingBar.visibility = View.GONE
                    rvListPromo.visibility = View.VISIBLE
                }
            }
            viewModelPromo.errorMsg.observe(this@MainActivity) {
                if (it.isNotEmpty()) {
                    Log.d("Get Error", "Error is: $it")
                    rvListPromo.isGone
                    constErrorGetData.isVisible
                } else {
                    Log.d("Get Nothing Error", "Nothing Error")
                    rvListPromo.isVisible
                    constErrorGetData.isGone
                }
            }
        }
    }
}