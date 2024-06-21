package com.example.travellabel.View.AddReply

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.travellabel.R
import com.example.travellabel.Request.AddReplyRequest
import com.example.travellabel.Request.CreateDiscussionRequest
import com.example.travellabel.View.CreateForum.CreateForumViewModel
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Reply.ReplyActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityAddReplyBinding
import com.example.travellabel.databinding.ActivityCreateForumBinding

class AddReplyActivity : AppCompatActivity() {
    private lateinit var viewModel: AddReplyViewModel
    private var _binding: ActivityAddReplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(AddReplyViewModel::class.java)

        var discussionId = intent.getStringExtra(EXTRA_ID)

//        AlertDialog.Builder(this@AddReplyActivity).apply {
//            setTitle("Yeah!")
//            setMessage("Nama : ${nama} \nTitle : ${title} \nDesk : ${desk} \nID : ${discussionId} ! \nAnda berhasil login. Sudah tidak sabar untuk belajar ya?")
//            setPositiveButton("Lanjut") { _, _ ->
//            }
//            create()
//            show()
//        }

        setupAction(discussionId)
    }

    private fun setupAction(discussionId: String?) {
        var nama = intent.getStringExtra(EXTRA_NAMA)
        var title = intent.getStringExtra(EXTRA_TITLE)
        var desk = intent.getStringExtra(EXTRA_DESK)

        binding.buttonSimpan.setOnClickListener {
            val desc = binding.edDesk.text.toString()

            discussionId?.let {

                viewModel.addReply(discussionId, AddReplyRequest(desc))
                viewModel.reply.observe(this) {
                    val intent = Intent(this@AddReplyActivity, ReplyActivity::class.java)
                    intent.putExtra(ReplyActivity.EXTRA_NAMA, nama)
                    intent.putExtra(ReplyActivity.EXTRA_TITLE, title)
                    intent.putExtra(ReplyActivity.EXTRA_DESK, desk)
                    intent.putExtra(ReplyActivity.EXTRA_ID, discussionId)
                    ViewModelFactory.clearInstance()
                    startActivity(intent)
                }
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