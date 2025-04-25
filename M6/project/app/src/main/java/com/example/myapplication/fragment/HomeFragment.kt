package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.HomeTweetAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding;
    private val viewModel: HomeViewModel by viewModels()

    lateinit var homeTweetAdapter: HomeTweetAdapter;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTweet.setOnClickListener {
            val tweet = binding.etTweet.text.toString();
            if(tweet.isNotEmpty()){
                viewModel.createNewTweet(tweet);
            }else{
                Toast.makeText(requireContext(), "Tweet tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.init()
        initRV()
    }

    fun initRV(){
        homeTweetAdapter = HomeTweetAdapter()
        homeTweetAdapter.onLikeClickListener = {
            viewModel.likeTweet(it)
        }
        homeTweetAdapter.onRetweetClickListener = {
            viewModel.retweetTweet(it)
        }
        homeTweetAdapter.onCommentClickListener = {

        }

        binding.rvTweets.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvTweets.adapter = homeTweetAdapter

        val tweetObserver = Observer<List<TweetEntity>> {
            homeTweetAdapter.submitList(it)
        }
        viewModel.tweets.observe(viewLifecycleOwner, tweetObserver)
    }
}