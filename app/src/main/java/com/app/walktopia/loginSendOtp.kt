package com.app.walktopia

import android.bluetooth.BluetoothDevice.EXTRA_NAME
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import java.util.concurrent.TimeUnit

class loginSendOtp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_send_otp)



        val inputmobile = findViewById<EditText>(R.id.editTextPhone3)
        val buttongetotp = findViewById<Button>(R.id.button2)


        val progressBar = findViewById<ProgressBar>(R.id.progressbarforotp)




        buttongetotp.setOnClickListener { v ->
            val imm =
                applicationContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)

            if (!inputmobile.text.toString().trim { it <= ' ' }.isEmpty()) {
                if (inputmobile.text.toString().trim { it <= ' ' }.length == 10) {


                    progressBar.visibility = View.VISIBLE
                    buttongetotp.visibility = View.INVISIBLE
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputmobile.text.toString(),
                        60,
                        TimeUnit.SECONDS,
                        this@loginSendOtp,
                        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                            }

                            override fun onVerificationFailed(e: FirebaseException) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                                Toast.makeText(this@loginSendOtp, e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onCodeSent(
                                verficationid: String,
                                forceResendingToken: PhoneAuthProvider.ForceResendingToken
                            ) {
                                progressBar.visibility = View.GONE
                                buttongetotp.visibility = View.VISIBLE
                                val intent = Intent(applicationContext, ReceiveOTP::class.java)
                                val name = inputmobile.text.toString()
                                intent.putExtra(EXTRA_NAME, name)
                                intent.putExtra("mobile", inputmobile.text.toString())
                                intent.putExtra("verfication", verficationid)
                                startActivity(intent)
                            }
                        }
                    )
                } else {
                    Toast.makeText(
                        this@loginSendOtp,
                        "Please enter correct number",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@loginSendOtp, "Enter Mobile number", Toast.LENGTH_SHORT).show()
            }
        }

    }
}