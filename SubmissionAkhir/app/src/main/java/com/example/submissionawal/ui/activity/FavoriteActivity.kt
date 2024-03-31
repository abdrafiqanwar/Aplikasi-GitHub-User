package com.example.submissionawal.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.R
import com.example.submissionawal.data.response.ItemsItem
import com.example.submissionawal.databinding.ActivityFavoriteBinding
import com.example.submissionawal.helper.ViewModelFactory
import com.example.submissionawal.ui.adapter.UserAdapter
import com.example.submissionawal.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        favoriteViewModel.isLoading.observe(this){
            showLoading(it)
        }

        favoriteViewModel.getAllFavoriteUsers().observe(this) { users ->
            val adapter = UserAdapter()
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(id = it.id, login = it.username, avatarUrl = it.avatarUrl, htmlUrl = it.htmlUrl)
                items.add(item)
            }
            adapter.submitList(items)
            binding.rvUser.adapter = adapter

            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
                override fun onItemClicked(data: ItemsItem) {
                    showSelectedUser(data)
                }
            })

            if (items.size > 0 ){
                binding.tvNoData.visibility = View.GONE
            } else{
                binding.tvNoData.visibility = View.VISIBLE
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
    }

    private fun showSelectedUser(user: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("username", user.login)
        intent.putExtra("avatarUrl", user.avatarUrl)
        intent.putExtra("id", user.id)
        intent.putExtra("htmlUrl", user.htmlUrl)
        startActivity(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}