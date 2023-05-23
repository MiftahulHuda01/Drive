package com.huda.drive.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.huda.drive.databinding.FragmentRegisterBinding
import com.huda.drive.model.User
import com.huda.drive.util.Utils

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.btnRegister.setOnClickListener {
            val username: String = binding.etUsername.text.toString().trim()
            val phone: String = binding.etPhone.text.toString().trim()
            val email: String = binding.etEmail.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Utils.toast(requireContext(), "Lengkapi yang masih kosong")
            } else {
                Utils.showLoading(requireContext())
                register(username, email, password, phone)
            }
        }

        binding.tvLogin.setOnClickListener {
            activity?.onBackPressed()
        }

        return root
    }

    private fun register(username: String, email: String, password: String, phone: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val uid: String = it.user!!.uid

                val dataUser = User(uid, username, phone, email, password, "default", "user")
                FirebaseDatabase.getInstance().reference.child("user").child(uid).setValue(dataUser)
                    .addOnSuccessListener {
                        Utils.toast(requireContext(), "Registrasi berhasil")
                        Utils.dismissLoading()
                        activity?.onBackPressed()
                    }
            }.addOnFailureListener {
                when (it.message.toString()) {
                    "The email address is already in use by another account." -> Utils.toast(
                        requireContext(),
                        "Email telah terdaftar"
                    )

                    "The given password is invalid. [ Password should be at least 6 characters ]" -> Utils.toast(
                        requireContext(),
                        "Password minimal 6 karakter"
                    )

                    else -> Utils.toast(requireContext(), "Registrasi gagal")
                }
                Utils.dismissLoading()
            }
    }
}