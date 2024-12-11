package com.nevidimka655.astracrypt.app.extensions

import java.io.File

fun File.recreate() = with(this) {
    delete()
    createNewFile()
}