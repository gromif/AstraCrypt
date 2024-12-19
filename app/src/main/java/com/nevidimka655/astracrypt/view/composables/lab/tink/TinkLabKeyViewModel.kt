package com.nevidimka655.astracrypt.view.composables.lab.tink

import android.content.Context
import androidx.lifecycle.ViewModel
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.tink_lab.domain.TinkLabKeyManager
import com.nevidimka655.tink_lab.domain.model.DataItem
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.TinkLabKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TinkLabKeyViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val tinkLabKeyManager: TinkLabKeyManager
) : ViewModel() {
    val keyState: StateFlow<TinkLabKey> = tinkLabKeyManager.keyState

    suspend fun shuffleKeyset(
        keysetPassword: String,
        dataType: DataType,
        aeadType: String
    ) = withContext(defaultDispatcher) {
        tinkLabKeyManager.generate(
            keysetPassword = keysetPassword,
            dataType = dataType,
            aeadType = aeadType
        )
    }

    fun buildDataTypesList(context: Context) = listOf(
        DataItem(context.getString(R.string.files), DataType.Files),
        DataItem(context.getString(R.string.text), DataType.Text)
    )

}