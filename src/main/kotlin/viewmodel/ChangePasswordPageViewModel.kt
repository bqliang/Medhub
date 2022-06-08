package viewmodel

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ChangePasswordPageViewModel {
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")

    var oldPasswordHidden by mutableStateOf(true)
    var newPasswordHidden by mutableStateOf(true)
    var passwordConfirmHidden by mutableStateOf(true)

    val snackbarHostState by mutableStateOf(SnackbarHostState())

}