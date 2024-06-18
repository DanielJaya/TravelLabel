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
import androidx.recyclerview.widget.RecyclerView
import com.example.travellabel.R
import com.example.travellabel.Response.LocationsItem
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.databinding.FragmentDescLocationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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

        fun newInstance(label: String, description: String): DescLocationFragment {
            val fragment = DescLocationFragment()
            val args = Bundle()
            args.putString(ARG_LABEL, label)
            args.putString(ARG_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_desc_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val label = arguments?.getString(ARG_LABEL)
        val description = arguments?.getString(ARG_DESCRIPTION)

        view.findViewById<TextView>(R.id.namaTempatWisata).text = label
        view.findViewById<TextView>(R.id.descTempatWisata).text = description
    }
}