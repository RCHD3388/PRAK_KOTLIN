package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.network.AppConfiguration

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding;
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener {
            if(it.itemId == R.id.im_login){
                navController.navigate(R.id.action_global_loginFragment)
            } else if(it.itemId == R.id.im_register){
                navController.navigate(R.id.action_global_registerFragment)
            }
            true
        }
    }
}