package com.fourall.aat.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import com.fourall.aat.Application
import com.fourall.aat.R
import com.fourall.aat.contract.repositories.UserRepository
import com.fourall.aat.data.di.CommandInjector
import com.fourall.aat.data.local.UserLocalDataSource
import com.fourall.aat.databinding.ActivityResultBinding
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.repositories.UserDataRepository
import com.fourall.aat.viewmodels.ResultViewModel
import com.fourall.aat.viewmodels.factory.ResultViewModelFactory
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {

    private lateinit var activityResultBinding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    private val userRepository: UserRepository by lazy {
        UserDataRepository(UserLocalDataSource(Application.database.userDao()))
    }

    private var userId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        prepareUi()
        prepareViewModel()
    }

    override fun onResume() {

        super.onResume()

        resultViewModel.loadUser(userId)
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

    private fun prepareUi() {

        title = getString(R.string.app_name)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userId = intent.getLongExtra("id", 0)
    }

    private fun prepareViewModel() {

        val resultViewModelFactory = ResultViewModelFactory(
                UserDataRepository(
                        UserLocalDataSource(Application.database.userDao())),
                CommandInjector
        )

        resultViewModel = ViewModelProviders.of(this, resultViewModelFactory)
                .get(ResultViewModel::class.java)

        resultViewModel.apply {

            command.removeObservers(this@ResultActivity)

            command.observe(this@ResultActivity, Observer { cmd ->

                cmd?.let { triggerCommand(it) }
            })
        }
    }

    private fun triggerCommand(command: GenericCommand) {

        when (command) {

            is ResultViewModel.Command.ShowUser -> {

                val textToDisplay = if (command.user != null) {
                    getString(R.string.your_name, command.user.name).plus(
                            getString(
                                    R.string.your_age,
                                    command.user.age
                            )
                    )
                } else {
                    getString(R.string.user_not_found)
                }

                tvResult.text = textToDisplay

                lottieAnimationResult.playAnimation()
            }
        }
    }
}