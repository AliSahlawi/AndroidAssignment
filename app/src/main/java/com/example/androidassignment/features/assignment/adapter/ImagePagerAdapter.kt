package com.example.androidassignment.features.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.databinding.ItemPagerImageBinding

class ImagePagerAdapter : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {
    private val images =  ArrayList<Int>()

    inner class ImageViewHolder(val binding: ItemPagerImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemPagerImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.pagerImageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size

    fun setImages(newImages: List<Int>) {
        val diffCallback = ImagesCallback(images, newImages)
        val diffItems = DiffUtil.calculateDiff(diffCallback)
        this.images.clear()
        this.images.addAll(newImages)
        diffItems.dispatchUpdatesTo(this)

    }


    inner class ImagesCallback(private val oldList: List<Int>, private val newList: List<Int>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldImagePosition: Int, newImagePosition: Int): Boolean {
            return oldList[oldImagePosition] == newList[oldImagePosition]
        }
        override fun areContentsTheSame(oldImagePosition: Int, newImagePosition: Int): Boolean {
            return oldList[oldImagePosition] == newList[oldImagePosition]
        }
    }
}