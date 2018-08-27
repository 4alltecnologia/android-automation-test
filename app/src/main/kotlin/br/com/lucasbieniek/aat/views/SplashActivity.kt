package br.com.lucasbieniek.aat.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.com.lucasbieniek.aat.R
import br.com.lucasbieniek.aat.extensions.fadeVisibility
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    companion object {
        const val splashTime = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ivSplashLogo.fadeVisibility(true)

        tvSplashMessage.fadeVisibility(true)

        Handler().postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, splashTime)
    }
}