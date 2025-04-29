package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.network.AppConfiguration

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding;
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
            AppConfiguration.authRepository.clearSession();
            val intent = Intent(requireContext(), MainActivity::class.java);
            startActivity(intent);
        }

    }
}