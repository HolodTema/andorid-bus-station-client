package com.terabyte.busstationclient.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.busstationclient.databinding.ListItemStationBinding
import com.terabyte.busstationclient.domain.model.shop.Station


class StationHolder(
    private val binding: ListItemStationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(station: Station, onStationClicked: () -> Unit) {
        binding.textStationName.text = station.name
        binding.textStationCoordinates.text = "Latitude: ${station.latitude}\tLongitude: ${station.longitude}"

        binding.root.setOnClickListener {
            onStationClicked()
        }

        binding.textHasBuilding.visibility = if (station.hasBuilding) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
        binding.textHasRestroom.visibility = if (station.hasRestroom) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
        binding.textHasCaffe.visibility = if (station.hasCaffe) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
        binding.textHasTown.visibility = if (station.hasTown) {
            View.VISIBLE
        }
        else {
            View.GONE
        }
    }
}


class StationAdapter(
    private val inflater: LayoutInflater,
    private val stations: List<Station>,
    private val onStationClicked: (Station) -> Unit
) : RecyclerView.Adapter<StationHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StationHolder {
        val binding = ListItemStationBinding.inflate(inflater, parent, false)
        return StationHolder(binding)
    }

    override fun onBindViewHolder(holder: StationHolder, position: Int) {
        val station = stations[position]
        holder.bind(station) {
            onStationClicked(station)
        }
    }

    override fun getItemCount(): Int {
        return stations.size
    }
}
