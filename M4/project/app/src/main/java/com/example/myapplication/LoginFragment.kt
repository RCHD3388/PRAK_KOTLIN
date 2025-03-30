package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myapplication.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            var username = binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()

            if(username != "" && password != ""){
                var currentUser = MockDB.users.find{ it.username == username && it.password == password}
                if(currentUser != null){
                    MockDB.loggedInUser = currentUser
                    val intent = Intent(requireContext(), HomeMainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(requireContext(), "username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "username atau password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}