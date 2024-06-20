package com.example.travellabel.View.Forum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.travellabel.Response.DiscussionsItem
import com.example.travellabel.databinding.ItemForumBinding

class ForumListAdapter : RecyclerView.Adapter<ForumListAdapter.ForumViewHolder>(){
    private var list = ArrayList<DiscussionsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DiscussionsItem)
    }

    fun setList(storyItem: List<DiscussionsItem>){
        list.clear()
        list.addAll(storyItem)
        notifyDataSetChanged()
    }


    inner class ForumViewHolder(val binding: ItemForumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DiscussionsItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }

            binding.apply {
                namaOrang.text = data.creatorId
                judulForum.text = data.title
                descForum.text = data.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumViewHolder {
        val binding = ItemForumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForumViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}