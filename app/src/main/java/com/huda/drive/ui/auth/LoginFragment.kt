package com.huda.drive.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.huda.drive.R
import com.huda.drive.databinding.FragmentLoginBinding
import com.huda.drive.model.User
import com.huda.drive.model.Users
import com.huda.drive.ui.admin.AdminMainActivity
import com.huda.drive.ui.user.UserMainActivity
import com.huda.drive.util.PreferenceManager
import com.huda.drive.util.Utils

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.btnLogin.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Utils.toast(requireContext(), "Lengkapi yang masih kosong")
            } else {
                Utils.showLoading(requireContext())
                login(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return root
    }

    private fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Utils.toast(requireContext(), "Login berhasil")

                val uid = FirebaseAuth.getInstance().uid.toString()
                FirebaseDatabase.getInstance().reference.child("user").child(uid)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val dataUser = snapshot.getValue(Users::class.java)!!
                                val user = User(
                                    dataUser.uid,
                                    dataUser.username,
                                    dataUser.phone,
                                    dataUser.email,
                                    dataUser.password,
                                    dataUser.profile,
                                    dataUser.role
                                )

                                PreferenceManager.saveUser(requireContext(), user)

                                Utils.dismissLoading()

                                when (dataUser.role) {
                                    "user" -> {
                                        startActivity(Intent(context, UserMainActivity::class.java))
                                        activity?.finish()
                                    }

                                    "admin" -> {
                                        startActivity(
                                            Intent(
                                                context,
                                                AdminMainActivity::class.java
                                            )
                                        )
                                        activity?.finish()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            }.addOnFailureListener {
                when (it.message.toString()) {
                    "There is no user record corresponding to this identifier. The user may have been deleted." -> Utils.toast(
                        requireContext(), "Email tidak terdaftar"
                    )

                    "The password is invalid or the user does not have a password." -> Utils.toast(
                        requireContext(), "Password salah"
                    )

                    else -> Utils.toast(requireContext(), "Login gagal")
                }
                Utils.dismissLoading()
            }
    }
}