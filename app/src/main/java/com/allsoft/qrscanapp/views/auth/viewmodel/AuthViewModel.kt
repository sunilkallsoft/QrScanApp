package com.allsoft.qrscanapp.views.auth.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    //set Back press
    private val backPressLiveData = MutableLiveData<Boolean>()
    fun getBackPressed() : MutableLiveData<Boolean> {
        return backPressLiveData
    }
    fun setBackPressed(mapData : Boolean){
        backPressLiveData.postValue(mapData)
    }

    //open registration fragment
    private val registrationFragmentLiveData = MutableLiveData<HashMap<String, Any>>()
    fun getRegistrationFragment() : MutableLiveData<HashMap<String, Any>> {
        return registrationFragmentLiveData
    }
    fun setRegistrationFragment(mapData : HashMap<String, Any>){
        registrationFragmentLiveData.postValue(mapData)
    }

    //open Login fragment
    private val loginFragmentLiveData = MutableLiveData<HashMap<String, Any>>()
    fun getLoginFragment() : MutableLiveData<HashMap<String, Any>> {
        return loginFragmentLiveData
    }
    fun setLoginFragment(mapData : HashMap<String, Any>){
        loginFragmentLiveData.postValue(mapData)
    }


    //open OTP fragment
    private val otpFragmentLiveData = MutableLiveData<HashMap<String, Any>>()
    fun getOTPFragment() : MutableLiveData<HashMap<String, Any>> {
        return otpFragmentLiveData
    }
    fun setOTPFragment(mapData : HashMap<String, Any>){
        otpFragmentLiveData.postValue(mapData)
    }

    //registration success fragment
    private val registrationSuccessLiveData = MutableLiveData<HashMap<String, Any>>()
    fun getRegistrationSuccess() : MutableLiveData<HashMap<String, Any>> {
        return registrationSuccessLiveData
    }
    fun setRegistrationSuccess(mapData : HashMap<String, Any>){
        registrationSuccessLiveData.postValue(mapData)
    }
}