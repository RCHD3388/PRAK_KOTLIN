package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.databinding.FragmentCreategroupBinding

class CreategroupFragment : Fragment() {
    lateinit var binding: FragmentCreategroupBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_creategroup, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            val userUsername = binding.etUserName.text.toString();
            if(userUsername.isNotEmpty()){

            }else{
                Toast.makeText(requireContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreate.setOnClickListener {
            val groupName = binding.etGroupname.text.toString()
            if(groupName.isNotEmpty()){

            }else{
                Toast.makeText(requireContext(), "Group Name tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}