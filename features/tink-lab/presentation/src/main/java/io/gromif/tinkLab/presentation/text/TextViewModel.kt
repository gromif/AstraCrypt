package io.gromif.tinkLab.presentation.text

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.tinkLab.domain.model.EncryptionException
import io.gromif.tinkLab.domain.model.EncryptionResult
import io.gromif.tinkLab.domain.usecase.DecryptTextUseCase
import io.gromif.tinkLab.domain.usecase.EncryptTextUseCase
import io.gromif.tinkLab.domain.usecase.ParseKeysetUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ASSOCIATED_DATA = "adata"
private const val TEXT = "utext"

@HiltViewModel
internal class TextViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val parseKeysetUseCase: ParseKeysetUseCase,
    private val encryptTextUseCase: EncryptTextUseCase,
    private val decryptTextUseCase: DecryptTextUseCase,
) : ViewModel() {
    val associatedDataState = state.getStateFlow(ASSOCIATED_DATA, "")
    val textState = state.getStateFlow(TEXT, "")

    private val encryptionExceptionsChannel = Channel<EncryptionException>()
    val encryptionExceptionsFlow = encryptionExceptionsChannel.receiveAsFlow()

    fun parseKeysetHandle(rawKeyset: String) = viewModelScope.launch {
        parseKeysetUseCase(rawKeyset)
    }

    fun encrypt() = viewModelScope.launch {
        val encryptionResult = encryptTextUseCase(
            text = textState.value,
            associatedData = associatedDataState.value
        )

        when (encryptionResult) {
            is EncryptionResult.Error -> encryptionExceptionsChannel.send(encryptionResult.encryptionException)
            is EncryptionResult.Success -> setText(encryptionResult.text)
        }
    }

    fun decrypt() = viewModelScope.launch {
        val encryptionResult = decryptTextUseCase(
            encryptedText = textState.value,
            associatedData = associatedDataState.value
        )

        when (encryptionResult) {
            is EncryptionResult.Error -> encryptionExceptionsChannel.send(encryptionResult.encryptionException)
            is EncryptionResult.Success -> setText(encryptionResult.text)
        }
    }

    fun setAssociatedData(data: String) = state.set(ASSOCIATED_DATA, data)
    fun setText(text: String) = state.set(TEXT, text)
}
