package com.example.apipractice.view.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.base.BaseCommonAdapter
import com.example.apipractice.data.BannerImage
import com.example.apipractice.data.BannerListModel
import com.example.apipractice.databinding.FragmentHomeBinding
import com.example.apipractice.networkcall.BannerListener
import com.example.apipractice.util.StorePreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), BannerListener {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeVM
    lateinit var storePreferences: StorePreferences
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_home,
            container,
            false
        )
        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)
        viewModel = ViewModelProvider(this).get(HomeVM::class.java)
        binding.viewModel = viewModel
        viewModel.bannerListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storePreferences = StorePreferences(requireContext())

        /* Get Banner List */
        viewModel.getBannerList()

        initView()
        /* Set Click Listeners */
        setClickListener()
    }

    /**
     * Initialize
     * */
    private fun initView() {

        if (viewModel.bannerAdapter == null) {

            /* Banner Adapter */
            viewModel.bannerAdapter =
                BaseCommonAdapter(viewModel.bannerAdapterList)
        }
        binding.bannerRecyclerView.adapter = viewModel.bannerAdapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.bannerRecyclerView)
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

    /* Get API Success Response */
    override fun onSuccess(bannerListResponse: LiveData<BannerListModel>) {
        bannerListResponse.observe(this, { it ->

            //TODO Use Coroutines in ViewModel

            viewModel.bannerAdapterList.clear()
            it.data?.forEach { it1 ->
                if (it1._id == AppConstant.BANNER_TYPE.HOME) {
                    it1.urls?.forEach {
                        viewModel.bannerAdapterList.add(
                            BannerHomeItemViewModel(
                                BannerImage(imageUrl = it.en)
                            )
                        )

                    }
                }
            }
            Log.e("ADAPTER", "ADAPTER : " + viewModel.bannerAdapterList.size)
            viewModel.bannerAdapter?.notifyDataSetChanged()
        })
    }
}