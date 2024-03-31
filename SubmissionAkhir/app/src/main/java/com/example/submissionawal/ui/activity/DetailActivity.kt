package com.example.submissionawal.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionawal.R
import com.example.submissionawal.data.response.DetailUserResponse
import com.example.submissionawal.database.FavoriteUser
import com.example.submissionawal.databinding.ActivityDetailBinding
import com.example.submissionawal.helper.ViewModelFactory
import com.example.submissionawal.ui.viewmodel.DetailViewModel
import com.example.submissionawal.ui.adapter.SectionsPagerAdapter
import com.example.submissionawal.ui.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application)
    }
    private var favoriteUser: FavoriteUser? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra("id", 0)
        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarUrl")
        val htmlUrl = intent.getStringExtra("htmlUrl")

        if(favoriteUser == null){
            favoriteUser = FavoriteUser()
        }

        favoriteUser.let {
            it?.id = id
            it?.username = username.toString()
            it?.avatarUrl = avatarUrl.toString()
            it?.htmlUrl = htmlUrl.toString()
        }

        favoriteViewModel.getFavoriteUserByUsername(username.toString()).observe(this){
            if (it != null){
                binding.fabFavorite.visibility = View.VISIBLE
                binding.fabFavoriteBorder.visibility = View.GONE
            } else{
                binding.fabFavoriteBorder.visibility = View.VISIBLE
                binding.fabFavorite.visibility = View.GONE
            }
        }

        binding.fabFavorite.setOnClickListener{
            favoriteViewModel.delete(favoriteUser as FavoriteUser)
            Toast.makeText(this, "Berhasil dihapus dari favorite", Toast.LENGTH_LONG).show()
        }

        binding.fabFavoriteBorder.setOnClickListener{
            favoriteViewModel.insert(favoriteUser as FavoriteUser)
            Toast.makeText(this, "Berhasil ditambahkan ke favorite", Toast.LENGTH_LONG).show()
        }

        detailViewModel.detailUser(username.toString())

        detailViewModel.listUser.observe(this){
            if (it != null) {
                setDetailUser(it)
            }
        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(user: DetailUserResponse){
        binding.tvUsername.text = user.login
        binding.tvName.text = user.name
        Glide.with(this).load(user.avatarUrl)
            .into(binding.ivPhoto)
        binding.tvRepo.text = user.publicRepos.toString()
        binding.tvFollowers.text = user.followers.toString()
        binding.tvFollowing.text = user.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}