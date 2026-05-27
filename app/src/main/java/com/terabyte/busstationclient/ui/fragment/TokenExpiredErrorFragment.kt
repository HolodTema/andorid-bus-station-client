package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentTokenExpiredErrorBinding

class TokenExpiredErrorFragment : Fragment() {

    private lateinit var binding: FragmentTokenExpiredErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTokenExpiredErrorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogInAgain.setOnClickListener {
            findNavController().navigate(R.id.action_from_token_expired_error_to_login)
        }
    }
}