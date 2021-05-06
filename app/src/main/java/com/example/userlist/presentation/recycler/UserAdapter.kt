package com.example.userlist.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.userlist.R
import com.example.userlist.databinding.ItemUserBinding
import com.example.userlist.presentation.model.UserItem

class UserAdapter(
    private val itemClick: (Int, Boolean) -> Unit
) :
    ListAdapter<UserItem, UserViewHolder>(object : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position))

}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val itemClick: (Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userItem: UserItem) {
        with(binding) {
            root.setOnClickListener {
                itemClick.invoke(userItem.id, userItem.isActive)
            }
            tvEmail.text = userItem.email
            tvName.text = userItem.name
            if (userItem.isActive) {
                tvActive.text = binding.root.context.getString(R.string.active_text)
            } else {
                tvActive.text = binding.root.context.getString(R.string.not_active_text)
            }
        }
    }

}