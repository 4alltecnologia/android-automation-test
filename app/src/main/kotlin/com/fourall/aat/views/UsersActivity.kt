package com.fourall.aat.views

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fourall.aat.Application
import com.fourall.aat.R
import com.fourall.aat.data.di.CommandInjector
import com.fourall.aat.data.local.UserDAO
import com.fourall.aat.data.local.UserLocalDataSource
import com.fourall.aat.databinding.ActivityUsersBinding
import com.fourall.aat.models.GenericCommand
import com.fourall.aat.repositories.UserDataRepository
import com.fourall.aat.viewmodels.UsersViewModel
import com.fourall.aat.viewmodels.factory.UsersViewModelFactory
import com.fourall.aat.views.adapters.UsersAdapter
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : BaseActivity() {

    private lateinit var activityUsersBinding: ActivityUsersBinding
    private lateinit var usersViewModel: UsersViewModel

    private var usersAdapter: UsersAdapter = UsersAdapter(mutableListOf(), this) { position ->

        val user = (usersViewModel.command as UsersViewModel.Command.ShowUsers).users[position]

        val inputIntent = Intent(this, InputActivity::class.java)

        inputIntent.putExtra(InputActivity.ARG_USER_ID, user.id)
        inputIntent.putExtra(InputActivity.ARG_USER_NAME, user.name)
        inputIntent.putExtra(InputActivity.ARG_USER_AGE, user.age)

        startActivity(inputIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        prepareUi()
        prepareViewModel()
    }

    override fun onResume() {

        super.onResume()

        usersViewModel.loadUsers()
    }

    private fun prepareUi() {

        activityUsersBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)

        title = getString(R.string.app_name)

        val linearLayoutManager = LinearLayoutManager(this)

        val dividerItemDecoration = DividerItemDecoration(
                usersRecyclerView.context, linearLayoutManager.orientation)

        usersRecyclerView.addItemDecoration(dividerItemDecoration)
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = usersAdapter
    }

    private fun prepareViewModel() {

        val usersViewModelFactory = UsersViewModelFactory(
                UserDataRepository(
                UserLocalDataSource(Application.database.userDao())),
                CommandInjector
        )

        usersViewModel = ViewModelProviders.of(this, usersViewModelFactory)
                .get(UsersViewModel::class.java)

        usersViewModel.apply {

            viewState.removeObservers(this@UsersActivity)
            command.removeObservers(this@UsersActivity)

            viewState.observe(this@UsersActivity, Observer { vs ->

                vs?.let { render(it) }
            })

            command.observe(this@UsersActivity, Observer { cmd ->

                cmd?.let { triggerCommand(it) }
            })
        }

        activityUsersBinding.usersViewModel = usersViewModel
    }

    private fun render(viewState: UsersViewModel.ViewState) {

        when (viewState.isLoadingUsers) {

            true -> { loadUsersProgressBar.visibility = View.VISIBLE }
            false -> { loadUsersProgressBar.visibility = View.INVISIBLE }
        }
    }

    private fun triggerCommand(command: GenericCommand) {

        when (command) {

            is UsersViewModel.Command.AddNewUser -> {

                val inputIntent = Intent(this, InputActivity::class.java)

                startActivity(inputIntent)
            }

            is UsersViewModel.Command.ShowUsers -> {

                if (command.users.isNotEmpty()) {

                    noUsersTextView.visibility = View.GONE

                    usersAdapter.updateUsers(command.users)

                } else {

                    usersRecyclerView.visibility = View.GONE
                    noUsersTextView.visibility = View.VISIBLE
                }
            }
        }
    }
}