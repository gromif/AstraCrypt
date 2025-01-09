package io.gromif.astracrypt.settings.aead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadLargeStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadSmallStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetNotesAeadTemplateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class AeadViewModel @Inject constructor(
    getNotesAeadTemplateUseCase: GetNotesAeadTemplateUseCase,
    getAeadTemplateListUseCase: GetAeadTemplateListUseCase,
    getAeadLargeStreamTemplateListUseCase: GetAeadLargeStreamTemplateListUseCase,
    getAeadSmallStreamTemplateListUseCase: GetAeadSmallStreamTemplateListUseCase
): ViewModel() {
    val aeadTemplateList = getAeadTemplateListUseCase()
    val aeadLargeStreamTemplateList = getAeadLargeStreamTemplateListUseCase()
    val aeadSmallStreamTemplateList = getAeadSmallStreamTemplateListUseCase()

    val notesAeadFlow = getNotesAeadTemplateUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), AeadTemplate(0, "")
    )

}