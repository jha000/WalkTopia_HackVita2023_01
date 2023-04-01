package com.app.walktopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class badges : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_badges)

        val close = findViewById<ImageView>(R.id.back)

        close.setOnClickListener{
            super.onBackPressed()
        }


    }
}