package ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.*
import memberPageState
import model.MemberPageState
import model.Sex
import model.SupplierPageState
import supplierPageState
import viewmodel.DashboardViewModel
import viewmodel.HandleMemberPageViewModel
import viewmodel.HandleSupplierPageViewModel

private val viewModel = HandleSupplierPageViewModel

@Composable
fun HandleSupplierPage() = Column {
    MyTopBar()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (supplierPageState == SupplierPageState.Edit) {
                OutlinedTextField(
                    label = { Text("供应商编号") },
                    value = viewModel.id.toString(),
                    leadingIcon = { Icon(painterResource("fingerprint_white_24dp.svg"), null) },
                    onValueChange = { },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            OutlinedTextField(
                label = { Text("名称") },
                value = viewModel.name,
                leadingIcon = { Icon(painterResource("face_white_24dp.svg"), null) },
                onValueChange = { viewModel.name = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("地址") },
                value = viewModel.address,
                leadingIcon = {
                    Icon(painterResource("place_white_24dp.svg"), null)
                },
                onValueChange = { viewModel.address = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("联系电话") },
                value = viewModel.phone,
                leadingIcon = { Icon(painterResource("smartphone_white_24dp.svg"), null) },
                onValueChange = { viewModel.phone = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                label = { Text("电子邮箱") },
                value = viewModel.email,
                leadingIcon = { Icon(painterResource("email_white_24dp.svg"), null) },
                onValueChange = { viewModel.email = it },
                singleLine = true
            )
        }

        ExtendedFloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            text = { Text(text = if (supplierPageState == SupplierPageState.Add) "添加" else "保存修改") },
            icon = { Icon(painterResource("save_white_24dp.svg"), null) },
            onClick = {
                if (supplierPageState == SupplierPageState.Add) {
                    addSupplier()
                } else {
                    editSupplier()
                }
            }
        )
    }
}


@Composable
private fun MyTopBar() = TopAppBar(
    title = {
        Text(
            text = if (supplierPageState == SupplierPageState.Add) "添加新供应商"
            else "编辑供应商信息"
        )
    },
    navigationIcon = {
        IconButton(
            onClick = {
                supplierPageState = SupplierPageState.List
            },
            content = { Icon(painterResource("arrow_back_white_24dp.svg"), null) }
        )
    },
    actions = {
        if (supplierPageState == SupplierPageState.Edit) {
            IconButton(
                onClick = {
                    DashboardViewModel.openDialog(
                        title = "确定删除",
                        message = "是否确定删除供应商${viewModel.name}（${viewModel.id}）？",
                        onConfirm = { deleteSupplier() }
                    )
                },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }
)