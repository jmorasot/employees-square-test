package com.juanmora.square.employeelist.features.splash.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.juanmora.square.employeelist.features.core.extensions.launchActivityAndFinish
import com.juanmora.square.employeelist.features.home.view.activities.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashActivity = installSplashScreen()
        super.onCreate(savedInstanceState)
        Thread.sleep(MILLIS_TO_WAIT)
        splashActivity.setKeepOnScreenCondition {
            false
        }
        launchActivityAndFinish<MainActivity>()
    }

    companion object {
        private const val MILLIS_TO_WAIT = 1000L
    }
}
