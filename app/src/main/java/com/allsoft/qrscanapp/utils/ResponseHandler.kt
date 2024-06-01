package com.allsoft.qrscanapp.utils

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {


        Log.e("TAG", "handleException: " + e.stackTrace.toString())
        e.printStackTrace()
//        Log.d("TAG", "handleException: " + e.printStackTrace())
        Log.e("TAG", "handleException: " + e.cause.toString())
        Log.e("TAG", "handleException: " + e.message.toString())


        return when (e) {
            is HttpException -> {

                val errorMessage = if(e.code()!=400 && e.code()!=403){

                    getErrorMessage(e.code(),e)



                } else{
                    val body=e.response()?.errorBody()
                    getErrorJSON(body)
                }
                //Resource.error(getErrorMessage(e.code(), e), null)}


//                Sentry.captureMessage("error Message"+errorMessage)
                Resource.error(errorMessage, null)
            }
//            is SocketTimeoutException -> Resource.error(getErrorMessage(ErrorCodes.SocketTimeOut.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE, e), null)
        }
    }

    private fun getErrorJSON(body: ResponseBody?): String {
        return try{
            val mainObject = JSONObject(body!!.string())

            mainObject.getString("message")

        }
        catch (e:Exception){
            "Something wrong happened"
        }

    }


    private fun getErrorMessage(code: Int, e: Exception): String {
        return when (code) {
            // SocketTimeOut.code -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Not found"
            400 -> e.message.toString()
            429 -> "Try after sometime"
            500 -> "Internal Server Error"
            else -> "Something went wrong"
        }
    }
}