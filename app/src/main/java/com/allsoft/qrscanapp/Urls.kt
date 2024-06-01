package com.allsoft.qrscanapp

import com.allsoft.qrscanapp.utils.AppConstants

object Urls {
    @JvmField
    val BASE_URL = BuildConfig.BASE_URL

    const val GENERATE_OTP = AppConstants.GENERATE_OTP_URL
    const val VALIDATE_OTP = AppConstants.VALIDATE_OTP_URL
    const val REGISTER_VISITOR = AppConstants.REGISTER_VISITOR_URL
    const val GET_DISTRICT_URL = AppConstants.GET_DISTRICT
    const val GET_BLOCK_URL = AppConstants.GET_BLOCK
    const val GET_GP_URL = AppConstants.GET_GP
    const val GET_VILLAGE_URL = AppConstants.GET_VILLAGE
    const val GET_CIVIC_BODIES_URL = AppConstants.GET_CIVIC_BODIES
    const val GET_WARD_URL = AppConstants.GET_WARD
    const val PASS_DETAIL_URL = AppConstants.PASS_DETAIL

}