package com.example.travellabel.View.Forum

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.travellabel.R
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Map.MapsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class ForumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forum)

        navbar()
        }


    private fun navbar(){
        val navBottom = findViewById<BottomNavigationView>(R.id.navBottom)
        navBottom.selectedItemId = R.id.forum

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.map -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bookmark -> {
                    val intent = Intent(this, BookmarkActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.forum -> {

                    true
                }
                else -> false
            }
        }
    }
}