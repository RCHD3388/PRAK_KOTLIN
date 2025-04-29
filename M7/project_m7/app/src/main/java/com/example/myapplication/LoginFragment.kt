package com.example.myapplication

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
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.network.AppConfiguration
import com.example.myapplication.viewmodels.LoginViewModel

class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupObserver()
    }

    private fun setupListener() {
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val rememberMe = binding.cbRememberMe.isChecked;

            if(username.isNotEmpty() && password.isNotEmpty()){
                // do login
                viewModel.login(username, password);
                AppConfiguration.authRepository.saveLoginSession(rememberMe, username);
            }else{
                Toast.makeText(requireContext(), "Username dan Password harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            result?.onSuccess { message ->
                Toast.makeText(requireContext(), "Login berhasil: $message", Toast.LENGTH_SHORT).show()
                viewModel.clearRegisterResult()
                val intent = Intent(requireContext(), HomeActivity::class.java);
                startActivity(intent)
            }?.onFailure { error ->
                Toast.makeText(requireContext(), "Login gagal: ${error.message}", Toast.LENGTH_SHORT).show()
                viewModel.clearRegisterResult()
            }
        })
    }
}