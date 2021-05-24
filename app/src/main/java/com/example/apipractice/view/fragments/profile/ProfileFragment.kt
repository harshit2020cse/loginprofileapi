package com.example.apipractice.view.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.databinding.FragmentProfileBinding
import com.example.apipractice.util.StorePreferences
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    /* Binding Layout View */
    lateinit var binding: FragmentProfileBinding

    /* Profile ViewModel */
    lateinit var viewModel: ProfileVM

    /* StorePreferences to Store Data */
    lateinit var storePreferences: StorePreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_profile,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(ProfileVM::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Initialize */
        initView()

        /* Set Observers */
        bindObservers()

        /* API Call */
        viewModel.getProfileData()

        /* Set CLick Listener */
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

}