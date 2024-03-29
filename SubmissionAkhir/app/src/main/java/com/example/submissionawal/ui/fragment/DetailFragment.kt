package com.example.submissionawal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawal.data.response.ItemsItem
import com.example.submissionawal.databinding.FragmentDetailBinding
import com.example.submissionawal.ui.viewmodel.FragmentViewModel
import com.example.submissionawal.ui.adapter.UserAdapter

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val fragmentViewModel by viewModels<FragmentViewModel>()

    var position: Int = 0
    var username: String = ""

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.listFollowers.observe(requireActivity()){
            if (it != null) {
                setUserData(it)
            }
        }

        fragmentViewModel.listFollowing.observe(requireActivity()){
            if (it != null) {
                setUserData(it)
            }
        }

        fragmentViewModel.isLoading.observe(requireActivity()){
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1){
            fragmentViewModel.getFollowers(username)
        } else {
            fragmentViewModel.getFollowing(username)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    private fun setUserData(user: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvUser.adapter = adapter
        if (user.size > 0){
            binding.tvNoData.visibility = View.GONE
        }else{
            binding.tvNoData.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}