package com.nevidimka655.astracrypt.utils

import android.app.Activity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PrivacyPolicyManager @Inject constructor() {

    private var _privacyPolicyMutableStateFlow: MutableStateFlow<String?>? = null
    val privacyPolicyStateFlow get() = run {
        initPrivacyPolicyStateFlow()
        _privacyPolicyMutableStateFlow!!.asStateFlow()
    }

    private fun initPrivacyPolicyStateFlow() {
        if (_privacyPolicyMutableStateFlow == null)
            _privacyPolicyMutableStateFlow = MutableStateFlow(null)
    }

    suspend fun loadPrivacyPolicy(activity: Activity) {
        activity.assets.open("privacy_policy.html").use {
            val privacyPolicyText = it.readBytes().decodeToString()
            _privacyPolicyMutableStateFlow?.emit(privacyPolicyText)
        }
    }

    fun clear() {
        _privacyPolicyMutableStateFlow = null
    }

}