package com.mif50.instagramimagefilter.Interface

interface EditImageFilterListener {
    fun onBrightnessChanged(brightness: Int)
    fun onSaturationChanged(saturation: Float)
    fun onConstrainChanged(constraint: Float)
    fun onEditStarted()
    fun onEditCompleted()
}