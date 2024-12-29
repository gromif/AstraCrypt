package com.nevidimka655.features.lab_zip

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.domain.lab_zip.FileInfo
import com.nevidimka655.domain.lab_zip.usecase.GetFileInfosUseCase
import com.nevidimka655.domain.lab_zip.usecase.GetSourceFileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CombineZipViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val getSourceFileInfoUseCase: GetSourceFileInfoUseCase,
    private val getFileInfosUseCase: GetFileInfosUseCase,
    private val uriToStringMapper: Mapper<Uri, String>
) : ViewModel() {
    val filesListState = mutableStateListOf<FileInfo>()
    var sourceState by mutableStateOf<FileInfo?>(null)

    fun setSource(sourceUri: Uri) = viewModelScope.launch(defaultDispatcher) {
        sourceState = getSourceFileInfoUseCase(uriToStringMapper(sourceUri))
    }

    fun addFiles(uris: List<Uri>) = viewModelScope.launch(defaultDispatcher) {
        val paths = uris.map { uriToStringMapper(it) }
        filesListState.addAll(getFileInfosUseCase(paths))
    }

    fun resetSource() {
        sourceState = null
    }

    fun resetFiles() = filesListState.clear()

}