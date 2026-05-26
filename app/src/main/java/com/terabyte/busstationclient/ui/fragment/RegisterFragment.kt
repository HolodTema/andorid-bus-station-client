package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentRegisterBinding
import com.terabyte.busstationclient.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowRegisterScreenState.collect { state ->
                    if (state.isLoading) {
                        binding.progressLoginLoading.visibility = View.VISIBLE
                    }
                    else {
                        binding.progressLoginLoading.visibility = View.GONE
                    }

                    if (state.isErrorEmailBusy) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_email_is_busy)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorNameValidation) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_name_validation)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorSurnameValidation) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_surname_validation)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorEmailValidation) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_email_validation)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorPasswordValidation) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_password_validation)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorUnableToRegister) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_unable_to_register)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.isErrorEmailBusy) {
                        binding.textCaptionError.visibility = View.VISIBLE
                        binding.textCaptionError.text = getString(R.string.error_email_is_busy)
                        return@collect
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.registrationSuccess) {
                        findNavController().navigate(R.id.action_from_register_to_shop)
                    }
                }
            }
        }

        binding.textOrLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_from_register_to_login)
        }
        binding.buttonCreateAccount.setOnClickListener {
            viewModel.register()
        }

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.updateName(p0.toString())
            }
        })

        binding.editSurname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.updateSurname(p0.toString())
            }
        })

        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.updateEmail(p0.toString())
            }
        })

        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                viewModel.updatePassword(p0.toString())
            }
        })
    }

}