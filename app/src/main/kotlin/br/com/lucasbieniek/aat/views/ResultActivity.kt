package br.com.lucasbieniek.aat.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import br.com.lucasbieniek.aat.R
import br.com.lucasbieniek.aat.models.Person
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // toolbar title
        title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val person = intent.getParcelableExtra<Person>("person")

        val textToDisplay = getString(R.string.your_name, person.name).plus(
            getString(
                R.string.your_age,
                person.age.toString()
            )
        )

        tvResult.text = textToDisplay

        Handler().post { lottieAnimationResult.playAnimation() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}