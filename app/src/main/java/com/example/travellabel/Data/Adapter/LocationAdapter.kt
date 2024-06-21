package com.example.travellabel.Data.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travellabel.Response.LocationsItem
import com.example.travellabel.databinding.ItemRecommendationBinding

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private var locationList = mutableListOf<LocationsItem>()

    fun setLocations(locations: List<LocationsItem>) {
        this.locationList = locations.toMutableList()
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(location: LocationsItem) {
            binding.recTempatWisata.text = location.label
            binding.descTempatWisata.text = location.description
            // You can set the image and other properties here
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locationList[position])
    }

    override fun getItemCount(): Int = locationList.size
}