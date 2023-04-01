package com.app.walktopia

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {

    var lottie: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lottie = findViewById(R.id.lottie)

        val SPLASH_SCREEN_TIME_OUT = 3000
        Handler().postDelayed({
            val i = Intent(
                this@MainActivity,
                dashboard::class.java
            )
            startActivity(i)
            finish()
        }, SPLASH_SCREEN_TIME_OUT.toLong())

    }
}