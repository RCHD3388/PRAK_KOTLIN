package com.example.myapplication.fragments

import android.content.Intent
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
import com.example.myapplication.HomeActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddfriendBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.viewmodels.AddFriendViewModel

class AddfriendFragment : Fragment() {
    lateinit var binding: FragmentAddfriendBinding
    val viewModel by viewModels<AddFriendViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_addfriend, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAdd.setOnClickListener {
            val username = binding.etFriendname.text.toString()
            viewModel.addFriend(username)
        }

        viewModel.addFriendResult.observe(viewLifecycleOwner, Observer { result ->
            result?.onSuccess {
                Toast.makeText(requireContext(), "Add Friend Berhasil: ${it.message}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addfriendFragment_to_homeFragment)
            }?.onFailure { error ->
                Toast.makeText(requireContext(), "Add Friend gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}