package com.app.walktopia

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import java.util.*
import java.util.concurrent.TimeUnit

class ReceiveOTP : AppCompatActivity() {


    var userno: TextView? = null
    private var inputotp: EditText? = null
    private var verificationid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_otp)

        userno = findViewById(R.id.notaken)
        inputotp = findViewById(R.id.inputotp)


        val progressBar = findViewById<ProgressBar>(R.id.Progressbarverifyotp)
        val buttonverify = findViewById<Button>(R.id.buttonverify)


        verificationid = getIntent().getStringExtra("verfication")
        buttonverify.setOnClickListener { v ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)

            if (!inputotp!!.getText().toString().trim { it <= ' ' }.isEmpty()) {
                val code = inputotp!!.getText().toString()
                if (verificationid != null) {
                    progressBar.visibility = View.VISIBLE
                    buttonverify.visibility = View.GONE
                    val phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationid!!, code
                    )
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener { task ->
                            progressBar.visibility = View.GONE
                            buttonverify.visibility = View.VISIBLE
                            if (task.isSuccessful) {


                                val intent = Intent(applicationContext, dashboard::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                Toast.makeText(this@ReceiveOTP, "otp received", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    this@ReceiveOTP,
                                    "Enter the Correct otp",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            } else {
                Toast.makeText(this@ReceiveOTP, "Please enter the otp", Toast.LENGTH_SHORT).show()
            }

            findViewById<View>(R.id.textresendotp).setOnClickListener {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + getIntent().getStringExtra("mobile"),
                    60,
                    TimeUnit.SECONDS,
                    this@ReceiveOTP,
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}
                        override fun onVerificationFailed(e: FirebaseException) {
                            Toast.makeText(this@ReceiveOTP, e.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCodeSent(
                            newVerification: String,
                            forceResendingToken: PhoneAuthProvider.ForceResendingToken
                        ) {
                            verificationid = newVerification
                            Toast.makeText(this@ReceiveOTP, "OTP sent again", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                )
            }


        }


    }
}