package logic

import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import medicinePageState
import memberPageState
import model.MedicinePageState
import model.MemberPageState
import model.SupplierPageState
import model.database.*
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import scope
import supplierPageState
import viewmodel.*

fun addMember() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleMemberPageViewModel
    val member = Member {
        name = viewModel.name
        sex = viewModel.sex
        phone = viewModel.phone
    }

    try {
        db.members.add(member)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("添加失败，error: ${e.message}")
        return@launch
    }

    memberPageState = MemberPageState.List
    searchMember()
    DashboardViewModel.snackbarHostState
        .showSnackbar("添加成功")
}


fun addMedicine() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleMedicinePageViewModel
    try {
        val medicine = Medicine {
            name = viewModel.name
            code = viewModel.code
            category = viewModel.subCatrgory!!
            purchasePrice = viewModel.purchasePrice.toFloat()
            price = viewModel.price.toFloat()
            inventory = viewModel.inventory.toInt()
            limit = viewModel.limit.toInt()
            specification = viewModel.specification.ifBlank { null }
            packing = viewModel.packing.ifBlank { null }
            medicare = viewModel.medicare.str
            manufacturer = viewModel.manufacturer
            address = viewModel.address.ifBlank { null }
        }

        db.medicines.add(medicine)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("添加失败：${e.message}")
        return@launch
    }
    searchMedicine()
    medicinePageState = MedicinePageState.List
    DashboardViewModel.snackbarHostState
        .showSnackbar("添加成功")
}


fun addSupplier() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleSupplierPageViewModel
    val supplier = Supplier {
        name = viewModel.name
        address = viewModel.address
        phone = viewModel.phone
        email = viewModel.email.ifBlank { null }
    }

    try {
        db.suppliers.add(supplier)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("添加失败，error: ${e.message}")
        return@launch
    }

    supplierPageState = SupplierPageState.List
    searchSupplier()
    DashboardViewModel.snackbarHostState
        .showSnackbar("添加成功")
}


fun addCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel
    val newCategory = Category {
        name = viewModel.curCategoryName
    }

    try {
        db.categories.add(newCategory)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState.showSnackbar("添加失败，error: ${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState.showSnackbar("添加父分类（${newCategory.name}）成功")
    searchCategory()
}


fun addSubCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel

    try {
        val newSubCategory = SubCategory {
            category = db.categories.find { it.id eq viewModel.curCategoryId }!!
            name = viewModel.curSubCategoryName
        }

        db.subCategories.add(newSubCategory)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState.showSnackbar("添加失败，error: ${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState.showSnackbar("添加子分类（${viewModel.curSubCategoryName}）成功")
    getSubCategoriesByCid(viewModel.curCategoryId)
}