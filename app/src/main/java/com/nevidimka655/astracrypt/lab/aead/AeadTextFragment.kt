package com.nevidimka655.astracrypt.lab.aead

import android.content.ClipData
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.databinding.FragmentLabTextBinding
import com.nevidimka655.astracrypt.utils.extensions.clipboardManager
import com.nevidimka655.astracrypt.utils.extensions.ui.setTooltip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AeadTextFragment : Fragment(R.layout.fragment_lab_text) {
    private val vm by activityViewModels<MainVM>()
    private val labManager get() = vm.toolsManager.labManager
    private var binding: FragmentLabTextBinding? = null

    private val clipboardManager by lazy {
        requireContext().clipboardManager()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLabTextBinding.bind(view).apply {
            encrypt.setOnClickListener { encryptText() }
            decrypt.setOnClickListener { decryptText() }
            clear.setOnClickListener { updateText("") }
            copy.run {
                setOnClickListener { copyText() }
                setTooltip(R.string.copy)
            }
            paste.run {
                setOnClickListener { pasteText() }
                setTooltip(R.string.paste)
            }
        }
    }

    private fun decryptText() = lifecycleScope.launch(Dispatchers.Main) {
        val decryptedText = withContext(Dispatchers.Default) {
            labManager.decryptText(
                text = getText(),
                associatedData = getAssociatedData()
            )
        }
        if (decryptedText != null) updateText(decryptedText)
    }

    private fun encryptText() = lifecycleScope.launch(Dispatchers.Main) {
        val encryptedText = withContext(Dispatchers.Default) {
            labManager.encryptText(
                text = getText(),
                associatedData = getAssociatedData()
            )
        }
        if (encryptedText != null) updateText(encryptedText)
    }

    private fun updateText(text: String) {
        binding?.text?.editText?.setText(text)
    }

    private fun pasteText() {
        val textFromClipboard = clipboardManager.primaryClip?.getItemAt(0)?.text ?: ""
        binding?.run {
            text.editText!!.setText(textFromClipboard)
        }
    }

    private fun copyText() {
        val text = binding?.run {
            text.editText!!.text.toString()
        } ?: ""
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText(
                "AstraCrypt - text",
                text
            )
        )
        Toast.makeText(requireContext(), R.string.copied, Toast.LENGTH_SHORT).show()
    }

    private fun getText() = binding?.run { text.editText!!.text.toString() } ?: ""
    private fun getAssociatedData() =
        binding?.run { associatedData.editText!!.text.toString() } ?: ""

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}