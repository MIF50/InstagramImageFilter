package com.mif50.instagramimagefilter.utlis

import android.util.Log

object Logger {
    val tag = "DEBUG"
    fun d(message: String){
        Log.d(tag,message)
    }
}