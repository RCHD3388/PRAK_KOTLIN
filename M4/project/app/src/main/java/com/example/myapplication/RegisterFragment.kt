package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            var username = binding.etUsername.text.toString()
            var name = binding.etName.text.toString()
            var password = binding.etPassword.text.toString()
            var confirmPassword = binding.etConfirmPassword.text.toString()
            var dateOfBirth = binding.etDateOfBirth.text.toString()
            var phoneNumber = binding.etPhoneNumber.text.toString()

            if(username != "" && name != "" && password != "" && confirmPassword != "" && dateOfBirth != "" && phoneNumber != ""){
                if(password == confirmPassword){
                    if(phoneNumber.length >= 12){
                        if(MockDB.users.find{ it.username == username} == null){
                            MockDB.users.add(User(username, password, name, dateOfBirth, phoneNumber))
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }else{
                            Toast.makeText(requireContext(), "Username sudah terpakai", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(), "Nomor telepon tidak valid", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Semua field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}