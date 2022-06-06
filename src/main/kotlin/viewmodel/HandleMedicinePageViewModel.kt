package viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import logic.searchCategory
import logic.searchSubCategory
import model.Medicare
import model.database.Category
import model.database.Medicine
import model.database.SubCategory

object HandleMedicinePageViewModel {

    var id by mutableStateOf(-1)
    var name by mutableStateOf("")
    var code by mutableStateOf("")
    var catrgory: Category? by mutableStateOf(null)
    var subCatrgory: SubCategory? by mutableStateOf(null)
    var purchasePrice by mutableStateOf("")
    var price by mutableStateOf("")
    var inventory by mutableStateOf("")
    var limit by mutableStateOf("")
    var specification by mutableStateOf("")
    var packing by mutableStateOf("")
    var medicare by mutableStateOf(Medicare.NONE)
    var manufacturer by mutableStateOf("")
    var address by mutableStateOf("")

    var medicareMenuExpanded by mutableStateOf(false)
    var categoryMenuExpanded by mutableStateOf(false)
    var subCategoryeMenuExpanded by mutableStateOf(false)

    val categories = SnapshotStateList<Category>()
    val subCategories = SnapshotStateList<SubCategory>()

    fun setData(medicine: Medicine? = null){
        if (medicine == null){
            name = ""
            code = ""
            catrgory = null
            subCatrgory = null
            purchasePrice = ""
            price = ""
            inventory = "0"
            limit = "0"
            specification = ""
            packing = ""
            medicare = Medicare.NONE
            manufacturer = ""
            address = ""
            subCategories.clear()
        }else {
            id = medicine.id
            name = medicine.name
            code = medicine.code
            catrgory = medicine.category.category
            subCatrgory = medicine.category
            searchSubCategory()
            purchasePrice = medicine.purchasePrice.toString()
            price = medicine.price.toString()
            inventory = medicine.inventory.toString()
            limit = medicine.limit.toString()
            specification = medicine.specification?:""
            packing = medicine.packing?:""
            medicare = when(medicine.medicare){
                "甲类" -> Medicare.A
                "乙类" -> Medicare.B
                else -> Medicare.NONE
            }
            manufacturer = ""
            address = ""
        }
        searchCategory()
    }
}