package com.allsoft.qrscanapp.data

class DefaultResponse<T>(
    val status : Boolean,
    val message : String,
    val error : String,
    val token : String,
    val data : T
)