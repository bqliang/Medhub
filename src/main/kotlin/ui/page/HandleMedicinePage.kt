package ui.page

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import logic.addMedicine
import logic.deleteMedicine
import logic.editMedicine
import logic.searchSubCategory
import medicinePageState
import model.Medicare
import model.MedicinePageState
import viewmodel.DashboardViewModel
import viewmodel.HandleMedicinePageViewModel

private val viewModel = HandleMedicinePageViewModel

@Composable
fun HandleMedicinePage() = Column(modifier = Modifier.fillMaxSize()) {
    MyTopBar()

    Box(modifier = Modifier.fillMaxSize()) {
        val stateVertical = rememberScrollState(0)

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .verticalScroll(stateVertical)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            /**
             * 药品编号和批准文号
             */
            Row {
                if (medicinePageState == MedicinePageState.Edit) {
                    OutlinedTextField(
                        label = { Text("药品编号") },
                        value = viewModel.id.toString(),
                        leadingIcon = { Icon(painterResource("fingerprint_white_24dp.svg"), null) },
                        onValueChange = { },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                OutlinedTextField(
                    label = { Text("批准文号") },
                    value = viewModel.code,
                    leadingIcon = { Icon(painterResource("verified_white_24dp.svg"), null) },
                    onValueChange = { viewModel.code = it },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * 药品名称与医保类型
             */
            Row {
                OutlinedTextField(
                    label = { Text("药品名称") },
                    value = viewModel.name,
                    leadingIcon = { Icon(painterResource("sell_white_24dp.svg"), null) },
                    onValueChange = { viewModel.name = it },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(16.dp))

                /**
                 * 医保类型
                 */
                Box {
                    OutlinedTextField(
                        label = { Text("医保类型") },
                        value = viewModel.medicare.str,
                        leadingIcon = { Icon(painterResource("category_white_24dp.svg"), null) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.medicareMenuExpanded = true }
                            ) {
                                Icon(
                                    painter = painterResource(if (viewModel.medicareMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                                    contentDescription = null
                                )
                            }
                        },
                        onValueChange = { },
                        singleLine = true
                    )

                    CursorDropdownMenu(
                        expanded = viewModel.medicareMenuExpanded,
                        onDismissRequest = { viewModel.medicareMenuExpanded = false },
                    ) {
                        Medicare.values().forEach { medicare ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.medicare = medicare
                                    viewModel.medicareMenuExpanded = false
                                },
                                content = { Text(medicare.str) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * 分类和子分类
             */
            Row {
                Box {
                    OutlinedTextField(
                        label = { Text("所属分类") },
                        value = viewModel.catrgory?.name ?: "",
                        leadingIcon = { Icon(painterResource("bookmark_white_24dp.svg"), null) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.categoryMenuExpanded = true }
                            ) {
                                Icon(
                                    painter = painterResource(if (viewModel.categoryMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                                    contentDescription = null
                                )
                            }
                        },
                        onValueChange = { },
                        singleLine = true
                    )

                    CursorDropdownMenu(
                        expanded = viewModel.categoryMenuExpanded,
                        onDismissRequest = { viewModel.categoryMenuExpanded = false },
                    ) {
                        viewModel.categories.forEach { category ->
                            DropdownMenuItem(
                                onClick = {
                                    searchSubCategory()
                                    viewModel.apply {
                                        categoryMenuExpanded = false
                                        catrgory = category
                                        subCatrgory = null
                                    }
                                },
                                content = { Text(category.name) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box {
                    OutlinedTextField(
                        label = { Text("子分类") },
                        value = viewModel.subCatrgory?.name ?: "",
                        leadingIcon = { Icon(painterResource("bookmarks_white_24dp.svg"), null) },
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.subCategoryeMenuExpanded = true }
                            ) {
                                Icon(
                                    painter = painterResource(if (viewModel.subCategoryeMenuExpanded) "expand_less_white_24dp.svg" else "expand_more_white_24dp.svg"),
                                    contentDescription = null
                                )
                            }
                        },
                        onValueChange = { },
                        singleLine = true
                    )

                    CursorDropdownMenu(
                        expanded = viewModel.subCategoryeMenuExpanded,
                        onDismissRequest = { viewModel.subCategoryeMenuExpanded = false },
                    ) {
                        viewModel.subCategories.forEach { subCategory ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.subCatrgory = subCategory
                                    viewModel.subCategoryeMenuExpanded = false
                                },
                                content = { Text(subCategory.name) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * 采购单价和销售单价
             */
            Row {
                OutlinedTextField(
                    label = { Text("采购单价") },
                    value = viewModel.purchasePrice,
                    leadingIcon = { Icon(painterResource("local_shipping_white_24dp.svg"), null) },
                    onValueChange = { viewModel.purchasePrice = it },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    label = { Text("销售单价") },
                    value = viewModel.price,
                    leadingIcon = { Icon(painterResource("currency_yen_white_24dp.svg"), null) },
                    onValueChange = { viewModel.price = it },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            /**
             * 库存量和库存下限
             */
            Row {
                OutlinedTextField(
                    label = { Text("库存数量") },
                    value = viewModel.inventory,
                    leadingIcon = { Icon(painterResource("inventory_white_24dp.svg"), null) },
                    onValueChange = { viewModel.inventory = it },
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    label = { Text("库存下限") },
                    value = viewModel.limit,
                    leadingIcon = { Icon(painterResource("inventory_white_24dp.svg"), null) },
                    onValueChange = { viewModel.limit = it },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("规格") },
                value = viewModel.specification,
                leadingIcon = { Icon(painterResource("face_white_24dp.svg"), null) },
                onValueChange = { viewModel.specification = it },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("包装") },
                value = viewModel.packing,
                leadingIcon = { Icon(painterResource("face_white_24dp.svg"), null) },
                onValueChange = { viewModel.packing = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("生产厂家") },
                value = viewModel.manufacturer,
                leadingIcon = { Icon(painterResource("manufacturing_white_24dp.svg"), null) },
                onValueChange = { viewModel.manufacturer = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                label = { Text("生产地址") },
                value = viewModel.address,
                leadingIcon = { Icon(painterResource("place_white_24dp.svg"), null) },
                onValueChange = { viewModel.address = it }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        /**
         * 滚动条
         */
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )

        ExtendedFloatingActionButton(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            text = { Text(text = if (medicinePageState == MedicinePageState.Add) "保存" else "保存修改") },
            icon = { Icon(painterResource("save_white_24dp.svg"), null) },
            onClick = { if (medicinePageState == MedicinePageState.Add) addMedicine() else editMedicine() }
        )
    }
}


@Composable
private fun MyTopBar() = TopAppBar(
    title = {
        Text(text = if (medicinePageState == MedicinePageState.Add) "添加新药品" else "编辑药品信息")
    },
    navigationIcon = {
        IconButton(
            onClick = {
                medicinePageState = MedicinePageState.List
            },
            content = { Icon(painterResource("arrow_back_white_24dp.svg"), null) }
        )
    },
    actions = {
        if (medicinePageState == MedicinePageState.Edit) {
            IconButton(
                onClick = {
                    DashboardViewModel.openDialog(
                        title = "确定删除",
                        message = "是否确定删除药品${viewModel.name}（${viewModel.id}）？",
                        onConfirm = { deleteMedicine() }
                    )
                },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }
)