package com.fourall.aat.views

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.fourall.aat.Application
import com.fourall.aat.R
import com.fourall.aat.data.di.CommandInjector
import com.fourall.aat.data.local.UserLocalDataSource
import com.fourall.aat.databinding.ActivityInputBinding
import com.fourall.aat.extensions.closeKeyboard
import com.fourall.aat.extensions.isKeyboardOpened
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.repositories.UserDataRepository
import com.fourall.aat.viewmodels.InputViewModel
import com.fourall.aat.viewmodels.factory.InputViewModelFactory
import kotlinx.android.synthetic.main.activity_input.*

class InputActivity : BaseActivity() {

    private lateinit var activityInputBinding: ActivityInputBinding
    private lateinit var inputViewModel: InputViewModel

    private var isOkUserName = false
    private var isOkUserAge = false

    private var userId = 0L

    private val RESULT_REQUEST_CODE = 1

    companion object {
        val ARG_USER_ID = "com.fourall.aat.ARG_USER_ID"
        val ARG_USER_NAME = "com.fourall.aat.ARG_USER_NAME"
        val ARG_USER_AGE = "com.fourall.aat.ARG_USER_AGE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        prepareUi()
        prepareListeners()
        prepareViewModel()
    }

    override fun onResume() {

        super.onResume()

        saveButton.isEnabled = isOkUserName && isOkUserAge
        cleanButton.isEnabled = isOkUserName || isOkUserAge
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                finish()
            }
        }
    }

    private fun prepareUi() {

        activityInputBinding = DataBindingUtil.setContentView(this, R.layout.activity_input)

        title = getString(R.string.app_name)

        userId = intent.getLongExtra(ARG_USER_ID, 0L)

        val userName = intent.getStringExtra(ARG_USER_NAME)
        val userAge = intent.getStringExtra(ARG_USER_AGE)

        userNameTextInputEditText.setText(userName)
        userAgeTextInputEditText.setText(userAge)

        isOkUserName = userName != null && userName.isNotEmpty()
        isOkUserAge = userAge != null && userAge.isNotEmpty()

        saveButton.isEnabled = isOkUserName && isOkUserAge
        cleanButton.isEnabled = isOkUserName || isOkUserAge
    }

    private fun prepareListeners() {

        userNameTextInputEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

                isOkUserName = p0?.isNotEmpty() ?: false

                saveButton.isEnabled = isOkUserAge && isOkUserName
                cleanButton.isEnabled = isOkUserName || isOkUserAge
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        userAgeTextInputEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {

                isOkUserAge = p0?.isNotEmpty() ?: false

                saveButton.isEnabled = isOkUserAge && isOkUserName
                cleanButton.isEnabled = isOkUserName || isOkUserAge
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        saveButton.setOnClickListener {

            if (isKeyboardOpened()) closeKeyboard(currentFocus)

            val age = userAgeTextInputEditText.text.toString()
            val name = userNameTextInputEditText.text.toString()

            if (userId == 0L) {
                inputViewModel.saveUser(name, age)
            } else {
                inputViewModel.saveUserById(userId, name, age)
            }
        }

        cleanButton.setOnClickListener {

            userId = 0

            userNameTextInputEditText.text?.clear()
            userAgeTextInputEditText.text?.clear()
        }
    }

    private fun prepareViewModel() {

        val inputViewModelFactory = InputViewModelFactory(
                UserDataRepository(
                        UserLocalDataSource(Application.database.userDao())),
                CommandInjector
        )

        inputViewModel = ViewModelProviders.of(this, inputViewModelFactory)
            .get(InputViewModel::class.java)

        inputViewModel.apply {

            viewState.removeObservers(this@InputActivity)
            command.removeObservers(this@InputActivity)

            viewState.observe(this@InputActivity, Observer { vs ->

                vs?.let { render(it) }
            })

            command.observe(this@InputActivity, Observer { cmd ->

                cmd?.let { triggerCommand(it) }
            })
        }

        activityInputBinding.inputViewModel = inputViewModel
    }

    private fun render(viewState: InputViewModel.ViewState) {

        when (viewState.isSavingUser) {

            true -> { saveUserProgressBar.visibility = View.VISIBLE }
            false -> { saveUserProgressBar.visibility = View.GONE }
        }
    }

    private fun triggerCommand(command: GenericCommand) {

        when (command) {

            is InputViewModel.Command.ShowUserInfo -> {

                command.user?.let {

                    userId = it.id

                    userNameTextInputEditText.setText(it.name)
                    userAgeTextInputEditText.setText(it.age)
                }
            }

            is InputViewModel.Command.ShowSavedUserMessage -> {

                userId = command.id

                val resultIntent = Intent(this@InputActivity, ResultActivity::class.java)

                resultIntent.putExtra("id", userId)

                startActivityForResult(resultIntent, RESULT_REQUEST_CODE)
            }
        }
    }
}
