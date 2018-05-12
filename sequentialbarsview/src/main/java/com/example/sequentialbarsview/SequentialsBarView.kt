package com.example.sequentialbarsview

/**
 * Created by anweshmishra on 13/05/18.
 */

import android.graphics.*
import android.content.*
import android.view.View
import android.view.MotionEvent

class SequentialBarsView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

}