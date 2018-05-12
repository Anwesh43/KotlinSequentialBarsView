package com.example.sequentialbarsview

/**
 * Created by anweshmishra on 13/05/18.
 */

import android.app.Activity
import android.graphics.*
import android.content.*
import android.view.View
import android.view.MotionEvent

class SequentialsBarView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    prevScale = scales[j]
                }
                dir = 0f
                stopcb(scales[j])
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator (var view : View, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch (ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class SequentialsBar (var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            paint.color = Color.parseColor("#448AFF")
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val barW : Float = (w/2)/state.scales.size
            for (i in 0..(state.scales.size-1)) {
                var x : Float = -barW/2 + (w/2 + barW/2) * state.scales[i]
                for(j in i+1..state.scales.size-1) {
                    x += barW * state.scales[j]
                }
                drawSaveRestore(canvas) {
                    it.translate(x, h/2)
                    it.drawRect(RectF(-barW/4, -h/6, barW/4, h/6), paint)
                }

            }
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : View) {

        private val sequentialsBar : SequentialsBar = SequentialsBar(0)

        private val animator : Animator = Animator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            sequentialsBar.draw(canvas, paint)
            animator.animate {
                sequentialsBar.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            sequentialsBar.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : SequentialsBarView {
            val view : SequentialsBarView = SequentialsBarView(activity)
            activity.setContentView(view)
            return view
        }
    }
}

inline fun drawSaveRestore(canvas : Canvas, body : (Canvas) -> Unit) {
    canvas.save()
    body(canvas)
    canvas.restore()
}
