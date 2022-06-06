package ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import logic.*
import viewmodel.CategoryListPageViewModel

private val viewModel = CategoryListPageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryListPage() = Row(modifier = Modifier.fillMaxSize()) {

    /**
     * 左侧（父分类）
     */
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .weight(0.5F)
    ) {
        Text(
            text = "父分类",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .weight(1f)
                .fillMaxWidth(),
            elevation = 4.dp,
            content = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .selectableGroup()
                ) {
                    items(viewModel.categories) { category ->
                        ListItem(
                            modifier = Modifier.clickable {
                                if (viewModel.curCategoryId != category.id) {
                                    viewModel.apply {
                                        curCategoryId = category.id
                                        curCategoryName = category.name
                                        curSubCategoryId = -1
                                        curSubCategoryName = ""
                                    }
                                    getSubCategoriesByCid(category.id)
                                }
                            },
                            icon = {
                                RadioButton(selected = (viewModel.curCategoryId == category.id), onClick = null)
                            },
                            text = { Text(category.name) },
                            trailing = { Text(category.id.toString()) }
                        )
                    }
                }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = viewModel.curCategoryName,
                onValueChange = { viewModel.curCategoryName = it }
            )
            IconButton(
                onClick = { addCategory() },
                content = { Icon(painterResource("add_white_24dp.svg"), null) }
            )
            IconButton(
                onClick = { editCategory() },
                content = { Icon(painterResource("edit_white_24dp.svg"), null) }
            )
            IconButton(
                onClick = { deleteCategory() },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }


    /**
     * 右侧（子分类）
     */
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .weight(0.5F)
    ) {
        Text(
            text = "子分类",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .weight(1f)
                .fillMaxWidth(),
            elevation = 4.dp,
            content = {
                if (viewModel.curCategoryId == -1) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "请在左侧选择一个父分类"
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .selectableGroup()
                    ) {
                        items(viewModel.subCategories) { subCategory ->
                            ListItem(
                                modifier = Modifier.clickable {
                                    viewModel.curSubCategoryId = subCategory.id
                                    viewModel.curSubCategoryName = subCategory.name
                                },
                                icon = {
                                    RadioButton(
                                        selected = (viewModel.curSubCategoryId == subCategory.id),
                                        onClick = null
                                    )
                                },
                                text = { Text(subCategory.name) },
                                trailing = { Text(subCategory.id.toString()) }
                            )
                        }
                    }
                }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = viewModel.curSubCategoryName,
                onValueChange = { viewModel.curSubCategoryName = it }
            )
            IconButton(
                onClick = { addSubCategory() },
                content = { Icon(painterResource("add_white_24dp.svg"), null) }
            )
            IconButton(
                onClick = { },
                content = { Icon(painterResource("edit_white_24dp.svg"), null) }
            )
            IconButton(
                onClick = { deleteSubCategory() },
                content = { Icon(painterResource("delete_white_24dp.svg"), null) }
            )
        }
    }
}