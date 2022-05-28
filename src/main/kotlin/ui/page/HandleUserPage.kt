package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.addUser
import logic.deleteUser
import logic.editUser
import model.Position
import model.Sex
import model.UserPageState
import userPageState
import viewmodel.DashboardViewModel
import viewmodel.HandleUserPageViewModel

private val viewModel = HandleUserPageViewModel

@Composable
fun HandleUsererPage() = Column {
    MyTopBar()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (userPageState == UserPageState.Edit) {
                OutlinedTextField(
                    label = { Text("员工编号") },
                    value = viewModel.id.toString(),
                    leadingIcon = { Icon(painterResource("fingerprint_white_24dp.svg"), null) },
                    onValueChange = { },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))
            }

            OutlinedTextField(
                label = { Text("姓名") },
                value = viewModel.name,
                leadingIcon = { Icon(painterResource("face_white_24dp.svg"), null) },
                onValueChange = { viewModel.name = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box {
                OutlinedTextField(
                    label = { Text("性别") },
                    value = viewModel.sex,
                    leadingIcon = {
                        Icon(
                            painterResource(if (viewModel.sex == "男") "male_white_24dp.svg" else "female_white_24dp.svg"),
                            null
                        )
                    },
                    readOnly = true,
                    onValueChange = { },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.sexMenuExpanded = true },
                            content = {
                                Icon(
                                    painter = painterResource(if (viewModel.sexMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                )

                CursorDropdownMenu(
                    expanded = viewModel.sexMenuExpanded,
                    onDismissRequest = { viewModel.sexMenuExpanded = false },
                ) {
                    Sex.values().forEach { sex ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.sex = sex.str
                                viewModel.sexMenuExpanded = false
                            },
                            content = { Text(sex.str) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box {
                OutlinedTextField(
                    label = { Text("职位") },
                    value = viewModel.type,
                    leadingIcon = {
                        Icon(
                            painterResource(if (viewModel.sex == "经理") "male_white_24dp.svg" else "female_white_24dp.svg"),
                            null
                        )
                    },
                    readOnly = true,
                    onValueChange = { },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = { viewModel.positionMenuExpanded = true },
                            content = {
                                Icon(
                                    painter = painterResource(if (viewModel.positionMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                )

                CursorDropdownMenu(
                    expanded = viewModel.positionMenuExpanded,
                    onDismissRequest = { viewModel.positionMenuExpanded = false },
                ) {
                    Position.values().forEach { position ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.type = position.str
                                viewModel.positionMenuExpanded = false
                            },
                            content = { Text(position.str) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("手机号码") },
                value = viewModel.phone,
                leadingIcon = { Icon(painterResource("smartphone_white_24dp.svg"), null) },
                onValueChange = { viewModel.phone = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("身份证号码") },
                value = viewModel.idNum,
                leadingIcon = { Icon(painterResource("gbadge_FILL0_wght400_GRAD0_opsz24.svg"), null) },
                onValueChange = { viewModel.idNum = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("入职日期") },
                value = viewModel.entry.toString(),
                leadingIcon = { Icon(painterResource("calendar_today_white_24dp.svg"), null) },
                onValueChange = { },
                singleLine = true
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            text = { Text(text = if (userPageState == UserPageState.Add) "添加" else "保存修改") },
            icon = { Icon(painterResource("save_white_24dp.svg"), null) },
            onClick = {
                if (userPageState == UserPageState.Add) addUser() else editUser()
            }
        )
    }
}


@Composable
private fun MyTopBar() = TopAppBar(
    title = {
        Text(
            text = if (userPageState == UserPageState.Add) "添加新员工"
            else "编辑员工信息"
        )
    },
    navigationIcon = {
        IconButton(
            onClick = {
                userPageState = UserPageState.List
            },
            content = { Icon(painterResource("arrow_back_white_24dp.svg"), null) }
        )
    },
    actions = {
        if (userPageState == UserPageState.Edit) {
            IconButton(
                onClick = {
                    DashboardViewModel.openDialog(
                        title = "确定删除",
                        message = "是否确定删除员工${viewModel.name}（${viewModel.id}）？",
                        onConfirm = { deleteUser() }
                    )
                },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }
)