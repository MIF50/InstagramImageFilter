package com.mif50.instagramimagefilter.utlis

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import javax.xml.transform.Source

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
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(inputStream,null,options)
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.d(e.message!!)
        }

        return null
    }

    fun getBitmapFromGallery(context: Context, path: Uri, width: Int,height: Int): Bitmap {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = context.contentResolver.query(path,filePathColumn,null, null, null)
        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picturePath,options)
        options.inSampleSize = calculateInSampleSize(options,width,height)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(picturePath, options)
    }

    fun insertImage(contextResolver: ContentResolver, source: Bitmap? ,title: String, description: String): String? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,title)
        values.put(MediaStore.Images.Media.DISPLAY_NAME,title)
        values.put(MediaStore.Images.Media.DESCRIPTION,description)
        values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpeg")
        values.put(MediaStore.Images.Media.DATE_ADDED,System.currentTimeMillis())
        values.put(MediaStore.Images.Media.DATE_TAKEN,System.currentTimeMillis())

        var url: Uri? = null
        var stringUrl : String? = null

        try {
            url = contextResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
            if (source != null){
                val imageOut = contextResolver.openOutputStream(url!!)
                try {
                    source.compress(Bitmap.CompressFormat.JPEG,50, imageOut)
                }
                finally {
                    imageOut!!.close()
                }

                val id = ContentUris.parseId(url)
                val miniThumb = MediaStore.Images.Thumbnails.getThumbnail(contextResolver,id,MediaStore.Images.Thumbnails.MINI_KIND,
                    null)
                storeThumbnail(contextResolver,miniThumb,id,50f,50f,MediaStore.Images.Thumbnails.MICRO_KIND)
            } else {
                contextResolver.delete(url!!,null,null)
                url = null
            }
        } catch (e: Exception) {
            if (url != null) {
                stringUrl = url.toString()
            }
            e.printStackTrace()
        }

        if (url != null) {
            contextResolver.delete(url,null,null)
        }
        return  stringUrl
    }

    private fun storeThumbnail(
        contextResolver: ContentResolver,
        source: Bitmap?,
        id:Long,
        width: Float,
        height: Float,
        microKind: Int
    ): Bitmap? {

        val matrix = Matrix()
        val scaleX = width/source!!.width
        val scaleY = height/ source!!.height

        matrix.setScale(scaleX,scaleY)
        val thumb = Bitmap.createBitmap(source,0,0,source.width,source.height,matrix,true)

        val values = ContentValues(4)
        values.put(MediaStore.Images.Thumbnails.KIND,microKind)
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID,id.toInt())
        values.put(MediaStore.Images.Thumbnails.HEIGHT,thumb.height)
        values.put(MediaStore.Images.Thumbnails.WIDTH,thumb.width)

        val url = contextResolver.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,values)
        try {
            val thumbOut = contextResolver.openOutputStream(url!!)
            thumb.compress(Bitmap.CompressFormat.JPEG,100,thumbOut)
            thumbOut!!.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null

        } catch (e: IOException){
            e.printStackTrace()
            return null
        }

        return null

    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        regWidth: Int,
        regHeight: Int
    ): Int {

        val height = options.outHeight
        val width  = options.outWidth
        var inSampleSize = 1

        if (height > regHeight || width > regWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= regHeight && halfWidth / inSampleSize >= regWidth){
                inSampleSize *= 2
            }
        }

        return inSampleSize

    }
}