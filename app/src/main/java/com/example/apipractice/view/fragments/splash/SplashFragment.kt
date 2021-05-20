package com.example.apipractice.view.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.apipractice.AppConstant
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentSplashBinding
import com.example.apipractice.util.StorePreferences

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding
    private lateinit var splashViewModel: SplashViewModel
    lateinit var storePreferences: StorePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        storePreferences = StorePreferences(requireContext())
        storePreferences.readValue(StorePreferences.USER_TYPE).asLiveData()
            .observe(requireActivity(), {
                if (it == AppConstant.USER_TYPE.PATIENT) {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                }
            })
        setClickListener()
    }

    private fun setClickListener() {

    }
}