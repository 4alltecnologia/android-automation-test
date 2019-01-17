package com.fourall.aat.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import com.fourall.aat.Application
import com.fourall.aat.R
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.local.UserLocalDataSource
import com.fourall.aat.repositories.UserDataRepository
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {

    private val userRepository: UserRepository by lazy {
        UserDataRepository(UserLocalDataSource(Application.database.userDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        title = getString(R.string.app_name)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userId = intent.getLongExtra("id", 0)

        GlobalScope.launch {

            val user = withContext(Dispatchers.Default) {
                userRepository.getUserById(userId)
            }

            val textToDisplay = if (user != null) {
                getString(R.string.your_name, user.name).plus(
                    getString(
                        R.string.your_age,
                        user.age
                    )
                )
            } else {
                getString(R.string.user_not_found)
            }

            tvResult.text = textToDisplay

            launch(Dispatchers.Main) {

                Handler().post { lottieAnimationResult.playAnimation() }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun finish() {

        setResult(RESULT_OK)

        super.finish()
    }
}