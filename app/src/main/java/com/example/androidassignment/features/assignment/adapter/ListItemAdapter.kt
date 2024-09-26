package com.example.androidassignment.features.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidassignment.databinding.ListItemBinding


class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>() {

    private val items =  ArrayList<ListItem>()


    inner class ListItemViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            itemImage.setImageResource(item.imageResId)
            itemTitle.text = item.title
            itemSubtitle.text = item.subtitle
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<ListItem>) {
        val diffCallback = ItemsCallback(items, newItems)
        val diffItems = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(newItems)
        diffItems.dispatchUpdatesTo(this)

    }


    inner class ItemsCallback(private val oldList: List<ListItem>, private val newList: List<ListItem>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] === newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val (_, title, subtitle) = oldList[oldItemPosition]
            val (_, title1, subtitle1) = newList[newItemPosition]
            return title == title1 && subtitle == subtitle1
        }
    }
}

data class ListItem(val imageResId: Int, val title: String, val subtitle: String)