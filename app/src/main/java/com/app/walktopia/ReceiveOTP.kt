package com.app.walktopia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

class ReceiveOTP : AppCompatActivity() {


    var userno: TextView? = null
    private var inputotp: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_otp)

        userno = findViewById(R.id.notaken)
        inputotp = findViewById(R.id.inputotp)


        val progressBar = findViewById<ProgressBar>(R.id.Progressbarverifyotp)
        val buttonverify = findViewById<Button>(R.id.buttonverify)




    }
}