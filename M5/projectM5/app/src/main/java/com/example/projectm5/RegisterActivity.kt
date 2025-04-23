package com.example.projectm5

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.projectm5.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener {
            var username = binding.etUsername.text.toString()
            var name = binding.etName.text.toString()
            var password = binding.etPassword.text.toString()
            var confirm = binding.etConfirmPassword.text.toString()

            if(username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()){
                if(!DataRepo.usernameExists(username)){
                    if(password == confirm){
                        DataRepo.addData(username, name, password)
                        Toast.makeText(this, "register berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "password dan confirm password harus sama", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "username tersebut telah digunakan", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}