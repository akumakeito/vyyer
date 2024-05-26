package com.akumakeito.vyyer.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.akumakeito.vyyer.R
import com.akumakeito.vyyer.databinding.FragmentAuthBinding
import com.akumakeito.vyyer.domain.state.AuthState
import com.akumakeito.vyyer.presentation.util.addTextChangedListener
import com.akumakeito.vyyer.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw IllegalStateException("Uninitialized binding")

    private val viewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            etPassword.addTextChangedListener {
                viewModel.onPasswordChanged(it)
            }

            etUsername.addTextChangedListener {
                viewModel.onUsernameChanged(it)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.isButtonEnable.collect {
                    btnLogin.isEnabled = it
                }
            }

            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(username, password)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collect { state ->
                    when (state) {
                        is AuthState.Authenticated -> {
                            if (state.isAuthenticated) {
                                findNavController().navigate(R.id.action_authFragment_to_scanHistoryFragment)
                                viewModel.clearCred()
                            }
                        }
                        is AuthState.Error -> {
                            showToast(requireContext(), state.message)
                            progressBar.visibility = View.GONE
                        }
                        AuthState.Loading ->  progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}