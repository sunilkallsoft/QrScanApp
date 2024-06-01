package com.allsoft.qrscanapp.data.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.model.Visitor
import com.allsoft.qrscanapp.data.source.LoginRepo
import com.allsoft.qrscanapp.data.source.MasterRepo
import com.allsoft.qrscanapp.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepo : LoginRepo) : ViewModel()  {
    val generateOTPLiveData = MediatorLiveData<Resource<DefaultResponse<String>>>()

    fun generateOTP(mobileNo : String){
        generateOTPLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = authRepo.generateOTP(mobileNo)
            generateOTPLiveData.postValue(response)
        }
    }

    val validateOTPLiveData = MediatorLiveData<Resource<DefaultResponse<Visitor>>>()

    fun validateOTP(mobileNo : String, otp: String){
        validateOTPLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = authRepo.validateOTP(mobileNo, otp)
            validateOTPLiveData.postValue(response)
        }
    }

    val registerVisitorLiveData = MediatorLiveData<Resource<DefaultResponse<Visitor>>>()

    fun registerVisitor(visitorsName : String, fathersName : String, gender : String, age : Int, mobileNo : String, meetingObjective : String, meetingDate : String, assembly : String, district : String, urbanRural : String, noOfPersonWith : String){
        registerVisitorLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = authRepo.registerVisitor(visitorsName, fathersName, gender, age, mobileNo, meetingObjective, meetingDate, assembly, district, urbanRural, noOfPersonWith)
            registerVisitorLiveData.postValue(response)
        }
    }
}