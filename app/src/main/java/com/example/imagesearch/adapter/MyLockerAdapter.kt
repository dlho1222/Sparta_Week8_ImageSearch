package com.example.imagesearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.data.SearchItem
import com.example.imagesearch.databinding.ItemImageBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyLockerAdapter(private val items: MutableList<SearchItem>) :
    RecyclerView.Adapter<MyLockerAdapter.MyLockerViewHolder>() {
    inner class MyLockerViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem) {
            with(binding) {
                val dateTime = item.dateTime
                val inputDateTime =
                    LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val outputDateString = inputDateTime.format(outputFormatter)
                tvSource.text = item.title
                tvDate.text = outputDateString.toString()

                Glide.with(itemView)
                    .load(item.thumbUrl)
                    .into(ivThumb)

                ivLike.isVisible = false

                ivThumb.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        items.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, items.size)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyLockerViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return MyLockerViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MyLockerViewHolder, position: Int) {
        holder.bind(items[position])
    }
}