package com.example.apipractice.view.fragments.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apipractice.R
import com.example.apipractice.application.AppConstant
import com.example.apipractice.databinding.FragmentProfileBinding
import com.example.apipractice.ui.MediaUtils
import com.example.apipractice.ui.openAppSettings
import com.example.apipractice.util.LogType
import com.example.apipractice.util.StorePreferences
import com.example.apipractice.util.logger
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ProfileFragment : Fragment() {

    /* Binding Layout View */
    lateinit var binding: FragmentProfileBinding

    lateinit var builder: AlertDialog.Builder

    /* Profile ViewModel */
    lateinit var viewModel: ProfileVM

    /* StorePreferences to Store Data */
    lateinit var storePreferences: StorePreferences

    /** Media File Data Members */
    private var mPictureFilePath: String = ""
    private var mPictureFileURI: Uri? = null

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
        builder = AlertDialog.Builder(requireContext())
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

        /* Disable if User is not allow to edit profile */
        if (viewModel.isProfileEditable.get() != true) {
            return
        }

        /*
         * This Profile Screen is now used to see member's profile
         * So Every editing feature will be disabled.
         * */
        viewModel.isProfileEditable.set(false)

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

        /* Edit Profile Picture */
        binding.editProfilePicture.setOnClickListener {

            /* Show Dialog to Choose Image Source */
            chooseImageSource()
        }

        /* Edit Details textView Click*/
        binding.editDetailsTextView.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

//        binding.changePasswordButton.setOnClickListener {
//
//            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//            alertDialog.setTitle("Hello")
//            alertDialog.setMessage("Hi this is Harshit")
//            alertDialog.setIcon(R.mipmap.ic_launcher)
//            alertDialog.setCancelable(false)
//            alertDialog.setPositiveButton("Yes") { dialog, which -> }
//            alertDialog.setNegativeButton("No") { dialog, which -> }
//            alertDialog.setNeutralButton("Don't Know") { dialog, which -> }
//            alertDialog.show()
//        }
    }


    /**
     * Show Dialog to Select Image Source
     * */
    private fun chooseImageSource() {

        /* set message for alert dialog */
        builder.setMessage(R.string.choose_image_source)

        /* Performing positive action */
        builder.setPositiveButton(viewModel.resourceProvider.getString(R.string.camera)) { dialog, which ->
            /* Ask For Permission */
            askCameraPermission()
        }

        /* Performing negative action */
        builder.setNegativeButton(viewModel.resourceProvider.getString(R.string.gallery)) { dialog, which ->
            /* Launch Image Picker */
            launchImagePicker()
        }

        builder.show()

    }

    /**
     * Launch Image Picker
     * */
    private fun launchImagePicker() {
        val pickPhoto = Intent(Intent.ACTION_PICK)
        pickPhoto.type = "image/*"
        if (pickPhoto.resolveActivity(requireContext().packageManager) != null) {
            cameraPickerActivityResult.launch(pickPhoto)
        } else {

            /* Notify User */
            Snackbar.make(
                requireContext(),
                binding.mainLayout,
                viewModel.resourceProvider.getString(R.string.no_relevant_app),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Callback from Activity Results
     * */
    private var cameraPickerActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                /* Get Intent Data */
                val data = result.data

                val exceptionHandler = CoroutineExceptionHandler { _, t ->
                    t.printStackTrace()

                    /* Show Error Message */
                    builder.setMessage(
                        t.localizedMessage
                            ?: viewModel.resourceProvider.getString(R.string.error_message)
                    )

                    builder.show()

                }

                CoroutineScope(Dispatchers.Main + exceptionHandler).launch {

                    var queryImageUrl = ""

                    if (data?.data != null) {

                        /* For Gallery Selection */
                        mPictureFileURI = data.data

                        mPictureFileURI?.let { uri ->
                            mPictureFileURI?.path?.let { path ->
                                queryImageUrl = path
                                queryImageUrl = MediaUtils().compressImageFile(
                                    requireContext(), queryImageUrl, false, uri
                                )
                            }
                        }
                    } else {

                        /* For Camera Capture */
                        queryImageUrl = mPictureFilePath

                        mPictureFileURI?.let { uri ->
                            MediaUtils().compressImageFile(
                                requireContext(),
                                queryImageUrl, uri = uri
                            )
                        }
                    }
                    val file = File(queryImageUrl)

                    /* Update URI Variable */
                    mPictureFileURI = Uri.fromFile(file)

                }
            }
        }

    /** Launch Camera App and take Picture */
    private fun launchCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(requireContext().packageManager) != null) {
            val pictureFile: File?
            try {
                pictureFile = MediaUtils().getPictureFile(requireContext())
                mPictureFilePath = pictureFile.absolutePath
            } catch (e: Exception) {

                logger(javaClass.simpleName, "launchCamera: ${e.message}", LogType.ERROR)

                /* Notify User */
                builder.setMessage(viewModel.resourceProvider.getString(R.string.error_message))
                builder.show()
                return
            }

            if (pictureFile.exists()) {
                pictureFile.deleteOnExit()
            }

            mPictureFileURI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                /*
                * Add Provider XML file and add in Manifest
                * */
                FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().applicationContext.packageName + ".provider",
                    pictureFile
                )
            } else {
                /* Below 23 Version Api get Uri directly */
                Uri.fromFile(pictureFile)
            }

            mPictureFileURI?.let {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, it)
                cameraPickerActivityResult.launch(cameraIntent)
                return
            }

            /* Notify User */
            builder.setMessage(viewModel.resourceProvider.getString(R.string.error_message))
            builder.show()

        } else {

            /* Notify User */
            Snackbar.make(
                requireContext(),
                binding.mainLayout,
                viewModel.resourceProvider.getString(R.string.no_relevant_app),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Callback from Permission Results
     * */
    private var cameraPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { grantResults ->
            if (grantResults[Manifest.permission.CAMERA] == true) {

                /* Launch Camera */
                launchCamera()
            } else {
                /* Notify User */
                Snackbar.make(
                    requireContext(),
                    binding.mainLayout,
                    viewModel.resourceProvider.getString(R.string.camera_permission_denied),
                    Snackbar.LENGTH_SHORT
                ).show()

                /* Notify User to grant permission */
                builder.setTitle(viewModel.resourceProvider.getString(R.string.permission_required))

                builder.setMessage(viewModel.resourceProvider.getString(R.string.provide_permission))

                //performing positive action
                builder.setPositiveButton(viewModel.resourceProvider.getString(R.string.grant_permission)) { dialog, which ->

                    /* Open App Setting Activity */
                    openAppSettings(requireContext())
                }

                //performing negative action
                builder.setNegativeButton(viewModel.resourceProvider.getString(R.string.grant_permission)) { dialog, which ->
                    dialog.dismiss()
                }

                builder.show()
            }
        }


    /**
     * Ask for Camera Permissions
     * */
    private fun askCameraPermission() {
        cameraPermissionResult.launch(arrayOf(Manifest.permission.CAMERA))
    }

}