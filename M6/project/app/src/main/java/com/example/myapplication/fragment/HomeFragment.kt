package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding;
    private val viewModel: HomeViewModel by viewModels()

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
    }
}