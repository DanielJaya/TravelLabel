package com.example.travellabel.View.AddReview

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.travellabel.View.Fragment.DescLocationFragment.DescLocationViewModel
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityAddReviewBinding


class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding
    private val viewModel: DescLocationViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var locationId: String
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationId = intent.getStringExtra("location_id") ?: ""
        token = intent.getStringExtra("token") ?: ""

        binding.submitReviewButton.setOnClickListener {
            val content = binding.edAddDescReview.text.toString()
            val selectedRatingId = binding.ratingRadioGroup.checkedRadioButtonId

            if (selectedRatingId != -1) {
                val selectedRating = findViewById<RadioButton>(selectedRatingId).text.toString()
                viewModel.submitReview(locationId, content, selectedRating, token)
                finish()
            } else {
                // Handle the case where no rating is selected
            }
        }
    }
}