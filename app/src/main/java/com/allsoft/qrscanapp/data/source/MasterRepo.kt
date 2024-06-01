package com.allsoft.qrscanapp.data.source

import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.data.api.MasterApi
import com.allsoft.qrscanapp.model.PlaceDataModel
import com.allsoft.qrscanapp.model.Visitor
import com.allsoft.qrscanapp.utils.Resource
import com.allsoft.qrscanapp.utils.ResponseHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class MasterRepo (private val masterApi: MasterApi,
                  private val responseHandler : ResponseHandler
) {

    suspend fun getDistrict() : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getDistrict()
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }


    suspend fun getBlock(id : Int) : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getBlock(id)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun getGP(id : Int) : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getGP(id)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun getVillage(id : Int) : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getVillage(id)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun getCivicBodies(id : Int) : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getCivicBodies(id)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun getWard(id : Int) : Resource<DefaultResponse<ArrayList<PlaceDataModel>>> {
        return try {
            val response = masterApi.getWard(id)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }


    suspend fun getPassDetail(passID : Int) : Resource<DefaultResponse<Visitor>> {
        return try {
            val jsonObject = JSONObject()
            jsonObject.put("pass_id", passID)
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),(jsonObject).toString())
            val response = masterApi.getPassDetail(requestBody)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }
}