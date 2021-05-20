package com.example.apipractice.view.fragments.profile.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.AppConstant
import com.example.apipractice.R
import com.example.apipractice.data.ProfileModel
import com.example.apipractice.databinding.FragmentEditProfileBinding
import com.example.apipractice.networkcall.ProfileListener

class EditProfileFragment : Fragment(), ProfileListener {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var viewModel: EditProfileVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_edit_profile,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(EditProfileVM::class.java)
        binding.viewModel = viewModel
        viewModel.profileListener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.app.getProfileData()?.let { viewModel.setUiData(it) }
        setClickListener()
    }

    private fun setClickListener() {

        /* Cancel Button Click */
        binding.cancelButton.setOnClickListener {

            /* Go Back */
            findNavController().navigateUp()
        }

        /* Update Button Click */
        binding.updateButton.setOnClickListener {
            viewModel.updateProfileData()
        }
    }

    /** Api Success*/
    override fun onSuccess(profileResponseResponse: LiveData<ProfileModel>) {
        profileResponseResponse.observe(this, Observer {
            val bundle = bundleOf().apply {
                putString(AppConstant.EDITPROFILE.KEY, AppConstant.EDITPROFILE.EDIT_PROFILE)
            }
            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment, bundle)
        })
    }
}