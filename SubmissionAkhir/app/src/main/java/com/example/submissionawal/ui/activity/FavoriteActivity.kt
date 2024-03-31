package com.example.submissionawal.ui.activity

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
import com.example.submissionawal.ui.viewmodel.MainViewModel

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
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
            binding.rvUser.adapter = adapter

            if (items.size > 0 ){
                binding.tvNoData.visibility = View.GONE
            } else{
                binding.tvNoData.visibility = View.VISIBLE
            }
        }

        binding.rvUser.layoutManager = LinearLayoutManager(this)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}