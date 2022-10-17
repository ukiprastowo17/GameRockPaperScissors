package com.blackhole.gamerockpaperscissors.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.blackhole.gamerockpaperscissors.databinding.ActivitySplashBinding
import com.blackhole.gamerockpaperscissors.ui.onboarding.OnboardingActivity


class SplashActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null

    private val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTimerSplashScreen()

        binding.imgSplash.load("https://i.ibb.co/HC5ZPgD/splash-screen1.png") {
            crossfade(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //destroy the timer for case new activity still opened when splashscreen destoryed
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }

    private fun setTimerSplashScreen() {
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        timer?.start()
    }

}