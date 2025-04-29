package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddfriendBinding
import com.example.myapplication.databinding.FragmentHomeBinding

class AddfriendFragment : Fragment() {
    lateinit var binding: FragmentAddfriendBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnAdd.setOnClickListener {
            val name = binding.etFriendname.text.toString()
            // Toast.makeText(requireContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }
}