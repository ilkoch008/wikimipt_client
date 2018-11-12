package com.example.wikimipt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.net.sip.SipErrorCode.TIME_OUT
import android.content.Intent
import android.os.Handler

var back_press : Boolean = false

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, Second_Activity::class.java)

        object : CountDownTimer(2000, 10) {
            override fun onFinish() {
                startActivity(intent)
                finish()
            }
            override fun onTick(millisUntilFinished: Long) {
                if(back_press){
                    cancel()
                    back_press = false
                    finish()
                }
            }
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        back_press = true
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        back_press = true
    }
}