package com.huda.drive.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.huda.drive.R
import com.huda.drive.model.Category
import com.huda.drive.model.Kendaraan
import com.huda.drive.util.Utils

class CategoryAdapter(val context: Context, private val itemList: MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        Glide.with(context)
            .load(item.img)
            .into(holder.imgItem)
        holder.tvItem.text = item.name
//        holder.itemView.setOnClickListener {
//            listener?.onItemClick(item)
//        }
        holder.itemView.setOnFocusChangeListener { view, _ ->
            if (view.isFocusableInTouchMode) {
                listener?.onItemClick(item)
            }
        }
    }

    override fun getItemCount() = itemList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgItem: ImageView = itemView.findViewById(R.id.img_item)
        val tvItem: TextView = itemView.findViewById(R.id.tv_item)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }
}