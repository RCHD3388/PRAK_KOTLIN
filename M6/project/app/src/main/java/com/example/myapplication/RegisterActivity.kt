package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                        Toast.makeText(this, "Berhasil melakukan register", Toast.LENGTH_SHORT).show()
                        finish()
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
    }
}