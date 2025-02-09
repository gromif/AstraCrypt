package io.gromif.astracrypt.settings.privacy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PrivacyPolicyScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val vm: PrivacyPolicyViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        if (vm.html.isEmpty()) vm.load(context = context)
    }
    Screen(
        modifier = modifier,
        privacyPolicyHtml = vm.html
    )
}