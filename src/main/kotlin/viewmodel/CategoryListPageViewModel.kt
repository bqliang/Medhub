package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.database.Category
import model.database.SubCategory

object CategoryListPageViewModel {

    val categories: SnapshotStateList<Category> = SnapshotStateList()

    val subCategories: SnapshotStateList<SubCategory> = SnapshotStateList()

    var curCategoryId by mutableStateOf(-1)
    var curSubCategoryId by mutableStateOf(-1)

    var curCategoryName by mutableStateOf("")
    var curSubCategoryName by mutableStateOf("")
}