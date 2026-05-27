package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentChooseStationBinding
import com.terabyte.busstationclient.ui.adapter.StationAdapter
import com.terabyte.busstationclient.viewmodel.ChooseStationScreenState
import com.terabyte.busstationclient.viewmodel.ChooseStationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChooseStationFragment : Fragment() {

    private val viewModel: ChooseStationViewModel by viewModels()

    private lateinit var binding: FragmentChooseStationBinding

    private val args: ChooseStationFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseStationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowScreenState.collect { state ->
                    when (state) {
                        is ChooseStationScreenState.Loading -> {
                            binding.progressLoading.visibility = View.VISIBLE
                            binding.recyclerStations.visibility = View.INVISIBLE
                            binding.textCaptionChooseStation.visibility = View.INVISIBLE
                        }

                        ChooseStationScreenState.TokenExpiredError -> {
                            findNavController().navigate(R.id.action_from_choose_station_to_token_expired_error)
                        }

                        is ChooseStationScreenState.NoInternetError -> {
                            findNavController().navigate(R.id.action_from_choose_station_to_no_internet_error)
                        }

                        is ChooseStationScreenState.Idle -> {
                            binding.progressLoading.visibility = View.INVISIBLE
                            binding.recyclerStations.visibility = View.VISIBLE
                            binding.textCaptionChooseStation.visibility = View.VISIBLE

                            val adapter = StationAdapter(layoutInflater, state.stations) { station ->
                                val bundle = Bundle().apply {
                                    putInt("stationId", station.id)
                                }
                                setFragmentResult(args.stationType, bundle)
                                findNavController().popBackStack()
                            }
                            binding.recyclerStations.adapter = adapter
                        }
                    }
                }
            }
        }
    }
}
