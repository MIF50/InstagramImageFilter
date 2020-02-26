package com.mif50.instagramimagefilter.utlis

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import java.lang.Exception

class NonSwipeableViewPager : ViewPager{

    constructor(context: Context): super(context){
        setMyScroller()
    }

    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet){
        setMyScroller()
    }

    private fun setMyScroller() {
        try {
            val viewpager = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this,MyScroller(context))
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}
class MyScroller(context: Context): Scroller(context,DecelerateInterpolator()){
    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, 400)
    }

}