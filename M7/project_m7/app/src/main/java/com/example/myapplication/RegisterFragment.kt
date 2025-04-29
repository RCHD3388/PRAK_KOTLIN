package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val displayName = binding.etDisplayname.text.toString()
            val password = binding.etPassword.text.toString()
            val confirm = binding.etConfirm.text.toString()

            if(username.isNotEmpty() && displayName.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()){
                if(password == confirm){
                    // do register
                }else{
                    Toast.makeText(requireContext(), "Password dan confirm password harus sama", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}