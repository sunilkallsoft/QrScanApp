package com.allsoft.qrscanapp.data.api

import com.allsoft.qrscanapp.Urls
import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.model.PlaceDataModel
import com.allsoft.qrscanapp.model.Visitor
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MasterApi {

    @GET(Urls.GET_DISTRICT_URL)
    suspend fun getDistrict() : DefaultResponse<ArrayList<PlaceDataModel>>


    @GET(Urls.GET_BLOCK_URL)
    suspend fun getBlock(@Query("id") id : Int) : DefaultResponse<ArrayList<PlaceDataModel>>

    @GET(Urls.GET_GP_URL)
    suspend fun getGP(@Query("id") id : Int) : DefaultResponse<ArrayList<PlaceDataModel>>

    @GET(Urls.GET_VILLAGE_URL)
    suspend fun getVillage(@Query("id") id : Int) : DefaultResponse<ArrayList<PlaceDataModel>>

    @GET(Urls.GET_CIVIC_BODIES_URL)
    suspend fun getCivicBodies(@Query("id") id : Int) : DefaultResponse<ArrayList<PlaceDataModel>>

    @GET(Urls.GET_WARD_URL)
    suspend fun getWard(@Query("id") id : Int) : DefaultResponse<ArrayList<PlaceDataModel>>


    @POST(Urls.PASS_DETAIL_URL)
    suspend fun getPassDetail(@Body requestBody: RequestBody) : DefaultResponse<Visitor>
}