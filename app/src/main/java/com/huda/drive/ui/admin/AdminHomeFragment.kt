package com.huda.drive.ui.admin

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.huda.drive.R
import com.huda.drive.adapter.FirebaseViewHolder
import com.huda.drive.databinding.FragmentAdminHomeBinding
import com.huda.drive.databinding.FragmentUserOrdersBinding
import com.huda.drive.model.Orders
import com.huda.drive.ui.user.UserOrdersFragmentDirections
import com.huda.drive.util.PreferenceManager
import com.huda.drive.util.Utils

class AdminHomeFragment : Fragment() {

    private lateinit var binding: FragmentAdminHomeBinding

    private lateinit var options: FirebaseRecyclerOptions<Orders>
    private lateinit var adapter: FirebaseRecyclerAdapter<Orders, FirebaseViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        val user = PreferenceManager.getUser(requireContext())!!

        val query: Query =
            FirebaseDatabase.getInstance().reference
                .child("orders")
//                .orderByChild("status")
//                .equalTo(0.0)

        options = FirebaseRecyclerOptions.Builder<Orders>()
            .setQuery(
                query,
                Orders::class.java
            )
            .build()

        adapter = object : FirebaseRecyclerAdapter<Orders, FirebaseViewHolder>(options) {
            override fun onDataChanged() {
                super.onDataChanged()
                val count = adapter.itemCount
                if (count == 0) {
                    Utils.toast(requireContext(), "Pesanan kosong")
                }
            }

            override fun onBindViewHolder(
                holder: FirebaseViewHolder,
                i: Int,
                order: Orders
            ) {
                Glide.with(requireContext())
                    .load(Utils.decodeStringtoInt(requireContext(), order.item.img))
                    .into(holder.imgItem!!)
                holder.tvItem!!.text = order.item.name
                holder.tvTimestamp!!.text = Utils.formatDateSimple(order.timestamp!!)
                holder.tvPrice!!.text = Utils.formatPrice(order.price!!)
                holder.tvStatus!!.text = Utils.formatStatus(order.status!!)
                when (order.status) {
                    0 -> holder.tvStatus!!.setTextColor(Color.parseColor("#FFC730"))
                    1 -> holder.tvStatus!!.setTextColor(Color.parseColor("#15BE6D"))
                    2 -> holder.tvStatus!!.setTextColor(Color.parseColor("#2196F3"))
                }

                holder.itemView.setOnClickListener {
                    val data =
                        AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminDetailOrderFragment(
                            order.id
                        )
                    findNavController().navigate(data)
                }
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): FirebaseViewHolder {
                return FirebaseViewHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.list_orders, parent, false)
                )
            }
        }
        binding.rvOrders.adapter = adapter

        return root
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}