package com.app.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.assignment.R
import com.app.assignment.databinding.DesignImgBinding
import com.app.assignment.helpers.ImageLoader
import com.app.assignment.models.ImageModel

class ImageAdapter (private val context: Context,
                    private val listener: (items: MutableList<ImageModel>,
                                           position : Int,
                                           imageData: ImageModel) -> Unit) :
    ListAdapter<ImageModel, ImageAdapter.CustomerViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
            DesignImgBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, pos: Int) {
        val currentItem = getItem(pos)
        holder.bind(currentItem)
    }


    inner class CustomerViewHolder(private val binding: DesignImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ImageModel) {

            val imageLoader = ImageLoader(binding.ivImg)

            binding.apply {

                ivImg.setImageResource(R.drawable.image_placeholder)
                imageLoader.loadImage(item.urls.full)

            }

        }

    }

    class DiffCallback : DiffUtil.ItemCallback<ImageModel>() {
        override fun areItemsTheSame(old: ImageModel, aNew: ImageModel) =
            old.id == aNew.id

        override fun areContentsTheSame(old: ImageModel, aNew: ImageModel) =
            old == aNew
    }

}