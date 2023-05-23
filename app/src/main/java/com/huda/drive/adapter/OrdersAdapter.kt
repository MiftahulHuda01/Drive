package com.huda.drive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.huda.drive.R
import com.huda.drive.model.Kendaraan
import com.huda.drive.model.Order
import com.huda.drive.util.Utils

class OrdersAdapter(val context: Context, private val itemList: MutableList<Order>) :
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_orders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        Glide.with(context)
            .load(Utils.decodeStringtoInt(context, item.item.img!!))
            .into(holder.imgItem)
        holder.tvItem.text = item.item.name
        holder.tvPrice.text = "${Utils.formatPrice(item.price)}/Hari"
        holder.itemView.setOnClickListener {
            listener?.onItemClick(item)
        }
    }

    override fun getItemCount() = itemList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem: ImageView = itemView.findViewById(R.id.img_item)
        val tvItem: TextView = itemView.findViewById(R.id.tv_item)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_status)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(orders: Order)
    }
}