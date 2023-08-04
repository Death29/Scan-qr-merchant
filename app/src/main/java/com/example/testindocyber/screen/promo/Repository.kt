package com.example.testindocyber.screen.promo

import com.example.testindocyber.model.network.ApiServices
import com.example.testindocyber.model.network.ResponsePromoItem
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getListPromo(): Response<ArrayList<ResponsePromoItem>> = apiServices.getListPromo()
}