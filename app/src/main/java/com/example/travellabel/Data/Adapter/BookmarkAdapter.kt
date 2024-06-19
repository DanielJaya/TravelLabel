package com.example.travellabel.Data.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travellabel.Response.LocationsItem1
import com.example.travellabel.databinding.ItemBookmarkBinding

class BookmarkAdapter(private val bookmarkList: List<LocationsItem1?>) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val bookmark = bookmarkList[position]
        holder.bind(bookmark)
    }

    override fun getItemCount(): Int {
        return bookmarkList.size
    }

    class BookmarkViewHolder(private val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: LocationsItem1?) {
            binding.bookmarkTempat.text = bookmark?.label
            binding.descriptionTempat.text = bookmark?.description
        }
    }
}
