package com.nevidimka655.astracrypt.features.auth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.FragmentAuthBinding
import com.nevidimka655.astracrypt.features.profile.AvatarIds
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.ColorManager
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.astracrypt.utils.extensions.withViewLifecycle

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val vm by activityViewModels<MainVM>()
    private val encryptionInfo get() = vm.encryptionManager.encryptionInfo
    private val authManager get() = vm.authManager
    private val info get() = authManager.info

    private var binding: FragmentAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(requireActivity().window) {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = Color.TRANSPARENT
            setBackgroundDrawableResource(R.drawable.setup_bg)
        }
        binding = FragmentAuthBinding.bind(view)
        setupBinding()
        vm.loadProfileInfo(!encryptionInfo.isAssociatedDataEncrypted)
        vm.profileInfoFlow.withViewLifecycle(viewLifecycleOwner) {
            binding?.run {
                if (encryptionInfo.isAssociatedDataEncrypted) {
                    avatar.setImageResource(AvatarIds.entries.random().resId)
                } else {
                    if (it.defaultAvatar != null) {
                        avatar.setImageResource(AvatarIds.entries[it.defaultAvatar!!].resId)
                    } else avatar.setImageDrawable(it.iconFile)
                }
                userName.text = if (it.name != null) it.name else getString(R.string.user)
            }
        }
    }

    private fun setupBinding() = binding?.run {
        hint.run {
            isVisible = info.hintIsEnabled && info.hint != null
            if (isVisible) setOnClickListener {
                Toast.makeText(requireContext(), info.hint, Toast.LENGTH_SHORT).show()
            }
        }
        passwordBox.run {
            isCounterEnabled = true
            counterMaxLength = AppConfig.AUTH_PASSWORD_MAX_LENGTH
        }
        fab.setOnClickListener {
            val password = passwordEditText.text.toString().trim()
            val isPasswordCorrect = password.isNotEmpty()
                    && password.length <= AppConfig.AUTH_PASSWORD_MAX_LENGTH
                    && authManager.checkPassword(password)
            if (isPasswordCorrect) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                        as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().window.decorView.windowToken, 0)
                if (encryptionInfo.isAssociatedDataEncrypted) {
                    KeysetFactory.initEncryptedAssociatedData(password)
                }
                navigateToMainMenu()
            }
            else Toast.makeText(
                requireContext(), getString(R.string.t_invalidPass), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateToMainMenu() {
        vm.loadProfileInfo()
        findNavController().navigate(R.id.action_global_homeFragment)
        requireActivity().window.decorView.setBackgroundColor(ColorManager.colorSurface)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}