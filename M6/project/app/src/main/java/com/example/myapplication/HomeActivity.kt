package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.viewmodel.UserLoginViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener {
            if(it.itemId == R.id.im_home){
                navController.navigate(R.id.action_global_homeFragment)
            } else if(it.itemId == R.id.im_profile){
                UserLoginViewModel.setActiveUser(UserLoginViewModel.COloggedinUser);
                navController.navigate(R.id.action_global_profileFragment)
            } else {
                navController.navigate(R.id.action_global_notifFragment)
            }
            true
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.profileFragment ||
            navController.currentDestination?.id == R.id.commentFragment ||
            navController.currentDestination?.id == R.id.notifFragment ){
            navController.navigate(R.id.action_global_homeFragment)
        }
    }
}