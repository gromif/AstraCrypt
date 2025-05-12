package io.gromif.tink_lab.presentation.key

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.domain.usecase.CreateLabKeyUseCase
import io.gromif.tink_lab.domain.usecase.GetFileAeadListUseCase
import io.gromif.tink_lab.domain.usecase.GetTextAeadListUseCase
import io.gromif.tink_lab.domain.usecase.LoadKeyUseCase
import io.gromif.tink_lab.domain.usecase.SaveKeyUseCase
import io.gromif.tink_lab.domain.util.KeyReader
import io.gromif.tink_lab.presentation.key.saver.DataTypeSaver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val UI_MODE = "ui_mode"
private const val KEYSET_PASSWORD = "kp"
private const val DATA_TYPE = "data_type"
private const val AEAD_TYPE = "aead_type"

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
internal class KeyViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val state: SavedStateHandle,
    private val createLabKeyUseCase: CreateLabKeyUseCase,
    private val saveKeyUseCase: SaveKeyUseCase,
    private val loadKeyUseCase: LoadKeyUseCase,
    private val uriToStringMapper: Mapper<Uri, String>,
    getFileAeadListUseCase: GetFileAeadListUseCase,
    getTextAeadListUseCase: GetTextAeadListUseCase
) : ViewModel() {
    val fileAeadList = getFileAeadListUseCase()
    val textAeadList = getTextAeadListUseCase()

    val uiMode = state.saveable(UI_MODE) { mutableStateOf<UiMode>(UiMode.CreateKey) }
    var keysetPassword by state.saveable(KEYSET_PASSWORD) { mutableStateOf("") }
    val aeadType = state.saveable(AEAD_TYPE) { mutableStateOf(fileAeadList[0]) }
    val dataType = state.saveable(key = DATA_TYPE, stateSaver = DataTypeSaver()) {
        mutableStateOf(DataType.Files)
    }

    fun createKey(dataType: DataType, aeadType: String): Key =
        createLabKeyUseCase(dataType, aeadType.uppercase())

    suspend fun save(key: Key, uri: Uri) = withContext(defaultDispatcher) {
        val keysetPassword = keysetPassword
        val uriString = uriToStringMapper(uri)
        saveKeyUseCase(key = key, path = uriString, password = keysetPassword)
    }

    suspend fun load(path: String) = withContext(defaultDispatcher) {
        val result = loadKeyUseCase(path = path, password = keysetPassword)
        if (result is KeyReader.Result.Success) result.key else null
    }

}