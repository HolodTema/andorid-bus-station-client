package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentShopBinding
import com.terabyte.busstationclient.domain.model.shop.VoyageFilterCriteria
import com.terabyte.busstationclient.ui.adapter.ShopVoyageAdapter
import com.terabyte.busstationclient.viewmodel.ShopScreenState
import com.terabyte.busstationclient.viewmodel.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopFragment : Fragment() {

    private val viewModel: ShopViewModel by viewModels()

    private lateinit var binding: FragmentShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowShopScreenState.collect { state ->
                    when (state) {
                        is ShopScreenState.Loading -> {
                            binding.textCaptionFromStation.visibility = View.INVISIBLE
                            binding.textFromStation.visibility = View.INVISIBLE
                            binding.textToStation.visibility = View.INVISIBLE
                            binding.textCaptionToStation.visibility = View.INVISIBLE
                            binding.textCaptionDate.visibility = View.INVISIBLE
                            binding.textDate.visibility = View.INVISIBLE
                            binding.buttonUpdateSearchResults.visibility = View.INVISIBLE
                            binding.buttonExchangeStations.visibility = View.INVISIBLE
                            binding.spinnerFilterBy.visibility = View.INVISIBLE
                            binding.textCaptionFilterBy.visibility = View.INVISIBLE
                            binding.recyclerVoyages.visibility = View.INVISIBLE
                            binding.textCaptionNoVoyages.visibility = View.INVISIBLE

                            binding.progressVoyagesLoading.visibility = View.VISIBLE
                        }
                        is ShopScreenState.Idle -> {
                            binding.textCaptionFromStation.visibility = View.VISIBLE
                            binding.textFromStation.visibility = View.VISIBLE
                            binding.textToStation.visibility = View.VISIBLE
                            binding.textCaptionToStation.visibility = View.VISIBLE
                            binding.textCaptionDate.visibility = View.VISIBLE
                            binding.textDate.visibility = View.VISIBLE
                            binding.buttonUpdateSearchResults.visibility = View.VISIBLE
                            binding.buttonExchangeStations.visibility = View.VISIBLE
                            binding.spinnerFilterBy.visibility = View.VISIBLE
                            binding.textCaptionFilterBy.visibility = View.VISIBLE

                            binding.progressVoyagesLoading.visibility = View.INVISIBLE

                            binding.textFromStation.text = state.startStation.name
                            binding.textToStation.text = state.endStation.name
                            binding.textDate.text = state.date.year.toString()

                            if (state.listVoyages.isEmpty()) {
                                binding.recyclerVoyages.visibility = View.INVISIBLE
                                binding.textCaptionNoVoyages.visibility = View.VISIBLE
                            }
                            else {
                                binding.recyclerVoyages.visibility = View.VISIBLE
                                binding.textCaptionNoVoyages.visibility = View.INVISIBLE

                                val adapter = ShopVoyageAdapter(state.startStation, state.endStation) { voyage ->

                                }
                                binding.recyclerVoyages.adapter = adapter
                                adapter.submitList(state.listVoyages)
                            }

                            binding.buttonExchangeStations.setOnClickListener {
                                viewModel.loadVoyagesByStationsAndDate(state.endStation, state.startStation, state.date)
                            }

                            binding.buttonUpdateSearchResults.setOnClickListener {
                                viewModel.loadVoyagesByStationsAndDate(state.startStation, state.endStation, state.date)
                            }
                        }
                        is ShopScreenState.TokenExpiredError -> {
                            findNavController().navigate(R.id.action_from_shop_to_token_expired_error)
                        }
                        is ShopScreenState.NoInternetError -> {
                            findNavController().navigate(R.id.action_from_shop_to_no_internet_error)
                        }
                    }
                }
            }
        }

        configureSpinner()

        binding.textDate.setOnClickListener {

        }

        binding.textFromStation.setOnClickListener {
            val action = ShopFragmentDirections.actionFromShopToChooseStation("chooseStartStation")
            findNavController().navigate(action)
        }

        binding.textToStation.setOnClickListener {
            val action = ShopFragmentDirections.actionFromShopToChooseStation("chooseEndStation")
            findNavController().navigate(action)
        }

        parentFragmentManager.setFragmentResultListener("chooseStartStation", viewLifecycleOwner) { _, bundle ->
            val startStationId = bundle.getInt("stationId")
            viewModel.updateStartStation(startStationId)
        }
        parentFragmentManager.setFragmentResultListener("chooseEndStation", viewLifecycleOwner) { _, bundle ->
            val endStationId = bundle.getInt("stationId")
            viewModel.updateEndStation(endStationId)
        }
    }

    private fun configureSpinner() {
        val spinnerItems = resources.getStringArray(R.array.voyages_filter_criteria)
        val adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFilterBy.adapter = adapter
        binding.spinnerFilterBy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        viewModel.updateFilterCriteria(VoyageFilterCriteria.BY_PRICE)
                    }
                    1 -> {
                        viewModel.updateFilterCriteria(VoyageFilterCriteria.BY_TIME_OF_DEPARTURE)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                // do nothing
            }
        }
    }
}