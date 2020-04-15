package com.mif50.instagramimagefilter.Interface

interface EditImageFilterListener {
    fun onBrightnessChanged(brightness: Int)
    fun onSaturationChanged(saturation: Int)
    fun onConstrainChanged(constraint: Int)
    fun onEditStarted()
    fun onEditCompleted()
}