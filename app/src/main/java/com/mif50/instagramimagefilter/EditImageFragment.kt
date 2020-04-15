package com.mif50.instagramimagefilter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.mif50.instagramimagefilter.Interface.EditImageFilterListener
import kotlinx.android.synthetic.main.fragment_edit_image.*


class EditImageFragment : Fragment(), SeekBar.OnSeekBarChangeListener {

    private var listener: EditImageFilterListener? = null

    fun setListener(listener: EditImageFilterListener){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekBarBrightness.max = 200
        seekBarBrightness.progress = 100

        seekBarConstraint.max = 20
        seekBarConstraint.progress = 0

        seekBarSaturation.max = 30
        seekBarSaturation.progress = 10

        seekBarSaturation.setOnSeekBarChangeListener(this)
        seekBarConstraint.setOnSeekBarChangeListener(this)
        seekBarBrightness.setOnSeekBarChangeListener(this)

    }


    fun resetControls(){
        seekBarBrightness.progress = 100
        seekBarConstraint.progress = 0
        seekBarSaturation.progress = 10
    }

    override fun onProgressChanged(seekbar: SeekBar, progress: Int, fromUser: Boolean) {
        listener?.let {
            when (seekbar.id) {
                seekBarBrightness.id -> {
                    it.onBrightnessChanged(progress - 100)
                }
                seekBarConstraint.id -> {
                    val floatValue = 0.10f * (progress + 10)
                    it.onConstrainChanged(floatValue)
                }
                seekBarSaturation.id -> {
                    val floatValue = 0.10f * (progress)
                    it.onSaturationChanged(floatValue)
                }
            }
        }

    }

    override fun onStartTrackingTouch(seekbar: SeekBar?) {
        listener?.onEditStarted()
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        listener?.onEditCompleted()
    }
}
