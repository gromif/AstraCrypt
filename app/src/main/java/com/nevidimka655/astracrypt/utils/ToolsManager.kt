package com.nevidimka655.astracrypt.utils

import com.nevidimka655.astracrypt.features.calculator.CalculatorManagerWrap
import com.nevidimka655.astracrypt.features.notes.NotesManager
import com.nevidimka655.astracrypt.lab.LabCombineZipManager
import com.nevidimka655.astracrypt.lab.aead.LabAeadManager
import com.nevidimka655.compose_details.DetailsManager
import kotlinx.coroutines.channels.Channel

class ToolsManager(
    private val snackbarChannel: Channel<Int>
) {
    private var _labManager: LabAeadManager? = null
    val labManager get() = _labManager ?: LabAeadManager(snackbarChannel).also { _labManager = it }

    private var _labCombineZipManager: LabCombineZipManager? = null
    val labCombineZipManager get() = _labCombineZipManager
        ?: LabCombineZipManager().also { _labCombineZipManager = it }

    private var _detailsManager: DetailsManager? = null
    val detailsManager get() = _detailsManager ?: DetailsManager().also { _detailsManager = it }

    private var _notesManager: NotesManager? = null
    val notesManager get() = _notesManager ?: NotesManager().also { _notesManager = it }

    private var _calculatorManager: CalculatorManagerWrap? = null
    val calculatorManager get() = _calculatorManager ?: CalculatorManagerWrap().also { _calculatorManager = it }

}