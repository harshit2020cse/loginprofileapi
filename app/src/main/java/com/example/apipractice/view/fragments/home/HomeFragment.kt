package com.example.apipractice.view.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.base.BaseCommonAdapter
import com.example.apipractice.databinding.FragmentHomeBinding
import com.example.apipractice.util.StorePreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    /* Binding Layout View */
    lateinit var binding: FragmentHomeBinding

    /* Edit Profile ViewModel */
    lateinit var viewModel: HomeVM

    /* StorePreferences to Store Data */
    lateinit var storePreferences: StorePreferences

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home,
            container,
            false
        )
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Get Banner List */
        viewModel.getBannerList()

        /* Initialize */
        initView()

        /* Set Observers */
        bindObservers()

        /* Set Click Listeners */
        setClickListener()
    }

    /**
     * Initialize
     * */
    private fun initView() {

        storePreferences = StorePreferences(requireContext())

    }

    /** Set Observers to capture actions */
    private fun bindObservers() {
        viewModel.apiResponse.observe(viewLifecycleOwner, {
            if (viewModel.bannerAdapter == null) {

                /* Banner Adapter */
                viewModel.bannerAdapter =
                    BaseCommonAdapter(viewModel.bannerAdapterList)
            }
            binding.bannerRecyclerView.adapter = viewModel.bannerAdapter

            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.bannerRecyclerView)
            Log.e("Banner", "${viewModel.bannerAdapterList}")
        })

    }

    /**
     * Set Click Listeners
     */
    private fun setClickListener() {
        /** Logout Button Click */
        binding.logoutButton.setOnClickListener {

            /** Clear DataStore Token and UserType */
            GlobalScope.launch {
                storePreferences.storeValue(StorePreferences.TOKEN, "")
                storePreferences.storeValue(StorePreferences.USER_TYPE, "")
                storePreferences.storeValue(StorePreferences.DEMAND_PROFILE_DATA, "")
            }
            val bundle = bundleOf().apply {
                putString(
                    AppConstant.AUTHENTICATION_KEYS.KEY,
                    AppConstant.AUTHENTICATION_KEYS.LOGOUT
                )
            }

            /* Navigate to Login */
            navController.navigate(R.id.action_global_loginFragment, bundle)
        }
    }

}