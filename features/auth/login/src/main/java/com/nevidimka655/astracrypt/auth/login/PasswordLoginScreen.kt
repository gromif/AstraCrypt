package com.nevidimka655.astracrypt.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordLoginScreen(
    modifier: Modifier = Modifier,
    onFabClick: Flow<Any>,
    onAuthenticated: () -> Unit
) {
    val vm: PasswordLoginViewModel = hiltViewModel()
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            if (vm.verifyPassword(password = password)) onAuthenticated()
        }
    }

    Screen(
        modifier = modifier,
        password = password,
        onPasswordChange = { password = it }
    )
}

@Preview(showBackground = true)
@Composable
private fun Screen(
    modifier: Modifier = Modifier,
    password: String = "password",
    onPasswordChange: (String) -> Unit = {},
    isHintVisible: Boolean = true,
    onShowHint: () -> Unit = {}
) = Box(
    modifier = modifier.padding(MaterialTheme.spaces.spaceMedium),
    contentAlignment = Alignment.Center
) {
    ElevatedCard {
        Column(
            modifier = Modifier.padding(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            TextField(value = password, onValueChange = onPasswordChange)
            if (isHintVisible) ElevatedButton(onClick = onShowHint) {
                Text(text = stringResource(id = R.string.hint))
            }
        }
    }
}