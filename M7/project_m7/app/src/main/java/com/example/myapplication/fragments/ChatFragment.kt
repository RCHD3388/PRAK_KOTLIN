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
import com.example.myapplication.adapters.ChatAdapter
import com.example.myapplication.adapters.HomeGroupAdapter
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.local.AppDatabase
import com.example.myapplication.viewmodels.ChatViewModel

class ChatFragment : Fragment() {
    lateinit var binding: FragmentChatBinding;
    val viewModel by viewModels<ChatViewModel>()
    lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshAllDataData();

        setupRv();
        setupViewHandler()
        setupObserver()

    }

    fun setupRv(){
        chatAdapter = ChatAdapter()
        chatAdapter.onNameClickListener = {
            viewModel.setActiveUsername(it.user_username)
        }
        binding.rvChats.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvChats.adapter = chatAdapter
    }
    fun setupViewHandler(){
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAdd.setOnClickListener {
            val chat = binding.etChat.text.toString()
            if(chat.isNotEmpty()){
                viewModel.addNewChat(chat)
                binding.etChat.setText("")
            }else{
                Toast.makeText(requireContext(), "Chat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvChatName.setOnClickListener {
            if(viewModel.isPrivateChat()){
                viewModel.setActiveUsername()
            }
        }
    }
    fun setupObserver(){
        viewModel.group.observe(viewLifecycleOwner, Observer { result ->
            binding.tvChatName.text = result.alternatif_name
            var formatedTime = "-";
            if(result.last_chat != null){
                formatedTime = HomeGroupAdapter.formatDate(result.last_chat!!.chat_time)
            }
            binding.tvLastMessageInformation.text = "Last message\n${formatedTime}"

            chatAdapter.withName = result.alternatif_name == result.group.name;
            chatAdapter.submitList(result.chats)
        })

        viewModel.activeUserState.observe(viewLifecycleOwner, Observer { result ->
            if(result){
                findNavController().navigate(R.id.action_chatFragment_to_profileFragment);
                viewModel.resetActiveUserState()
            }
        })

        viewModel.resultAddChat.observe(viewLifecycleOwner, Observer { result ->
            result?.onFailure { error ->
                Toast.makeText(requireContext(), "Send chat gagal: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}