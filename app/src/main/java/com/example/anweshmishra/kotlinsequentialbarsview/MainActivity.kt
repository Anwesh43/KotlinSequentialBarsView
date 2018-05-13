package com.example.anweshmishra.kotlinsequentialbarsview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.sequentialbarsview.SequentialsBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view : SequentialsBarView = SequentialsBarView.create(this)
        view.addOnMoveListener {
            Toast.makeText(this, "$it bar is moved", Toast.LENGTH_SHORT).show()
        }
        fullScreen()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
