package com.ziv_nergal.gerva.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingActivity<Binding : ViewDataBinding> : AppCompatActivity() {

    protected val binding: Binding by lazy {
        DataBindingUtil.inflate(layoutInflater, layoutResource, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    abstract val layoutResource: Int
}