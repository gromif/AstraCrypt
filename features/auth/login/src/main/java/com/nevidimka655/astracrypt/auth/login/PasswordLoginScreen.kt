package com.nevidimka655.astracrypt.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.TextFields
import com.nevidimka655.ui.compose_core.text_fields.Password
import com.nevidimka655.ui.compose_core.text_fields.icons.PasswordToggleIconButton
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PasswordLoginScreen(
    modifier: Modifier = Modifier,
    auth: Auth,
    onFabClick: Flow<Any>,
    onAuthenticated: () -> Unit
) {
    val context = LocalContext.current
    val vm: PasswordLoginViewModel = hiltViewModel()
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onFabClick.collectLatest {
            if (vm.verifyPassword(password = password)) onAuthenticated()
            else Toast.makeText(context, R.string.t_invalidPass, Toast.LENGTH_SHORT).show()
        }
    }

    Screen(
        modifier = modifier,
        password = password,
        onPasswordChange = { password = it },
        isHintVisible = auth.hintState,
        onShowHint = { Toast.makeText(context, auth.hintText ?: "", Toast.LENGTH_SHORT).show() },
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
    Card {
        Column(
            modifier = Modifier.padding(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.Password,
                trailingIcon = {
                    TextFields.Icons.PasswordToggleIconButton(passwordVisible) {
                        passwordVisible = it
                    }
                },
                placeholder = TextFields.placeholder(text = stringResource(R.string.password)),
                label = TextFields.label(text = stringResource(R.string.password)),
                visualTransformation = TextFields.passwordVisualTransform(state = passwordVisible)
            )
            if (isHintVisible) OutlinedButton(onClick = onShowHint) {
                Text(text = stringResource(id = R.string.hint))
            }
        }
    }
}