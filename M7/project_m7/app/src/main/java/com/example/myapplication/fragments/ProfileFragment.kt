package com.example.myapplication.fragments

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
import com.example.myapplication.HomeActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.network.AppConfiguration
import com.example.myapplication.viewmodels.LoginViewModel
import com.example.myapplication.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding;
    val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProfileStat()

        if(AppDatabase.activeUser != AppDatabase.currentUser){
            binding.btnLogout.text = "Back"
        }

        binding.btnLogout.setOnClickListener {
            if(AppDatabase.activeUser == AppDatabase.currentUser){
                viewModel.logout()
            }else{
                AppDatabase.activeUser = null;
                findNavController().navigateUp()
            }
        }

        viewModel.logoutResult.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                // navigate to main activity
                val intent = Intent(requireContext(), MainActivity::class.java);
                startActivity(intent);
            }
        })
    }

    fun setupProfileStat(){
        binding.tvUsername.text = AppDatabase.activeUser?.username ?: ""
        binding.tvDisplayname.text = AppDatabase.activeUser?.name ?: ""
    }
}