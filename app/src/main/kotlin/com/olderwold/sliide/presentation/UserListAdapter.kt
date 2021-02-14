package com.olderwold.sliide.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.sliide.R

internal class UserListAdapter(
    private val onLongClick: (item: UserItem) -> Unit
) : ListAdapter<UserItem, UserListAdapter.ViewHolder>(UserDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view, onLongClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val containerView: View,
        private val onLongClick: (user: UserItem) -> Unit
    ) : RecyclerView.ViewHolder(containerView) {
        private val emailTextView = containerView.findViewById<TextView>(R.id.email)
        private val nameTextView = containerView.findViewById<TextView>(R.id.name)
        private val createdTextView = containerView.findViewById<TextView>(R.id.created)

        fun bind(item: UserItem) {
            bindCallbacks(item)
            bindUserData(item)
            updateColorState(item)
        }

        private fun bindCallbacks(item: UserItem) {
            if (item.toBeDeleted) {
                containerView.setOnLongClickListener(null)
            } else {
                containerView.setOnLongClickListener {
                    onLongClick(item)
                    true
                }
            }
        }

        private fun bindUserData(item: UserItem) {
            val user = item.user
            emailTextView.text = user.email
            nameTextView.text = user.name
            createdTextView.text = user.createdAt?.toString()
        }

        private fun updateColorState(item: UserItem) {
            val colorRes = if (item.toBeDeleted) {
                R.color.purple_200
            } else {
                android.R.color.white
            }
            val color = ContextCompat.getColor(containerView.context, colorRes)
            containerView.setBackgroundColor(color)
        }
    }

    private object UserDiff : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem.user.id == newItem.user.id
        }

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem == newItem
        }
    }
}
