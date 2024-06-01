package com.allsoft.qrscanapp.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.views.auth.AuthActivity
import com.allsoft.qrscanapp.views.dashboard.MainActivity
import java.util.Locale

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val locale = Locale("hi")
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale

        resources.updateConfiguration(config, this.resources?.displayMetrics)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, AuthActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }
}