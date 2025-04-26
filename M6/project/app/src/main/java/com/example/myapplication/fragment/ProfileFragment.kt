package com.example.myapplication.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.adapter.HomeTweetAdapter
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.entity.dto.UserTweetDto
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.ProfileViewModel
import com.example.myapplication.viewmodel.UserLoginViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    lateinit var homeTweetAdapter: HomeTweetAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            UserLoginViewModel.COlogout();
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        viewModel.init()
        setProfile()
        setTweet()
    }
    fun setProfile(){
        binding.tvName.text = UserLoginViewModel.COActivateUser.name
        binding.tvUsername.text = UserLoginViewModel.COActivateUser.username
        binding.tvDob.text = UserLoginViewModel.COActivateUser.date_of_birth
    }
    fun setTweet(){
        homeTweetAdapter = HomeTweetAdapter()
        homeTweetAdapter.isProfile = true;
        homeTweetAdapter.onLikeClickListener = {
            viewModel.likeTweet(it)
        }
        homeTweetAdapter.onRetweetClickListener = {
            viewModel.retweetTweet(it)
        }
        homeTweetAdapter.onCommentClickListener = {
            val action = ProfileFragmentDirections.actionProfileFragmentToCommentFragment(UserLoginViewModel.COloggedinUser, it)
            findNavController().navigate(action)
        }

        binding.rvPersonTweet.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvPersonTweet.adapter = homeTweetAdapter

        val tweetObserver = Observer<List<UserTweetDto>> {
            homeTweetAdapter.submitList(it)
            binding.tvTweetsCount.text = "${it.size} Tweets"
        }
        viewModel.tweets.observe(viewLifecycleOwner, tweetObserver)
    }
}