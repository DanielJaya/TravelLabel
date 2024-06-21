package com.example.travellabel.View.Forum

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellabel.Data.Adapter.ForumListAdapter
import com.example.travellabel.Response.DiscussionsItem
import com.example.travellabel.View.CreateForum.CreateForumActivity
import com.example.travellabel.View.Reply.ReplyActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityForumBinding

class ForumActivity : AppCompatActivity() {
    private lateinit var viewModel: ForumViewModel
    private var _binding: ActivityForumBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ForumViewModel::class.java)

        var locationId = intent.getStringExtra(EXTRA_LOCATION)
        setupAction(locationId)
//        navbar()
    }

    private fun setupAction(locationId: String?) {
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this@ForumActivity, CreateForumActivity::class.java)
            intent.putExtra(CreateForumActivity.EXTRA_LOCATION, locationId)
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }

        locationId?.let {
            viewModel.getDiscussion(locationId)
        }
        viewModel.listDiscussion.observe(this) { listDiscussion ->
            val forumAdapter = ForumListAdapter()
            forumAdapter.setOnItemClickCallback(object : ForumListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DiscussionsItem) {
                    Intent(this@ForumActivity, ReplyActivity::class.java).also {
                        it.putExtra(ReplyActivity.EXTRA_NAMA, data.creator.username)
                        it.putExtra(ReplyActivity.EXTRA_TITLE, data.title)
                        it.putExtra(ReplyActivity.EXTRA_DESK, data.content)
                        it.putExtra(ReplyActivity.EXTRA_ID, data.id)
                        ViewModelFactory.clearInstance()
                        startActivity(it)
                    }
                }
            })

            forumAdapter.setList(listDiscussion)
            binding.apply {
                val layoutManager = LinearLayoutManager(this@ForumActivity)
                rvForum.layoutManager = layoutManager
                rvForum.setHasFixedSize(true)
                rvForum.adapter = forumAdapter
                if (listDiscussion.isEmpty()) {
                    teksKosong.text = "Forum Diskusi masih kosong nih, ayo buat diskusi baru !"
                } else if (!(listDiscussion.isEmpty())) {
                    teksKosong.text = ""
                }
            }
        }


    }

//    private fun navbar(){
//        val navBottom = findViewById<BottomNavigationView>(R.id.navBottom)
//        navBottom.selectedItemId = R.id.forum
//
//        navBottom.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.home -> {
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.map -> {
//                    val intent = Intent(this, MapsActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.bookmark -> {
//                    val intent = Intent(this, BookmarkActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                R.id.forum -> {
//
//                    true
//                }
//                R.id.profile -> {
//                    val intent = Intent(this, ProfileActivity::class.java)
//                    startActivity(intent)
//                    true
//                }
//                else -> false
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val EXTRA_LOCATION = "extra_location"
    }
}