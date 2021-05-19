package com.example.apipractice.view.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.AppConstant
import com.example.apipractice.R
import com.example.apipractice.application.MyApplication
import com.example.apipractice.databinding.FragmentHomeBinding
import com.example.apipractice.util.StorePreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeVM
    lateinit var storePreferences: StorePreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storePreferences = StorePreferences(requireContext())

        setClickListener()
    }

    private fun setClickListener() {
        //Logout Button Click
        binding.logoutButton.setOnClickListener {

            // clear dataStore token and userType
            GlobalScope.launch {
                storePreferences.setToken("")
                storePreferences.setUser("")
            }
            val bundle = bundleOf().apply {
                putString(AppConstant.AUTHENTICATION_KEYS.KEY , AppConstant.AUTHENTICATION_KEYS.LOGOUT)
            }
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment,bundle)
        }
    }
}