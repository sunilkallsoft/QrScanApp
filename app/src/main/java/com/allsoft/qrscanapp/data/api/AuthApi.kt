package com.allsoft.qrscanapp.data.api

import com.allsoft.qrscanapp.Urls
import com.allsoft.qrscanapp.data.DefaultResponse
import com.allsoft.qrscanapp.model.Visitor
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(Urls.GENERATE_OTP)
    suspend fun generateOTP(@Body requestBody: RequestBody) : DefaultResponse<String>

    @POST(Urls.VALIDATE_OTP)
    suspend fun validateOTP(@Body requestBody: RequestBody) : DefaultResponse<Visitor>

    @POST(Urls.REGISTER_VISITOR)
    suspend fun registerVisitor(@Body requestBody: RequestBody) : DefaultResponse<Visitor>
}