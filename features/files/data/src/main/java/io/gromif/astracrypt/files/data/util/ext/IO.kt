package io.gromif.astracrypt.files.data.util.ext

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import java.io.InputStream
import java.io.OutputStream

internal suspend fun InputStream.copyToSuspend(
    output: OutputStream
) = coroutineScope {
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var loadedSize = read(buffer)
    while (isActive && loadedSize != -1) {
        output.write(buffer, 0, loadedSize)
        loadedSize = read(buffer)
    }
    output.close()
}
