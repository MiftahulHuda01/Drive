package com.huda.drive.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import com.huda.drive.R
import com.huda.drive.databinding.ActivityUserMainBinding

class UserMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        val navController = findNavController(R.id.nav_host_fragment_activity_user_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.userHomeFragment -> showBottomNav(true)
                R.id.userOrdersFragment -> showBottomNav(true)
                R.id.userProfileFragment -> showBottomNav(true)
                else -> showBottomNav(false)
            }
        }
        binding.navView.setupWithNavController(navController)
    }

    private fun showBottomNav(params: Boolean) {
        binding.navView.visibility = if (params) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}