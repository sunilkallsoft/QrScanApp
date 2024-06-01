package com.allsoft.qrscanapp.data.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.model.Visitor
import com.allsoft.qrscanapp.data.source.MasterRepo
import com.allsoft.qrscanapp.model.PlaceDataModel
import com.allsoft.qrscanapp.utils.Resource
import kotlinx.coroutines.launch

class MasterViewModel(private val masterRepo : MasterRepo) : ViewModel() {




    val districtLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getDistrict(){
        districtLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getDistrict()
            districtLiveData.postValue(response)
        }
    }

    val blockLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getBlock(id : Int){
        blockLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getBlock(id)
            blockLiveData.postValue(response)
        }
    }

    val gpLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getGP(id : Int){
        gpLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getGP(id)
            gpLiveData.postValue(response)
        }
    }

    val villageLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getVillage(id : Int){
        villageLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getVillage(id)
            villageLiveData.postValue(response)
        }
    }

    val civicBodiesLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getCivicBodies(id : Int){
        civicBodiesLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getCivicBodies(id)
            civicBodiesLiveData.postValue(response)
        }
    }

    val wardLiveData = MediatorLiveData<Resource<DefaultResponse<ArrayList<PlaceDataModel>>>>()

    fun getWard(id : Int){
        wardLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getWard(id)
            wardLiveData.postValue(response)
        }
    }

    val passDetailLiveData = MediatorLiveData<Resource<DefaultResponse<Visitor>>>()

    fun getPassDetail(passID : Int){
        passDetailLiveData.postValue(Resource.loading(null))
        viewModelScope.launch {
            val response = masterRepo.getPassDetail(passID)
            passDetailLiveData.postValue(response)
        }
    }
}