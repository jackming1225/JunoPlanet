package com.planetjuno

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_video.*
import kotlin.math.max
import kotlin.math.min


class ImageViewerActivity : AppCompatActivity() {

    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_video)
        val imageUrl = intent.extras?.getString("hdUrl") ?: ""
        loadImage(imageUrl)
    }

    private fun loadImage(imageUrl: String) {
        Picasso.get()
            .load(imageUrl)
            .into(ivZoomImage, object : Callback {
                override fun onSuccess() {
                    mScaleGestureDetector =
                        ScaleGestureDetector(this@ImageViewerActivity, ScaleListener())
                }

                override fun onError(e: Exception?) {
                    //to-do
                }
            })
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mScaleGestureDetector?.onTouchEvent(event)
        return true
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            mScaleFactor *= (detector?.scaleFactor ?: 0.0f)
            mScaleFactor = max(
                0.1f,
                min(mScaleFactor, 10.0f)
            );
            ivZoomImage.scaleX = mScaleFactor;
            ivZoomImage.scaleY = mScaleFactor;
            return true
        }
    }
}