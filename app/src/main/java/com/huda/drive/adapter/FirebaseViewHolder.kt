package com.huda.drive.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huda.drive.R

class FirebaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imgItem: ImageView? = null
    var tvItem: TextView? = null
    var tvTimestamp: TextView? = null
    var tvPrice: TextView? = null
    var tvStatus: TextView? = null

    init {
        imgItem = itemView.findViewById(R.id.img_item)
        tvItem = itemView.findViewById(R.id.tv_item)
        tvTimestamp = itemView.findViewById(R.id.tv_timestamp)
        tvPrice = itemView.findViewById(R.id.tv_price)
        tvStatus = itemView.findViewById(R.id.tv_status)
    }
}