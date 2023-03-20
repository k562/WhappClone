package com.example.whappclone.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.whappclone.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)


        Handler(Looper.getMainLooper()).postDelayed({

        val intent = Intent(this@SplashScreenActivity , NumberActivity::class.java)
        startActivity(intent)
        finish()
        },4000)
    }
}