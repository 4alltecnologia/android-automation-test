package com.fourall.aat.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fourall.aat.R
import com.fourall.aat.extensions.fadeVisibility
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME_IN_MILLISECONDS = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fourallLogoImageView.fadeVisibility(true)

        splashTitleDescriptionTextView.fadeVisibility(true)

        Handler().postDelayed({

            val intent = Intent(this, UsersActivity::class.java)

            startActivity(intent)

            finish()
        }, SPLASH_TIME_IN_MILLISECONDS)
    }
}