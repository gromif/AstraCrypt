package com.nevidimka655.astracrypt.app.utils

import com.nevidimka655.astracrypt.features.calculator.CalculatorManagerWrap
import com.nevidimka655.astracrypt.features.lab.LabCombineZipManager
import com.nevidimka655.astracrypt.features.lab.aead.LabAeadManager
import com.nevidimka655.astracrypt.features.notes.NotesManager

class ToolsManager {
    private var _labManager: LabAeadManager? = null
    val labManager get() = _labManager ?: LabAeadManager().also { _labManager = it }

    private var _labCombineZipManager: LabCombineZipManager? = null
    val labCombineZipManager get() = _labCombineZipManager
        ?: LabCombineZipManager().also { _labCombineZipManager = it }

    private var _notesManager: NotesManager? = null
    val notesManager get() = _notesManager!!

    private var _calculatorManager: CalculatorManagerWrap? = null
    val calculatorManager get() = _calculatorManager ?: CalculatorManagerWrap().also { _calculatorManager = it }

}