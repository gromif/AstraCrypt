package com.nevidimka655.astracrypt.utils.io

import java.io.File

class WorkerSerializer {

    fun saveStringListToFile(arr: List<String>): File {
        val file = File.createTempFile("wkr_str_l", null)
        val text = arr.joinToString(separator = "\n").trimEnd()
        file.writeText(text)
        return file
    }

}