package com.nevidimka655.astracrypt.utils.extensions

import java.io.File

fun File.recreate() = with(this) {
    delete()
    createNewFile()
}