package viewmodel

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import preferences


object LoginPageViewModel {

    /**
     * 账户输入框的值
     */
    var account: String by mutableStateOf(preferences.get("account", ""))

    /**
     * 密码输入框的值
     */
    var password: String by mutableStateOf(preferences.get("password", ""))

    /**
     * 是否隐藏密码
     */
    var passwordHidden by mutableStateOf(true)

    /**
     * 是否显示对话框
     */
    var showDialog by mutableStateOf(false)

    /**
     * 对话框标题
     */
    var dialogTitle by mutableStateOf("")

    /**
     * 对话框文字
     */
    var dialogText by mutableStateOf("")

    /**
     * 显示登录页面的对话框
     *
     * @param title 对话框标题
     * @param msg 对话框提示内容
     */
    fun openDialog(title: String, msg: String) {
        dialogTitle = title
        dialogText = msg
        showDialog = true
    }

    val snackbarHostState by mutableStateOf(SnackbarHostState())
}
