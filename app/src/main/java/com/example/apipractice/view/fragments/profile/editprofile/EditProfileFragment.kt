package com.example.apipractice.view.fragments.profile.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var editProfileViewModel: EditProfileViewModel

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        editProfileViewModel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        binding.viewModel = editProfileViewModel
        binding.executePendingBindings()

        setClickListener()
    }

    private fun setClickListener() {

        /* Cancel Button Click */
        binding.cancelButton.setOnClickListener {

            /* Go Back */
            findNavController().navigateUp()
        }
    }
}