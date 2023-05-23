package com.huda.drive.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.huda.drive.R
import com.huda.drive.databinding.FragmentAdminDetailOrderBinding
import com.huda.drive.databinding.FragmentUserDetailOrderBinding
import com.huda.drive.model.Orders
import com.huda.drive.ui.user.UserDetailOrderFragmentArgs
import com.huda.drive.util.Utils

class AdminDetailOrderFragment : Fragment() {

    private lateinit var binding: FragmentAdminDetailOrderBinding

    private val args: AdminDetailOrderFragmentArgs by navArgs()

    var status: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdminDetailOrderBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        FirebaseDatabase.getInstance().reference.child("orders").child(args.id)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val order = snapshot.getValue(Orders::class.java)!!

                            binding.tvStatus.text = Utils.formatStatus(order.status!!)
                            status = order.status!!
                            when (status) {
                                0 -> {
                                    binding.btnConfirm.visibility = View.VISIBLE
                                    binding.btnConfirm.text = "Konfirmasi Pesanan"
                                }

                                1 -> {
                                    binding.btnConfirm.visibility = View.VISIBLE
                                    binding.btnConfirm.text = "Selesaikan Pesanan"
                                }

                                2 -> binding.btnConfirm.visibility = View.GONE
                            }
                            binding.tvTimestamp.text = Utils.formatDateSimple(order.timestamp!!)

                            Glide.with(requireContext())
                                .load(order.profile)
                                .error(R.drawable.ic_account)
                                .into(binding.imgProfile)
                            binding.tvUsername.text = order.username
                            binding.tvPhone.text = order.phone
                            binding.tvEmail.text = order.email

                            Glide.with(requireContext())
                                .load(Utils.decodeStringtoInt(requireContext(), order.item.img))
                                .into(binding.imgItem)
                            binding.tvItem.text = order.item.name
                            binding.tvPrice.text = "${Utils.formatPrice(order.item.price!!)}/Hari"

                            val filter1 = order.item.features.replace("-", "• ")
                            val filter2 = filter1.replace("; ", "\n").trim()
                            binding.tvFeature.text = filter2

                            binding.tvDateStart.text = order.timeRental
                            binding.tvDateEnd.text = order.timeReturn
                            binding.tvTotalPrice.text = Utils.formatPrice(order.price!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        binding.btnConfirm.setOnClickListener {
            when (status) {
                0 -> {
                    val status: MutableMap<String, Any> = HashMap()
                    status["status"] = 1
                    FirebaseDatabase.getInstance().reference
                        .child("orders")
                        .child(args.id)
                        .updateChildren(status)
                }

                1 -> {
                    val status: MutableMap<String, Any> = HashMap()
                    status["status"] = 2
                    FirebaseDatabase.getInstance().reference
                        .child("orders")
                        .child(args.id)
                        .updateChildren(status)
                }
            }
        }

        return root
    }
}