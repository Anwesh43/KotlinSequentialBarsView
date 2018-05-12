package com.example.anweshmishra.kotlinsequentialbarsview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.sequentialbarsview.SequentialsBarView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SequentialsBarView.create(this)
    }
}
