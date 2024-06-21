package com.example.travellabel.View.Reply

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellabel.Data.Adapter.ForumListAdapter
import com.example.travellabel.Data.Adapter.ReplyListAdapter
import com.example.travellabel.R
import com.example.travellabel.Response.DiscussionsItem
import com.example.travellabel.Response.RepliesItem
import com.example.travellabel.View.AddReply.AddReplyActivity
import com.example.travellabel.View.CreateForum.CreateForumActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Forum.ForumViewModel
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityForumBinding
import com.example.travellabel.databinding.ActivityReplyBinding

class ReplyActivity : AppCompatActivity() {
    private lateinit var viewModel: ReplyViewModel
    private var _binding: ActivityReplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ReplyViewModel::class.java)

        val discussionId = intent.getStringExtra(EXTRA_ID)
        setupAction(discussionId)
    }

    private fun setupAction(discussionId: String?) {
        var nama = intent.getStringExtra(EXTRA_NAMA)
        var title = intent.getStringExtra(EXTRA_TITLE)
        var desk = intent.getStringExtra(EXTRA_DESK)
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this@ReplyActivity, AddReplyActivity::class.java)
            intent.putExtra(AddReplyActivity.EXTRA_NAMA, nama)
            intent.putExtra(AddReplyActivity.EXTRA_TITLE, title)
            intent.putExtra(AddReplyActivity.EXTRA_DESK, desk)
            intent.putExtra(AddReplyActivity.EXTRA_ID, discussionId)
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }

        binding.namaOrang.text = nama
        binding.judulForum.text = title
        binding.descForum.text = desk

        discussionId?.let {
            viewModel.getReply(discussionId)
        }
        viewModel.listReply.observe(this) { listReply ->
            val replyAdapter = ReplyListAdapter()
            replyAdapter.setOnItemClickCallback(object : ReplyListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: RepliesItem) {
//                    Intent(this@ForumActivity, ReplyActivity::class.java).also {
//                        it.putExtra(ReplyActivity.EXTRA_NAMA, data.creator.username)
//                        it.putExtra(ReplyActivity.EXTRA_TITLE, data.title)
//                        it.putExtra(ReplyActivity.EXTRA_DESK, data.content)
//                        it.putExtra(ReplyActivity.EXTRA_ID, data.id)
//                        ViewModelFactory.clearInstance()
//                        startActivity(it)
//                    }
                }
            })

            replyAdapter.setList(listReply)
            binding.apply {
                val layoutManager = LinearLayoutManager(this@ReplyActivity)
                rvForum.layoutManager = layoutManager
                rvForum.setHasFixedSize(true)
                rvForum.adapter = replyAdapter
            }
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESK = "extra_desk"
        const val EXTRA_ID = "extra_id"
    }
}