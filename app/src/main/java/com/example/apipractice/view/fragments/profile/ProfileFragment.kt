package com.example.apipractice.view.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.application.AppConstant
import com.example.apipractice.R
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.databinding.FragmentProfileBinding
import com.example.apipractice.networkcall.ProfileListener
import com.example.apipractice.util.StorePreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(), ProfileListener {

    lateinit var binding: FragmentProfileBinding
    lateinit var viewModel: ProfileVM
    lateinit var storePreferences: StorePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ProfileVM::class.java)
        binding.viewModel = viewModel

        /* ProfileListener Interface */
        viewModel.profileListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storePreferences = StorePreferences(requireContext())

        /* Profile Updated SnackBar */
        when (arguments?.getString(AppConstant.EDITPROFILE.KEY)) {
            AppConstant.EDITPROFILE.EDIT_PROFILE ->
                Snackbar.make(
                    requireContext(),
                    binding.mainLayout,
                    resources.getString(R.string.profile_updated_successfully),
                    Snackbar.LENGTH_SHORT
                ).show()
        }
        /* API Call */
        viewModel.getProfileData()

        /* Set CLick Listener */
        setClickListener()
    }

    /**
     * Set Click Listeners
     */
    private fun setClickListener() {

        /* Edit Details textView Click*/
        binding.editDetailsTextView.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    /** Get API Success Response */
    override fun onSuccess(profileResponseResponse: LiveData<ProfileModel>) {
        profileResponseResponse.observe(this, Observer {
            GlobalScope.launch {

                /* Set UI data */
                it.data?.let { it1 ->
                    viewModel.setUIData(it1)

                    /* Store Profile data in DataStore */
                    storePreferences.storeValue(StorePreferences.DEMAND_PROFILE_DATA, it1)
                    viewModel.app.setProfileData(it1)
                }

            }
        })
    }
}