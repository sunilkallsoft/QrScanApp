package com.allsoft.qrscanapp.data.source

import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.data.api.AuthApi
import com.allsoft.qrscanapp.model.Visitor
import com.allsoft.qrscanapp.utils.Resource
import com.allsoft.qrscanapp.utils.ResponseHandler
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class LoginRepo  (private val authApi: AuthApi,
                  private val responseHandler : ResponseHandler
) {

    suspend fun generateOTP(mobileNo : String) : Resource<DefaultResponse<String>> {
        return try {
            val jsonObject = JSONObject()
            jsonObject.put("mobile_number", mobileNo)
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),(jsonObject).toString())
            val response = authApi.generateOTP(requestBody)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun validateOTP(mobileNo : String, otp : String) : Resource<DefaultResponse<Visitor>> {
        return try {
            val jsonObject = JSONObject()
            jsonObject.put("mobile_number", mobileNo)
            jsonObject.put("otp", otp)
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),(jsonObject).toString())
            val response = authApi.validateOTP(requestBody)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }

    suspend fun registerVisitor(visitorsName : String, fathersName : String, gender : String, age : Int, mobileNo : String, meetingObjective : String, meetingDate : String, assembly : String, district : String, urbanRural : String, noOfPersonWith : String) : Resource<DefaultResponse<Visitor>> {
        return try {
            val jsonObject = JSONObject()
            jsonObject.put("visitor_name", visitorsName)
            jsonObject.put("fathers_name", fathersName)
            jsonObject.put("gender", gender)
            jsonObject.put("age", age)
            jsonObject.put("mobile_number", mobileNo)
            jsonObject.put("meeting_objective", meetingObjective)
            jsonObject.put("meeting_date", meetingDate)
            jsonObject.put("assembly", assembly)
            jsonObject.put("district", district)
            jsonObject.put("urban_rural", urbanRural)
            jsonObject.put("no_of_person_with", noOfPersonWith)
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),(jsonObject).toString())
            val response = authApi.registerVisitor(requestBody)
            responseHandler.handleSuccess(response)
        }catch (e : Exception){
            responseHandler.handleException(e)
        }
    }
}