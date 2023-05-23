package com.huda.drive.util

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.huda.drive.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {

    private lateinit var loading: Dialog

    fun formatNumber(number: Int): String {
        return String.format("%,d", number)
    }

    fun formatPrice(number: Int): String {
        return String.format("Rp %,d", number)
    }

    fun formatDateSimple(timestamp: Long): String {
        val formatDate = SimpleDateFormat(
            "dd MMM yyyy • HH:mm", Locale("ID")
        )
        val date = Date(timestamp)
        return formatDate.format(date)
    }

    fun formatStatus(status: Int): String? {
        var statues: String? = null
        when (status) {
            0 -> statues = "Menunggu Konfirmasi"
            1 -> statues = "Disewa"
            2 -> statues = "Selesai"
        }
        return statues
    }

    fun showLoading(context: Context) {
        loading = Dialog(context)
        loading.setContentView(R.layout.dialog_progress)
        loading.window!!.setBackgroundDrawableResource(R.drawable.bg_progress)
        loading.show()
    }

    fun dismissLoading() {
        loading.dismiss()
    }

    fun toast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun decodeStringtoInt(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}