package com.example.main.injection

import com.example.main.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object MainModule {

    val mainModule = module {
        viewModel {
            MainViewModel()
        }
    }
}