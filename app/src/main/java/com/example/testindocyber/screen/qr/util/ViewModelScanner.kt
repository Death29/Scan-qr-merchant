package com.example.testindocyber.screen.qr.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testindocyber.model.network.room.DataUserRepo
import com.example.testindocyber.model.network.room.InformationUser
import com.example.testindocyber.model.network.room.ListPaymentRepo
import com.example.testindocyber.model.network.room.Payment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDataUser @Inject constructor(private val repoUser:DataUserRepo ): ViewModel() {
    val dataUser : LiveData<InformationUser> = repoUser.getDataUser
    fun insertDataUser(data: InformationUser) = viewModelScope.launch {
        repoUser.insertDataUser(data)
    }
    fun updateDataUser(data: InformationUser) = viewModelScope.launch {
        repoUser.updateDataUser(data)
    }
}

@HiltViewModel
class ViewModelDataPayment @Inject constructor(private val repoPayment: ListPaymentRepo): ViewModel(){
    val dataPayment: LiveData<List<Payment>> = repoPayment.getAllPayment
    fun insertPayment(payment: List<Payment>) = viewModelScope.launch {
        repoPayment.insertDataPayment(payment)
    }
}