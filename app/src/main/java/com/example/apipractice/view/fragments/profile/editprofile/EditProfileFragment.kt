package com.example.apipractice.view.fragments.profile.editprofile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    /* Binding Layout View */
    lateinit var binding: FragmentEditProfileBinding

    /* Edit Profile ViewModel */
    lateinit var viewModel: EditProfileVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_edit_profile,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(EditProfileVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Set Observers */
        bindObservers()

        /* Set Click Listener */
        setClickListener()
    }

    /** Set Observers to capture actions */
    private fun bindObservers() {

        /* Observe Profile Data */
        viewModel.profileResponse.observe(viewLifecycleOwner, {
            Log.e(ContentValues.TAG, "Response $it")

            if (it.data?.status == true) {
                val bundle = bundleOf().apply {
                    putString(AppConstant.EDITPROFILE.KEY, AppConstant.EDITPROFILE.EDIT_PROFILE)
                }
                /* Navigate to Profile Screen */
                findNavController().navigate(
                    R.id.action_editProfileFragment_to_profileFragment,
                    bundle
                )
            }
        })
    }

    /**
     * Set Click Listeners
     */
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

}