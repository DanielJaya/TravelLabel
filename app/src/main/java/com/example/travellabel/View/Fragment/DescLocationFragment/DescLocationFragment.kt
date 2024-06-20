package com.example.travellabel.View.Fragment.DescLocationFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travellabel.Data.api.Api
import com.example.travellabel.Data.pref.UserPreference
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Data.pref.dataStore
import com.example.travellabel.R
import com.example.travellabel.Response.LocationsItem
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.FragmentDescLocationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DescLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

    private val viewModel: DescLocationViewModel by viewModels {
        ViewModelFactory(UserRepository.getInstance(Api.getApiService(token), UserPreference.getInstance(requireContext().dataStore)))
    }

    private lateinit var token: String
    private lateinit var locationId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDescLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userPreference = UserPreference.getInstance(requireContext().dataStore)
        runBlocking {
            token = userPreference.getSession().first().accessToken ?: "default_token"
        }

        locationId = arguments?.getString(ARG_LOCATION_ID) ?: "default_location_id"

        val label = arguments?.getString(ARG_LABEL)
        val description = arguments?.getString(ARG_DESCRIPTION)

        binding.namaTempatWisata.text = label
        binding.descTempatWisata.text = description

        binding.bookmark.setOnClickListener {
            if (viewModel.bookmarkStatus.value == true) {
                viewModel.removeBookmark(locationId, token)
            } else {
                viewModel.addBookmark(locationId, token)
            }
        }

        viewModel.bookmarkStatus.observe(viewLifecycleOwner, Observer { isBookmarked ->
            if (isBookmarked) {
                Glide.with(this).load(R.drawable.ic_yes_bookmark).into(binding.bookmark)
            } else {
                Glide.with(this).load(R.drawable.ic_bookmark).into(binding.bookmark)
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}