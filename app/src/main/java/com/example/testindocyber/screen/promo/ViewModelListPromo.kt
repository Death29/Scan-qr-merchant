package com.example.testindocyber.screen.promo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testindocyber.model.network.ResponsePromoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelListPromo @Inject constructor(private val repo: Repository): ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()

    val listPromo = MutableLiveData<ArrayList<ResponsePromoItem>>()

    fun getDataPromo()= viewModelScope.launch {
        loading.postValue(true)

        val response =  repo.getListPromo()
        if (response.isSuccessful) {
            listPromo.postValue(response.body())
            loading.postValue(false)
        } else{
            errorMsg.postValue(response.message())
            loading.postValue(false)
        }

    }
}