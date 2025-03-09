package io.gromif.astracrypt.utils

import androidx.core.content.FileProvider
import io.gromif.astracrypt.resources.R

class AppFileProvider: FileProvider(R.xml.provider_paths)