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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CommentAdapter
import com.example.myapplication.adapter.HomeTweetAdapter
import com.example.myapplication.databinding.CommentItemBinding
import com.example.myapplication.databinding.FragmentCommentBinding
import com.example.myapplication.entity.TweetEntity
import com.example.myapplication.entity.UTCommentEntity
import com.example.myapplication.entity.dto.UserTweetDto
import com.example.myapplication.viewmodel.CommentViewModel
import com.example.myapplication.viewmodel.HomeViewModel

class CommentFragment : Fragment() {
    lateinit var binding: FragmentCommentBinding;
    private val viewModel: CommentViewModel by viewModels()
    val args by navArgs<CommentFragmentArgs>()

    lateinit var commentAdapter: CommentAdapter;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(args.tweet)
        initMainTweet()
        initRV()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnPost.setOnClickListener {
            val comment = binding.etPostComment.text.toString()
            if(comment != ""){
                viewModel.createNewComment(args.user, comment)
            }else{
                Toast.makeText(requireContext(), "Komentar tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun initMainTweet(){
        binding.ivLike.setOnClickListener {
            viewModel.likeTweet()
        }
        binding.ivRetweets.setOnClickListener {
            viewModel.retweetTweet()
        }

        val tweetOriginalObserver = Observer<UserTweetDto> {
            val originalTweet = it.originalEntity ?: it.tweetEntity
            binding.tvName.text = originalTweet.user_name
            binding.tvUsername.text = originalTweet.user_username
            binding.tvTweet.text = originalTweet.tweet
            binding.tvLike.text = "${originalTweet.like} Likes"
            binding.tvRetweet.text = "${originalTweet.retweet} Retweets"

            binding.ivLike.setImageResource(if(it.current_user_like_status) R.drawable.heart else R.drawable.heart_empty)
        }
        viewModel.userTweetDto.observe(viewLifecycleOwner, tweetOriginalObserver)
    }

    fun initRV(){
        commentAdapter = CommentAdapter()

        binding.rvComments.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvComments.adapter = commentAdapter

        val commentObserver = Observer<List<UTCommentEntity>> {
            commentAdapter.submitList(it)
        }
        viewModel.comments.observe(viewLifecycleOwner, commentObserver)
    }
}