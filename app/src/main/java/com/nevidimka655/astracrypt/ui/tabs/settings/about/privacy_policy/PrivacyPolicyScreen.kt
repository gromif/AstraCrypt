package com.nevidimka655.astracrypt.ui.tabs.settings.about.privacy_policy

import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier, privacyPolicyHtml: String
) = Column(modifier = modifier) {
    if (privacyPolicyHtml.isNotEmpty()) AndroidView(
        factory = { context -> WebView(context) },
        update = { webView -> webView.loadData(privacyPolicyHtml, "text/html", "utf-8") }
    )
}