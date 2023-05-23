package com.huda.drive.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.huda.drive.R
import com.huda.drive.databinding.ActivityAuthBinding
import com.huda.drive.ui.admin.AdminMainActivity
import com.huda.drive.ui.user.UserMainActivity
import com.huda.drive.util.PreferenceManager
import com.huda.drive.util.Utils

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        binding.root.apply { setContentView(this) }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val user = PreferenceManager.getUser(this)!!

            when (user.role) {
                "user" -> {
                    startActivity(Intent(this, UserMainActivity::class.java))
                    finish()
                }

                "admin" -> {
                    startActivity(Intent(this, AdminMainActivity::class.java))
                    finish()
                }
            }
        }
    }
}