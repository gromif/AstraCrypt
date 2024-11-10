package com.nevidimka655.astracrypt.tabs.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.FragmentPrivacyPolicyBinding
import com.nevidimka655.astracrypt.utils.extensions.withViewLifecycle

class PrivacyPolicyFragment : Fragment(R.layout.fragment_privacy_policy) {
    private val vm by activityViewModels<MainVM>()
    private val assetsManager get() = vm.assetsManager
    private var binding: FragmentPrivacyPolicyBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrivacyPolicyBinding.bind(view)
        assetsManager.privacyPolicyStateFlow.withViewLifecycle(viewLifecycleOwner) {
            if (it != null) binding?.webView?.loadData(it, "text/html", "utf-8")
        }
        vm.loadPrivacyPolicy(requireActivity())
    }
}