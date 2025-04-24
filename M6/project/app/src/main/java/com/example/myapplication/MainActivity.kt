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
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewmodel.UserLoginViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding;
    val viewModel by viewModels<UserLoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.init()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()){
                viewModel.loginUser(username, password);
            }else{
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        val loginStatusObserver = Observer<String> {
            if(it != ""){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                if(it == "Berhasil melakukan login"){
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        viewModel.loggedInStatus.observe(this, loginStatusObserver)
    }
}