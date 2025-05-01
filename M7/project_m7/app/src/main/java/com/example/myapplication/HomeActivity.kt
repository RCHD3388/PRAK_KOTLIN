package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.local.AppDatabase

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding;
    lateinit var navController: NavController;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerViewHome) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationViewHome.setOnItemSelectedListener {
            if(it.itemId == R.id.im_home){
                navController.navigate(R.id.action_global_homeFragment)
            } else if(it.itemId == R.id.im_addfriend){
                navController.navigate(R.id.action_global_addfriendFragment)
            } else if(it.itemId == R.id.im_profile){
                AppDatabase.activeUser = AppDatabase.currentUser;
                navController.navigate(R.id.action_global_profileFragment)
            }
            true
        }
    }
}