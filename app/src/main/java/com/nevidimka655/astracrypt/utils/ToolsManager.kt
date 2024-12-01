package com.nevidimka655.astracrypt.utils

import com.nevidimka655.astracrypt.features.calculator.CalculatorManagerWrap
import com.nevidimka655.astracrypt.features.lab.LabCombineZipManager
import com.nevidimka655.astracrypt.features.lab.aead.LabAeadManager
import com.nevidimka655.astracrypt.features.notes.NotesManager
import kotlinx.coroutines.channels.Channel

class ToolsManager(
    private val snackbarChannel: Channel<Int>
) {
    private var _labManager: LabAeadManager? = null
    val labManager get() = _labManager ?: LabAeadManager(snackbarChannel).also { _labManager = it }

    private var _labCombineZipManager: LabCombineZipManager? = null
    val labCombineZipManager get() = _labCombineZipManager
        ?: LabCombineZipManager().also { _labCombineZipManager = it }

    private var _notesManager: NotesManager? = null
    val notesManager get() = _notesManager!!

    private var _calculatorManager: CalculatorManagerWrap? = null
    val calculatorManager get() = _calculatorManager ?: CalculatorManagerWrap().also { _calculatorManager = it }

}