package com.rishiz.institute.data.remote.response

import android.os.Message
import java.lang.Error

data class ApiResponse(
    val statusCode:Int,
    val message: String,
    val error: String
)
