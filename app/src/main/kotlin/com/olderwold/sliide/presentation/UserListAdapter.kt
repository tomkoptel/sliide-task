package com.olderwold.sliide.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.sliide.R
import com.olderwold.sliide.domain.User

internal class UserListAdapter : ListAdapter<User, UserListAdapter.VH>(UserDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.user_list_item, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        containerView: View
    ): RecyclerView.ViewHolder(containerView) {
        private val emailTextView = containerView.findViewById<TextView>(R.id.email)
        private val nameTextView = containerView.findViewById<TextView>(R.id.name)
        private val createdTextView = containerView.findViewById<TextView>(R.id.created)

        fun bind(user: User) {
            emailTextView.text = user.email
            nameTextView.text = user.name
            createdTextView.text = user.createdAt?.toString()
        }
    }

    object UserDiff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}
