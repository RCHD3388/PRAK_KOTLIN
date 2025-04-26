package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.HomeTweetAdapter
import com.example.myapplication.adapter.NotifAdapter
import com.example.myapplication.databinding.FragmentNotifBinding
import com.example.myapplication.entity.NotificationEntity
import com.example.myapplication.entity.dto.UserTweetDto
import com.example.myapplication.viewmodel.HomeViewModel
import com.example.myapplication.viewmodel.NotifViewModel
import com.example.myapplication.viewmodel.UserLoginViewModel

class NotifFragment : Fragment() {
    private val viewModel: NotifViewModel by viewModels()
    lateinit var notifAdapter: NotifAdapter;

    lateinit var binding: FragmentNotifBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notif, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()
        initRV()
    }
    fun initRV(){
        notifAdapter = NotifAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.adapter = notifAdapter

        val tweetObserver = Observer<List<NotificationEntity>> {
            notifAdapter.submitList(it)
        }
        viewModel.notifs.observe(viewLifecycleOwner, tweetObserver)
    }
}