package com.example.myapplication.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapters.HomeGroupAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding;
    lateinit var homeGroupAdapter: HomeGroupAdapter
    val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshGroupList();

        setupRv()
        setupObserver()

        binding.btnSearch.setOnClickListener {
            viewModel.refreshGroupList(binding.etSearch.text.toString());
        }
        binding.btnNewgroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_creategroupFragment)
        }
    }

    fun setupRv(){
        homeGroupAdapter = HomeGroupAdapter()
        homeGroupAdapter.onItemClickListener = {
            // go to chat fragment
            AppDatabase.activeGroupId = it.group;
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }
        binding.rvFriendList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFriendList.adapter = homeGroupAdapter
    }
    fun setupObserver(){
        viewModel.groupLists.observe(viewLifecycleOwner, Observer { result ->
            if(result.isNotEmpty()){
                binding.tvFriendlistEmpty.visibility = View.GONE;
            }else{
                binding.tvFriendlistEmpty.visibility = View.VISIBLE;
            }
            homeGroupAdapter.submitList(result)
        })
    }
}