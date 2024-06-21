package com.example.travellabel.View.CreateForum

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.travellabel.R
import com.example.travellabel.Request.CreateDiscussionRequest
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Forum.ForumViewModel
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityCreateForumBinding

class CreateForumActivity : AppCompatActivity() {
    private lateinit var viewModel: CreateForumViewModel
    private var _binding: ActivityCreateForumBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(CreateForumViewModel::class.java)

        var locationId = intent.getStringExtra(EXTRA_LOCATION)
        setupAction(locationId)
    }

    private fun setupAction(locationId: String?) {
        binding.buttonSimpan.setOnClickListener {
            val judul = binding.edJudul.text.toString()
            val desc = binding.edDesk.text.toString()

            locationId?.let {
                val createDiscussionRequest = CreateDiscussionRequest(it, judul, desc)
                viewModel.createDiscussion(createDiscussionRequest)
                viewModel.discussion.observe(this) {
                    val intent = Intent(this@CreateForumActivity, ForumActivity::class.java)
                    intent.putExtra(ForumActivity.EXTRA_LOCATION, locationId)
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
        const val EXTRA_LOCATION = "extra_location"
    }
}