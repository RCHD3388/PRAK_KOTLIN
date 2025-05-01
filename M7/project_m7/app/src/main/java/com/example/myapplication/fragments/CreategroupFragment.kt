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
import com.example.myapplication.adapters.MemberAdapter
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.databinding.FragmentCreategroupBinding
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.viewmodels.CreateGroupViewModel

class CreategroupFragment : Fragment() {
    lateinit var binding: FragmentCreategroupBinding;
    val viewModel by viewModels<CreateGroupViewModel>()
    lateinit var memberAdapter: MemberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_creategroup, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRv()
        setButtonHandler()
        setObserver()
    }

    fun setupRv(){
        memberAdapter = MemberAdapter()
        memberAdapter.onDeleteClickListener = {
            viewModel.removeTargetUser(it)
        }
        binding.rvTempUser.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTempUser.adapter = memberAdapter
    }

    fun setButtonHandler(){
        binding.btnAdd.setOnClickListener {
            val userUsername = binding.etUserName.text.toString();
            if(userUsername.isNotEmpty()){
                viewModel.addTargetUser(userUsername);
            }else{
                Toast.makeText(requireContext(), "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreate.setOnClickListener {
            val groupName = binding.etGroupname.text.toString()
            if(groupName.isNotEmpty()){
                viewModel.createGroup(groupName);
            }else{
                Toast.makeText(requireContext(), "Group Name tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp();
        }
    }
    fun setObserver(){
        viewModel.targetUserList.observe(viewLifecycleOwner, Observer {
            memberAdapter.submitList(it.toList().filter { it.username != AppDatabase.currentUser!!.username ?: "" })
        })
        viewModel.addTargetUserResult.observe(viewLifecycleOwner, Observer { result ->
            result?.onFailure { error ->
                Toast.makeText(requireContext(), "Add user gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.createGroupResult.observe(viewLifecycleOwner, Observer { result ->
            result?.onSuccess {
                Toast.makeText(requireContext(), "Create Group Berhasil: ${it.message}", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }?.onFailure { error ->
                Toast.makeText(requireContext(), "Create Group gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}