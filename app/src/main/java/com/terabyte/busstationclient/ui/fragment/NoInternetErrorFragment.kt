package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentNoInternetErrorBinding

class NoInternetErrorFragment : Fragment() {

    private lateinit var binding: FragmentNoInternetErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoInternetErrorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonTryAgain.setOnClickListener {
            findNavController().navigate(R.id.action_from_no_internet_error_to_shop)
        }
    }
}