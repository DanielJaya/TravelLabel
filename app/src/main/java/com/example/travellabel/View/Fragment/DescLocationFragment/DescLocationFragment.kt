package com.example.travellabel.View.Fragment.DescLocationFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.travellabel.Data.pref.UserPreference
import com.example.travellabel.Data.pref.dataStore
import com.example.travellabel.R
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.FragmentDescLocationBinding
import com.example.travellabel.Data.Adapter.ReviewAdapter
import com.example.travellabel.View.Forum.ForumActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DescLocationFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_LABEL = "label"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_LOCATION_ID = "location_id"

        fun newInstance(label: String, description: String, locationId: String): DescLocationFragment {
            val fragment = DescLocationFragment()
            val args = Bundle()
            args.putString(ARG_LABEL, label)
            args.putString(ARG_DESCRIPTION, description)
            args.putString(ARG_LOCATION_ID, locationId)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentDescLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var token: String
    private lateinit var locationId: String

    private val viewModel: DescLocationViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DescLocationFragment", "onViewCreated called")

        val adapter = ReviewAdapter(emptyList())
        binding.reviewContainer.layoutManager = LinearLayoutManager(requireContext())
        binding.reviewContainer.adapter = adapter

        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        runBlocking {
            token = userPreference.getSession().first().accessToken ?: "default_token"
        }

        locationId = arguments?.getString(ARG_LOCATION_ID) ?: "default_location_id"

        val label = arguments?.getString(ARG_LABEL)
        val description = arguments?.getString(ARG_DESCRIPTION)

        binding.buttonForum.setOnClickListener {
            val intents = Intent(context, ForumActivity::class.java)
            intents.putExtra(ForumActivity.EXTRA_LOCATION, locationId)
            ViewModelFactory.clearInstance()
            startActivity(intents)
        }

        binding.namaTempatWisata.text = label
        binding.descTempatWisata.text = description

        viewModel.fetchReviews(locationId)

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviews?.let {
                adapter.updateReviews(it)
                Log.d("DescLocationFragment", "Reviews observed: ${it.size} reviews")
            }
        }

        binding.bookmark.setOnClickListener {
            if (viewModel.bookmarkStatus.value == true) {
                viewModel.removeBookmark(locationId, token)
            } else {
                viewModel.addBookmark(locationId, token)
            }
        }

        viewModel.bookmarkStatus.observe(viewLifecycleOwner) { isBookmarked ->
            if (isBookmarked) {
                Glide.with(this).load(R.drawable.ic_yes_bookmark).into(binding.bookmark)
            } else {
                Glide.with(this).load(R.drawable.ic_bookmark).into(binding.bookmark)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
