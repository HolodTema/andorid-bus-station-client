package com.terabyte.busstationclient.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.terabyte.busstationclient.databinding.ListItemVoyageBinding
import com.terabyte.busstationclient.domain.model.shop.Station
import com.terabyte.busstationclient.domain.model.shop.Voyage
import com.terabyte.busstationclient.domain.util.DateFormatHelper

class ShopVoyageHolder(
    private val binding: ListItemVoyageBinding,
    private val startStation: Station,
    private val endStation: Station
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(voyage: Voyage, onBuyClicked: () -> Unit) {
        binding.textName.text = voyage.name
        binding.textStartTime.text = DateFormatHelper.formatDate(voyage.startTime)
        binding.textFromStation.text = startStation.name
        binding.textToStation.text = endStation.name
        binding.textEndTime.text = DateFormatHelper.formatDate(voyage.endTime)
        binding.textPrice.text = "${voyage.cost} RUB"
        binding.textButtonBuy.setOnClickListener {
            onBuyClicked()
        }
    }
}


object VoyageDiffCallback : DiffUtil.ItemCallback<Voyage>() {

    override fun areContentsTheSame(
        oldItem: Voyage,
        newItem: Voyage
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: Voyage,
        newItem: Voyage
    ): Boolean {
        return oldItem.id == newItem.id
    }

}


class ShopVoyageAdapter(
    private val startStation: Station,
    private val endStation: Station,
    private val onBuyClicked: (Voyage) -> Unit
) : ListAdapter<Voyage, ShopVoyageHolder>(VoyageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopVoyageHolder {
        val binding = ListItemVoyageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopVoyageHolder(binding, startStation, endStation)
    }

    override fun onBindViewHolder(holder: ShopVoyageHolder, position: Int) {
        val voyage = getItem(position)
        holder.bind(voyage) {
            onBuyClicked(voyage)
        }
    }
}