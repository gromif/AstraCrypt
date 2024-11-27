package com.nevidimka655.astracrypt.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.model.ActionItem

object Actions {

    object Tabs {

        object Home {
            val toolbar = arrayOf(Items.search)
        }

    }

    private object Items {

        val back = ActionItem(
            contentDescription = R.string.back,
            icon = Icons.AutoMirrored.Default.ArrowBack
        )

        val search = ActionItem(
            contentDescription = android.R.string.search_go,
            icon = Icons.AutoMirrored.Default.ArrowBack
        )

    }

}