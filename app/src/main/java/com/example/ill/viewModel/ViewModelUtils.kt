package com.example.ill.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
inline fun <reified T : ViewModel> getViewModel(factory: ViewModelProvider.Factory): T {
    val context = LocalContext.current
    val owner = LocalViewModelStoreOwner.current
    return ViewModelProvider(owner!!, factory)[T::class.java]
}
