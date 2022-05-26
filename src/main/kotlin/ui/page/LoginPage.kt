package ui.page

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isDark
import logic.login
import viewmodel.LoginPageViewModel


private val viewModel = LoginPageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview()
fun LoginPage() = Column(modifier = Modifier.fillMaxSize()) {
    MyAppBar()
    Box {
        // 背景图
        Image(
            painter = painterResource("DNA-background.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.15f,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource("medication_white_48dp.svg"),
                contentDescription = null,
                modifier = Modifier.size(86.dp),
                tint = MaterialTheme.colors.primary
            )

            InfoInput()

            MyButtons()
        }

        // 版权脚注
        Text(
            modifier = Modifier.align(Alignment.BottomCenter).padding(0.dp, 10.dp),
            fontWeight = FontWeight.Light,
            fontSize = 13.sp,
            text = "develop by bqliang",
        )

        SnackbarHost(
            hostState = viewModel.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // 对话框
        if (viewModel.showDialog) {
            LoginPageDialog()
        }
    }
}

/**
 * 顶栏
 *
 */
@Composable
private fun MyAppBar() = TopAppBar(
    title = {
        IconButton(
            onClick = { },
            content = {
                Row {
                    Icon(painterResource("medication_white_24dp.svg"), null)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("MedHub")
                }
            }
        )
    },
    actions = {
        IconButton(
            content = {
                Icon(
                    painter = painterResource("question_answer_white_24dp.svg"),
                    contentDescription = null
                )
            },
            onClick = {
                // TODO 登录页面 - 顶栏 - 帮助图标 - 点击事件
            }
        )
        IconButton(
            onClick = { isDark = !isDark },
            content = {
                Icon(
                    painter = painterResource(if (isDark) "light_mode_white_24dp.svg" else "dark_mode_white_24dp.svg"),
                    contentDescription = null
                )
            }
        )
    }
)


/**
 * 账号密码输入
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InfoInput() = Column {
    // 账户输入框
    OutlinedTextField(
        value = viewModel.account,
        onValueChange = { viewModel.account = it },
        singleLine = true,
        label = { Text(text = "账号") },
        leadingIcon = { Icon(painterResource("account_circle_white_24dp.svg"), null) }
    )

    Spacer(modifier = Modifier.height(10.dp))

    // 密码输入框
    OutlinedTextField(
        modifier = Modifier.onPreviewKeyEvent { keyEvent ->
            if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyDown) {
                login()
                true
            } else false
        },
        value = viewModel.password,
        onValueChange = { viewModel.password = it },
        singleLine = true,
        label = { Text(text = "密码") },
        leadingIcon = { Icon(painterResource("key_white_24dp.svg"), null) },
        trailingIcon = {
            IconButton(
                onClick = { viewModel.passwordHidden = !viewModel.passwordHidden },
                content = {
                    Icon(
                        painter = painterResource(if (viewModel.passwordHidden) "visibility_white_24dp.svg" else "visibility_off_white_24dp.svg"),
                        contentDescription = null
                    )
                }
            )
        },
        visualTransformation = if (viewModel.passwordHidden) PasswordVisualTransformation() else VisualTransformation.None
    )
}


/**
 * 登录和找回密码按钮
 *
 */
@Composable
private fun MyButtons() = Row(
    horizontalArrangement = Arrangement.Center,
    modifier = Modifier.padding(10.dp, 15.dp)
) {
    Button(
        onClick = { login() }
    ) {
        Icon(painterResource("login_white_24dp.svg"), null)
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = "登录",
            fontSize = 15.sp
        )
    }

    Spacer(modifier = Modifier.width(16.dp))

    OutlinedButton(
        onClick = {
            // TODO 登录页面 - 找回密码
        }
    ) {
        Icon(painterResource("help_outline_white_24dp.svg"), null)
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            text = "忘记密码",
            fontSize = 15.sp
        )
    }
}


/**
 * 登录页面对话框
 *
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LoginPageDialog() = AlertDialog(
    modifier = Modifier.fillMaxWidth(0.7f),
    onDismissRequest = { viewModel.showDialog = false },
    title = {
        Text(
            text = viewModel.dialogTitle,
            fontWeight = FontWeight.W700,
            style = MaterialTheme.typography.h6
        )
    },
    text = {
        Text(
            text = viewModel.dialogText,
            fontSize = 16.sp
        )
    },
    confirmButton = {
        TextButton(
            onClick = { viewModel.showDialog = false },
            content = {
                Text(
                    "确认",
                    fontWeight = FontWeight.W700,
                    style = MaterialTheme.typography.button
                )
            }
        )
    }
)