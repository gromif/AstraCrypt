package io.gromif.astracrypt.settings.aead

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadLargeStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadSmallStreamTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetAeadTemplateListUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.GetNotesAeadTemplateUseCase
import io.gromif.astracrypt.settings.aead.domain.usecase.SetNotesAeadUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AeadViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val setNotesAeadUseCase: SetNotesAeadUseCase,
    getNotesAeadNameUseCase: GetNotesAeadTemplateUseCase,
    getAeadTemplateListUseCase: GetAeadTemplateListUseCase,
    getAeadLargeStreamTemplateListUseCase: GetAeadLargeStreamTemplateListUseCase,
    getAeadSmallStreamTemplateListUseCase: GetAeadSmallStreamTemplateListUseCase
): ViewModel() {
    val aeadTemplateList = getAeadTemplateListUseCase()
    val aeadLargeStreamTemplateList = getAeadLargeStreamTemplateListUseCase()
    val aeadSmallStreamTemplateList = getAeadSmallStreamTemplateListUseCase()

    val notesAeadNameState = getNotesAeadNameUseCase().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), null
    )

    fun setNotesAead(id: Int) = viewModelScope.launch(defaultDispatcher) {
        setNotesAeadUseCase(aead = id)
    }

}