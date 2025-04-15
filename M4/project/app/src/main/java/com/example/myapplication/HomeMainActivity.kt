package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityHomeMainBinding

class HomeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostfragment =supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostfragment.navController

        binding.bottomNav.setOnItemSelectedListener {
            if(it.itemId == R.id.home_mi){
                navController.navigate(R.id.homeFragment)
            } else if(it.itemId == R.id.activity_mi){
                navController.navigate(R.id.loginFragment)
            } else if(it.itemId == R.id.voucher_mi){
                navController.navigate(R.id.voucherFragment)
            } else {
                navController.navigate(R.id.accountFragment)
            }
            true
        }
    }
}