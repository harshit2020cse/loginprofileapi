package com.example.apipractice.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * BaseFragment:
 *
 * T : Auto Generate When Layout File Converted into <layout>...</layout> Data Binding
 * V : Class which extends ViewModel Class {@link ViewModel()}
 *
 * */
abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment() {

    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: V
    lateinit var binding: T

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        viewModel = ViewModelProvider(this, viewModelFactory).get(setViewModel())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)

        /* Bind Views */
        bindViews(viewModel)
    }

    /**
     * @return Layout Resource Such as R.layout.main_layout
     * */
    abstract fun setLayout(): Int

    /**
     * @return Class which extends {@link ViewModel}
     * */
    abstract fun setViewModel(): Class<V>

    /**
     * Bind Views
     * e.g. binding.viewModel(variable name of the layout data) = viewModel
     * */
    abstract fun bindViews(viewModel: V)


}