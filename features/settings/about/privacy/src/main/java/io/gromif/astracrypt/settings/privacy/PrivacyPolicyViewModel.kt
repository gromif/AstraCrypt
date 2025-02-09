package io.gromif.astracrypt.settings.privacy

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PrivacyPolicyViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {
    var html by mutableStateOf("")
        private set

    fun load(context: Context) = viewModelScope.launch(defaultDispatcher) {
        context.assets.open("privacy_policy.html").use {
            html = it.readBytes().decodeToString()
        }
    }

}