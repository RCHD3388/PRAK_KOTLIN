package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val displayName = binding.etDisplayname.text.toString()
            val password = binding.etPassword.text.toString()
            val confirm = binding.etConfirm.text.toString()

            if (username.isNotEmpty() && displayName.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if (password == confirm) {
                    viewModel.register(displayName, username, password)
                } else {
                    Toast.makeText(requireContext(), "Password dan Confirm Password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObserver() {
        viewModel.registerResult.observe(viewLifecycleOwner, Observer { result ->
            result?.onSuccess { message ->
                Toast.makeText(requireContext(), "Register berhasil: $message", Toast.LENGTH_SHORT).show()
                viewModel.clearRegisterResult()
            }?.onFailure { error ->
                Toast.makeText(requireContext(), "Register gagal: ${error.message}", Toast.LENGTH_SHORT).show()
                viewModel.clearRegisterResult()
            }
        })
    }
}