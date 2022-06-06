package logic

import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loginSuccessful
import loginUserId
import model.database.users
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import preferences
import scope
import utils.md5
import viewmodel.LoginPageViewModel

private val viewModel = LoginPageViewModel

/**
 * 登录执行的动作
 *
 */
fun login() = scope.launch(Dispatchers.IO) {

    val account = viewModel.account
    val password = viewModel.password

    val user = db.users.find {
        (it.phone eq account) and (it.password eq password.md5)
    }

    if (user == null) {
        viewModel.snackbarHostState
            .showSnackbar("登录失败，请仔细检查您输入的账户和密码再重试")
    } else {

        loginSuccessful = true
        loginUserId = user.id

        preferences.apply {
            put("account", account)
            put("password", password)
            //putBoolean("login_successful", true)
        }
    }
}