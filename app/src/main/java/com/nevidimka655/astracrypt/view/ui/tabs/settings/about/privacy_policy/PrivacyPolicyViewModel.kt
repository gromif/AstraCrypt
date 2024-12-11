package com.nevidimka655.astracrypt.view.ui.tabs.settings.about.privacy_policy

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrivacyPolicyViewModel: ViewModel() {
    var html by mutableStateOf("")
        private set

    fun load(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        context.assets.open("privacy_policy.html").use {
            html = it.readBytes().decodeToString()
        }
    }

}