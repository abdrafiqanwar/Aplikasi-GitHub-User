package com.example.submissionawal.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionawal.data.response.ItemsItem
import com.example.submissionawal.databinding.ItemUserBinding
import com.example.submissionawal.ui.activity.DetailActivity

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.binding.tvUsername.text = user.login
        holder.binding.tvUserUrl.text = user.htmlUrl
        Glide.with(holder.itemView.context).load(user.avatarUrl)
            .into(holder.binding.ivPhoto)
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("username", user.login)
            intent.putExtra("avatarUrl", user.avatarUrl)
            intent.putExtra("id", user.id)
            intent.putExtra("htmlUrl", user.htmlUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}