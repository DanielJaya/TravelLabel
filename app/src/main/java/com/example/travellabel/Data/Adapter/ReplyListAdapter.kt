package com.example.travellabel.Data.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travellabel.Response.DiscussionsItem
import com.example.travellabel.Response.RepliesItem
import com.example.travellabel.databinding.ItemForumBinding
import com.example.travellabel.databinding.ItemReplyBinding

class ReplyListAdapter : RecyclerView.Adapter<ReplyListAdapter.ReplyViewHolder>(){
    private var list = ArrayList<RepliesItem>()

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: RepliesItem)
    }

    fun setList(data: List<RepliesItem>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }


    inner class ReplyViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RepliesItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(data)
            }

            binding.apply {
                namaOrang.text = data.creator.username
                descForum.text = data.content
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        val binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}