package com.terabyte.busstationclient.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.terabyte.busstationclient.R
import com.terabyte.busstationclient.databinding.FragmentLoginBinding
import com.terabyte.busstationclient.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: FragmentLoginBinding

    private lateinit var backCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Ничего не делаем – кнопка "Назад" игнорируется
                // Можно показать диалог "Нельзя вернуться"
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlowLoginScreenState.collect { state ->
                    if (state.isLoading) {
                        binding.progressLoginLoading.visibility = View.VISIBLE
                    }
                    else {
                        binding.progressLoginLoading.visibility = View.GONE
                    }

                    if (state.isLoginError) {
                        binding.textCaptionError.visibility = View.VISIBLE
                    }
                    else {
                        binding.textCaptionError.visibility = View.GONE
                    }

                    if (state.loginSuccess) {
                        findNavController().navigate(R.id.action_from_login_to_shop)
                    }
                }
            }
        }

        binding.textCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_from_login_to_register)
        }

        binding.buttonLogin.setOnClickListener {
            viewModel.login()
        }

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

    override fun onDestroy() {
        backCallback.remove()
        super.onDestroy()
    }

}