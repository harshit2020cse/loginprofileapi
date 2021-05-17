package com.example.apipractice.view.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentLoginBinding
import com.example.apipractice.databinding.FragmentSplashBinding
import com.example.apipractice.view.fragments.login.LoginViewModel

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    lateinit var splashViewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_splash,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        binding.viewModel = splashViewModel
        binding.executePendingBindings()

        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

        setClickListener()
    }

    private fun setClickListener() {

    }
}