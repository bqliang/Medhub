package ui.page

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import isDark
import kotlinx.coroutines.launch
import loginSuccessful
import medicinePageState
import memberPageState
import model.*
import preferences
import scope
import supplierPageState
import userPageState
import viewmodel.DashboardViewModel
import viewmodel.LoginPageViewModel


private val viewModel = DashboardViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Dashboard() = Row {

    MyNavigationRail()

    Box(modifier = Modifier.fillMaxSize()) {

        // 淡出淡入效果
        Crossfade(
            targetState = viewModel.selectRailItem,
            animationSpec = tween(1000)
        ) { selectRailItem ->
            when (selectRailItem) {
                RailBarItem.MEDICINE -> MedicinePage()
                RailBarItem.FRE -> FreListPage()
                RailBarItem.MEMBER -> MemberPage()
                RailBarItem.USER -> UserPage()
                RailBarItem.SUPPLIER -> SupplierPage()
                RailBarItem.CATEGORY -> CategoryListPage()
            }
        }

        // Snackbar
        SnackbarHost(
            hostState = viewModel.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Show snackbar message whenever login successful
        if (loginSuccessful) {
            LaunchedEffect(DashboardViewModel.snackbarHostState) {
                DashboardViewModel.snackbarHostState.showSnackbar("欢迎登录 MedHub!")
            }
        }

        if (viewModel.showDialog) {
            AlertDialog(
                modifier = Modifier.fillMaxWidth(0.6f),
                onDismissRequest = { viewModel.showDialog = false },
                title = { Text(viewModel.dialogTitle) },
                text = { Text(viewModel.dialogMessage) },
                confirmButton = {
                    TextButton(
                        content = { Text(("确定")) },
                        onClick = {
                            viewModel.showDialog = false
                            viewModel.dialogOnConfirm()
                            viewModel.dialogOnConfirm = { }
                        }
                    )
                },
                dismissButton = {
                    TextButton(
                        content = { Text(("取消")) },
                        onClick = { viewModel.showDialog = false }
                    )
                }
            )
        }

    }
}


@Composable
fun MyNavigationRail() = NavigationRail(modifier = Modifier.fillMaxHeight()) {
    RailBarItem.values().forEach { railBarItem ->
        NavigationRailItem(
            icon = { Icon(painterResource(railBarItem.iconPath), null) },
            label = { Text(railBarItem.string) },
            selected = (viewModel.selectRailItem == railBarItem),
            onClick = { viewModel.selectRailItem = railBarItem },
            alwaysShowLabel = false
        )
    }

    NavigationRailItem(
        icon = {
            Icon(
                painter = painterResource(if (isDark) "light_mode_white_24dp.svg" else "dark_mode_white_24dp.svg"),
                contentDescription = null
            )
        },
        label = { Text(if (isDark) "亮色主题" else "暗色主题") },
        onClick = {
            isDark = !isDark
            preferences.putBoolean("dark_theme", isDark)
        },
        selected = false,
        alwaysShowLabel = false
    )
    NavigationRailItem(
        icon = { Icon(painterResource("logout_white_24dp.svg"), contentDescription = null) },
        label = { Text("退出登录") },
        onClick = {
            loginSuccessful = false
            scope.launch {
                preferences.putBoolean("login_successful", false)
                //viewModel.selectRailItem = RailBarItem.MEDICINE
                LoginPageViewModel.snackbarHostState
                    .showSnackbar("期待您再次使用 Medhub，祝您生活愉快！")
            }
        },
        selected = false,
        alwaysShowLabel = false
    )
}


@Composable
private fun MemberPage() = Crossfade(memberPageState) { state ->
    when (state) {
        MemberPageState.List -> MemberListPage()
        else -> HandleMemberPage()
    }
}


@Composable
private fun MedicinePage() = Crossfade(medicinePageState) { state ->
    when (state) {
        MedicinePageState.List -> MedicineListPage()
        else -> HandleMedicinePage()
    }
}


@Composable
private fun SupplierPage() = Crossfade(supplierPageState) { state ->
    when (state) {
        SupplierPageState.List -> SupplierListPage()
        else -> HandleSupplierPage()
    }
}


@Composable
private fun UserPage() = Crossfade(userPageState) { state ->
    when (state) {
        UserPageState.List -> UserListPage()
        else -> HandleUsererPage()
    }
}