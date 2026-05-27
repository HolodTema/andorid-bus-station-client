package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.terabyte.busstationclient.databinding.FragmentChooseStationBinding
import com.terabyte.busstationclient.viewmodel.ChooseStationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseStationFragment : Fragment() {

    private val viewModel: ChooseStationViewModel by viewModels()

    private lateinit var binding: FragmentChooseStationBinding

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

    }
}
