package io.gromif.astracrypt.auth.presentation

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.TextFields
import io.gromif.ui.compose.core.ext.FlowObserver
import io.gromif.ui.compose.core.text_fields.Password
import io.gromif.ui.compose.core.text_fields.icons.PasswordToggleIconButton
import io.gromif.ui.compose.core.theme.spaces
import kotlinx.coroutines.flow.Flow

@Composable
fun PasswordLoginScreen(
    modifier: Modifier = Modifier,
    onFabClick: Flow<Any>,
    onAuthenticated: () -> Unit
) {
    val context = LocalContext.current
    val vm: PasswordLoginViewModel = hiltViewModel()
    val auth by vm.authState.collectAsStateWithLifecycle(Auth())
    var password by rememberSaveable { mutableStateOf("") }

    FlowObserver(onFabClick) {
        if (vm.verifyPassword(password = password)) {
            if (auth.bindTinkAd) vm.decryptTinkAd(password = password)
            onAuthenticated()
        } else Toast.makeText(context, R.string.t_invalidPass, Toast.LENGTH_SHORT).show()
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