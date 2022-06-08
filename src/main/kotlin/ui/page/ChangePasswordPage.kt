package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import changePasswordPageVisible
import kotlinx.coroutines.launch
import logic.editPassword
import scope
import viewmodel.ChangePasswordPageViewModel


private val viewModel = ChangePasswordPageViewModel

@Composable
fun ChangePasswordPage() = Dialog(
    onCloseRequest = { changePasswordPageVisible = false },
    state = rememberDialogState(size = DpSize(400.dp, 350.dp)),
    resizable = false,
    title = "密码修改"
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(top = 16.dp).align(Alignment.TopCenter)) {
            // 原密码
            OutlinedTextField(
                value = viewModel.oldPassword,
                onValueChange = { viewModel.oldPassword = it },
                singleLine = true,
                label = { Text(text = "原密码") },
                leadingIcon = { Icon(painterResource("key_white_24dp.svg"), null) },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.oldPasswordHidden = !viewModel.oldPasswordHidden },
                        content = {
                            Icon(
                                painter = painterResource(if (viewModel.oldPasswordHidden) "visibility_white_24dp.svg" else "visibility_off_white_24dp.svg"),
                                contentDescription = null
                            )
                        }
                    )
                },
                visualTransformation = if (viewModel.oldPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 新密码
            OutlinedTextField(
                value = viewModel.newPassword,
                onValueChange = { viewModel.newPassword = it },
                singleLine = true,
                label = { Text(text = "新密码") },
                leadingIcon = { Icon(painterResource("key_white_24dp.svg"), null) },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.newPasswordHidden = !viewModel.newPasswordHidden },
                        content = {
                            Icon(
                                painter = painterResource(if (viewModel.newPasswordHidden) "visibility_white_24dp.svg" else "visibility_off_white_24dp.svg"),
                                contentDescription = null
                            )
                        }
                    )
                },
                visualTransformation = if (viewModel.newPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 确认密码
            OutlinedTextField(
                value = viewModel.passwordConfirm,
                onValueChange = { viewModel.passwordConfirm = it },
                singleLine = true,
                label = { Text(text = "确认密码") },
                leadingIcon = { Icon(painterResource("key_white_24dp.svg"), null) },
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.passwordConfirmHidden = !viewModel.passwordConfirmHidden },
                        content = {
                            Icon(
                                painter = painterResource(if (viewModel.passwordConfirmHidden) "visibility_white_24dp.svg" else "visibility_off_white_24dp.svg"),
                                contentDescription = null
                            )
                        }
                    )
                },
                visualTransformation = if (viewModel.passwordConfirmHidden) PasswordVisualTransformation() else VisualTransformation.None
            )
        }

        FloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            onClick = {
                if (viewModel.newPassword != viewModel.passwordConfirm) {
                    scope.launch {
                        viewModel.snackbarHostState.showSnackbar("密码不一致")
                    }
                } else {
                    editPassword()
                }
            }
        ) {
            Icon(
                painter = painterResource("done_FILL0_wght500_GRAD0_opsz24.svg"),
                contentDescription = null
            )
        }

        // Snackbar
        SnackbarHost(
            hostState = viewModel.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}