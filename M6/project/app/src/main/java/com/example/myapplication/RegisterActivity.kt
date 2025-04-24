package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.entity.UserEntity
import com.example.myapplication.viewmodel.UserRegisterViewModel

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding;
    val viewModel by viewModels<UserRegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.init()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val confirm = binding.etConfirm.text.toString()
            val dob = binding.etDob.text.toString()

            if(username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && dob.isNotEmpty()){
                if(password == confirm){
                    if(true){
                        // berhasil register
                        viewModel.registerUser(username, name, password, dob);
                    }else{
                        Toast.makeText(this, "Gagal melakukan register", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Password dan confirm password tidak sama", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnLogin.setOnClickListener {
            finish()
        }

        val registerStatusObserver = Observer<String> {
            if(it != ""){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if(it == "Berhasil melakukan register"){
                    finish()
                }
            }
        }
        viewModel.registerStatus.observe(this, registerStatusObserver)
    }
}