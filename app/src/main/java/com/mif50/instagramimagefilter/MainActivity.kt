package com.mif50.instagramimagefilter

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mif50.instagramimagefilter.utlis.BitmapUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_content.*

class MainActivity : AppCompatActivity() {


    var originalImage: Bitmap? = null
    lateinit var  filteredImage: Bitmap
    lateinit var finalImage: Bitmap

    lateinit var filterListFragment: FilterListFragment
    lateinit var editImageFragment: EditImageFragment

    var brightnessFinal = 0
    var saturationFinal = 1.0f
    var contrastFinal = 1.0f

    companion object {
        const val IMAGE_NAME = "flash.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // set Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Instagram Filter"

        loadImage()
    }

    private fun loadImage(){
        originalImage = BitmapUtils.getBitmapFromAssets(this, IMAGE_NAME,300,300)
        filteredImage = originalImage!!.copy(Bitmap.Config.ARGB_8888,true)
        finalImage = originalImage!!.copy(Bitmap.Config.ARGB_8888,true)
        image_preview.setImageBitmap(originalImage)
    }
}
