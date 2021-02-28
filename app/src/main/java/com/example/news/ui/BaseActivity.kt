package com.example.news.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM: ViewModel, DB: ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: VM
    lateinit var viewDataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initDataBinding()
    }

    fun initViewModel() {
        viewModel = getViewModelClass()
    }

    fun initDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
    }

    abstract fun getLayoutId():Int
    abstract fun getViewModelClass(): VM
}