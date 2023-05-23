package com.huda.drive.ui.user

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
import com.huda.drive.databinding.FragmentUserDetailOrderBinding
import com.huda.drive.model.Orders
import com.huda.drive.util.Utils

class UserDetailOrderFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailOrderBinding

    private val args: UserDetailOrderFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserDetailOrderBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        FirebaseDatabase.getInstance().reference.child("orders").child(args.id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            val order = snapshot.getValue(Orders::class.java)!!

                            binding.tvStatus.text = Utils.formatStatus(order.status!!)
                            binding.tvTimestamp.text = Utils.formatDateSimple(order.timestamp!!)

                            Glide.with(requireContext()).load(order.item.img).into(binding.imgItem)
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

        return root
    }
}