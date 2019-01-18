package com.fourall.aat.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fourall.aat.R
import com.fourall.aat.models.User
import kotlinx.android.synthetic.main.user_item.view.*

class UsersAdapter(
        private var users: MutableList<User>,
        private val context: Context,
        private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item,
                    parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = users[position]

        holder.userNameTextView.text = user.name
        holder.userAgeTextView.text = user.age

        holder.userItemView.setOnClickListener {
            itemClickListener(position)
        }
    }

    fun updateUsers(updatedUsers: List<User>) {

        users.clear()
        users.addAll(updatedUsers)

        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userNameTextView: TextView = view.userNameTextView
        val userAgeTextView: TextView = view.userAgeTextView
        val userItemView: View = view.userItemView
    }
}