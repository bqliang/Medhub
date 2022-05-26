package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.addMember
import logic.deleteMember
import logic.editMember
import memberPageState
import model.MemberPageState
import model.Sex
import viewmodel.DashboardViewModel
import viewmodel.HandleMemberPageViewModel

private val viewModel = HandleMemberPageViewModel

@Composable
fun HandleMemberPage() = Column {
    MyTopBar()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (memberPageState == MemberPageState.Edit) {
                OutlinedTextField(
                    label = { Text("会员编号") },
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

            OutlinedTextField(
                label = { Text("手机号码") },
                value = viewModel.phone,
                leadingIcon = { Icon(painterResource("smartphone_white_24dp.svg"), null) },
                onValueChange = { viewModel.phone = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("注册日期") },
                value = viewModel.regdate.toString(),
                leadingIcon = { Icon(painterResource("calendar_today_white_24dp.svg"), null) },
                onValueChange = { },
                singleLine = true
            )
        }
        ExtendedFloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            text = { Text(text = if (memberPageState == MemberPageState.Add) "添加" else "保存修改") },
            icon = { Icon(painterResource("save_white_24dp.svg"), null) },
            onClick = {
                if (memberPageState == MemberPageState.Add) {
                    addMember()
                } else {
                    editMember()
                }
            }
        )
    }
}


@Composable
private fun MyTopBar() = TopAppBar(
    title = {
        Text(
            text = if (memberPageState == MemberPageState.Add) "添加新会员"
            else "编辑会员信息"
        )
    },
    navigationIcon = {
        IconButton(
            onClick = {
                memberPageState = MemberPageState.List
            },
            content = { Icon(painterResource("arrow_back_white_24dp.svg"), null) }
        )
    },
    actions = {
        if (memberPageState == MemberPageState.Edit) {
            IconButton(
                onClick = {
                    DashboardViewModel.openDialog(
                        title = "确定删除",
                        message = "是否确定删除用户${viewModel.name}（${viewModel.id}）？",
                        onConfirm = { deleteMember() }
                    )
                },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }
)