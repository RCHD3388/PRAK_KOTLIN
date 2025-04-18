package com.example.projectm5

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.projectm5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            var username = binding.etUsername.text.toString()
            var password = binding.etPassword.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty()){
                if(DataRepo.isValiduser(username, password)){
                    DataRepo.signIn(username);
                    Toast.makeText(this, "login berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "username atau password salah", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "username atau password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}