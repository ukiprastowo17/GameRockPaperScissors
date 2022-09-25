package com.blackhole.gamerockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blackhole.gamerockpaperscissors.R
import com.blackhole.gamerockpaperscissors.databinding.ActivitySplashBinding

class SplashScreen : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.imgSplash.postDelayed({

            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}