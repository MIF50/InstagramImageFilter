package com.mif50.instagramimagefilter.utlis

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream
import java.lang.Exception

object BitmapUtils {

    fun getBitmapFromAssets(context: Context, fileName: String, width: Int, height: Int): Bitmap? {
        val assetManager = context.assets
        val inputStream: InputStream
        val bitmap: Bitmap? = null
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            inputStream = assetManager.open(fileName)
            options.inSampleSize = calculateInSampleSize(options,width,height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        width: Int,
        height: Int
    ): Int {

        return 0

    }
}