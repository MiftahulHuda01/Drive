package com.huda.drive.ui.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.huda.drive.R
import com.huda.drive.databinding.FragmentUserProfileBinding
import com.huda.drive.ui.auth.AuthActivity
import com.huda.drive.util.PreferenceManager
import com.huda.drive.util.Utils

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        val root = binding.root

        val user = PreferenceManager.getUser(requireContext())!!

        Glide.with(requireContext())
            .load(user.profile)
            .error(R.drawable.ic_account)
            .into(binding.imgProfile)

        binding.tvUsername.text = user.username
        binding.tvPhone.text = user.phone
        binding.tvEmail.text = user.email

        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            PreferenceManager.deleteUser(requireContext())
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            activity?.finish()
        }

        return root
    }
}