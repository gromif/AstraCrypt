package io.gromif.astracrypt.utils.app.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openExternally(uri: Uri) = startActivity(Intent(Intent.ACTION_VIEW, uri))