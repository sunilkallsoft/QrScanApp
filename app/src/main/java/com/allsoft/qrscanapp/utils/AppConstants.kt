package com.allsoft.qrscanapp.utils

class AppConstants {
    companion object{
        const val OTP_TOKEN = "otp_token"
        const val KEY_ACCESS_TOKEN = "access_token"


        const val GENERATE_OTP_URL = "/api/auth/generateOTP"
        const val VALIDATE_OTP_URL = "/api/auth/validateOTP"
        const val REGISTER_VISITOR_URL = "/api/auth/registerVisitor"
        const val GET_DISTRICT = "/api/appmasterlist/getDistrict"
        const val GET_BLOCK = "/api/appmasterlist/getBlock"
        const val GET_GP = "/api/appmasterlist/getGP"
        const val GET_VILLAGE = "/api/appmasterlist/getVillage"
        const val GET_CIVIC_BODIES = "/api/appmasterlist/getNNN"
        const val GET_WARD = "/api/appmasterlist/getWard/"
        const val PASS_DETAIL = "/api/reports/passDetails"
    }
}