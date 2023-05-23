package com.huda.drive.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.huda.drive.R
import com.huda.drive.databinding.FragmentUserOrderBinding
import com.huda.drive.model.Kendaraan
import com.huda.drive.model.Order
import com.huda.drive.util.PreferenceManager
import com.huda.drive.util.Utils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class UserOrderFragment : Fragment() {

    private lateinit var binding: FragmentUserOrderBinding

    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
    var startDate: String = dateFormat.format(calendar.time)
    var endDate: String = dateFormat.format(calendar.time)
    var day = 1
    var price: Int = 0
    var totalPrice = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserOrderBinding.inflate(inflater, container, false)
        val root = binding.root

        val kendaraan = arguments?.getParcelable<Kendaraan>("kendaraan")
        if (kendaraan != null) {
            Glide.with(binding.imgItem)
                .load(Utils.decodeStringtoInt(requireContext(), kendaraan.img!!))
                .into(binding.imgItem)
            binding.tvItem.text = kendaraan.name
            binding.tvPrice.text = "${Utils.formatPrice(kendaraan.price)}/Hari"
            price = kendaraan.price
        }

        binding.etDateStart.setText(startDate)
        binding.etDateEnd.setText(endDate)

        binding.etDateStart.setOnClickListener {
            datePickerDialog(dateListener(binding.etDateStart))
        }

        binding.etDateEnd.setOnClickListener {
            datePickerDialog(dateListener(binding.etDateEnd))
        }

        totalPrice = day * price
        binding.tvTotalPrice.text = Utils.formatPrice(totalPrice)

        binding.btnOrder.setOnClickListener {
            val id = calendar.timeInMillis
            val user = PreferenceManager.getUser(requireContext())!!
            val dataOrder =
                Order(
                    id.toString(),
                    user.uid,
                    user.username,
                    user.phone,
                    user.email,
                    user.profile,
                    startDate,
                    endDate,
                    day,
                    id,
                    kendaraan!!,
                    totalPrice,
                    0
                )

            FirebaseDatabase.getInstance().reference.child("orders").child(id.toString())
                .setValue(dataOrder).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Order Berhasil", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_userOrderFragment_to_userOrdersFragment)
                }
        }

        return root
    }

    private fun dateListener(etDate: EditText): DatePickerDialog.OnDateSetListener {
        return DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedDate = dateFormat.format(calendar.time)

            if (etDate == binding.etDateStart) {
                startDate = selectedDate
            } else {
                endDate = selectedDate
            }
            etDate.setText(selectedDate)

            day = cekUpdate(startDate, endDate).toInt()
            totalPrice = day * price
            binding.tvTotalPrice.text = Utils.formatPrice(totalPrice)
        }
    }

    private fun datePickerDialog(dateEndListener: DatePickerDialog.OnDateSetListener) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            dateEndListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun cekUpdate(startDate: String, endDate: String): Long {
        val calStartDate = Calendar.getInstance()
        val calEndDate = Calendar.getInstance()

        calStartDate.time = dateFormat.parse(startDate)
        calEndDate.time = dateFormat.parse(endDate)

        val diffInMillis = calEndDate.timeInMillis - calStartDate.timeInMillis
        val diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        return diffInDays + 1
    }
}