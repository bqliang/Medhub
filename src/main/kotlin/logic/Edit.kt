package logic

import changePasswordPageVisible
import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loginUserId
import medicinePageState
import memberPageState
import model.MedicinePageState
import model.MemberPageState
import model.SupplierPageState
import model.UserPageState
import model.database.*
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import scope
import supplierPageState
import userPageState
import utils.md5
import viewmodel.*


fun editMember() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleMemberPageViewModel

    try {
        db.members
            .find { it.id eq viewModel.id }!!.apply {
                name = viewModel.name
                sex = viewModel.sex
                phone = viewModel.phone
                flushChanges()
            }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("修改失败：${e.message}")
        return@launch
    }
    searchMember()
    memberPageState = MemberPageState.List
    DashboardViewModel.snackbarHostState
        .showSnackbar("修改成功")
}


fun editMedicine() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleMedicinePageViewModel
    try {
        db.medicines.find { it.id eq viewModel.id }!!.apply {
            code = viewModel.code
            name = viewModel.name
            inventory = viewModel.inventory.toInt()
            limit = viewModel.limit.toInt()
            category = viewModel.subCatrgory!!
            purchasePrice = viewModel.purchasePrice.toFloat()
            price = viewModel.price.toFloat()
            medicare = viewModel.medicare.str
            specification = viewModel.specification.ifBlank { null }
            packing = viewModel.packing.ifBlank { null }
            manufacturer = viewModel.manufacturer
            address = viewModel.address.ifBlank { null }
            flushChanges()
        }
    }catch (e: Exception){
        DashboardViewModel.snackbarHostState
            .showSnackbar("修改失败：${e.message}")
        return@launch
    }
    searchMedicine()
    medicinePageState = MedicinePageState.List
    DashboardViewModel.snackbarHostState
        .showSnackbar("修改成功")
}


fun editSupplier() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleSupplierPageViewModel

    try {
        db.suppliers
            .find { it.id eq viewModel.id }!!.apply {
                name = viewModel.name
                address = viewModel.address
                phone = viewModel.phone
                email = viewModel.email.ifBlank { null }
                flushChanges()
            }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("修改失败：${e.message}")
        return@launch
    }
    searchSupplier()
    supplierPageState = SupplierPageState.List
    DashboardViewModel.snackbarHostState
        .showSnackbar("修改成功")
}


fun editCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel

    if (viewModel.curCategoryId == -1){
        DashboardViewModel.snackbarHostState
            .showSnackbar("您尚未选中任何父分类")
        return@launch
    }

    try {
        db.categories
            .find { it.id eq viewModel.curCategoryId }!!.apply {
                name = viewModel.curCategoryName
                flushChanges()
            }
    } catch (e : Exception) {
        DashboardViewModel.snackbarHostState.showSnackbar("修改失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState.showSnackbar("修改成功")
    searchCategory()
}


fun editSubCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel

    if (viewModel.curSubCategoryId == -1){
        DashboardViewModel.snackbarHostState
            .showSnackbar("您尚未选中任何子分类")
        return@launch
    }

    try {
        db.subCategories
            .find { it.id eq viewModel.curSubCategoryId }!!.apply {
                name = viewModel.curSubCategoryName
                flushChanges()
            }
    } catch (e : Exception) {
        DashboardViewModel.snackbarHostState.showSnackbar("修改失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState.showSnackbar("修改成功")
    getSubCategoriesByCid(viewModel.curCategoryId)
}


fun editUser() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleUserPageViewModel

    try {
        db.users
            .find { it.id eq viewModel.id }!!.apply {
                name = viewModel.name
                sex = viewModel.sex
                phone = viewModel.phone
                type = viewModel.type
                idNum = viewModel.idNum
                flushChanges()
            }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("修改失败：${e.message}")
        return@launch
    }
    searchUsers()
    userPageState = UserPageState.List
    DashboardViewModel.snackbarHostState
        .showSnackbar("修改成功")
}


fun editPassword() = scope.launch(Dispatchers.IO) {
    val viewModel = ChangePasswordPageViewModel

    try {
        val user = db.users.find { it.id eq loginUserId }
        if (viewModel.oldPassword.md5 != user!!.password) {
            viewModel.snackbarHostState.showSnackbar("修改失败（原密码不正确）")
            return@launch
        } else {
            user.password = viewModel.passwordConfirm
            user.flushChanges()
        }
    } catch (e: Exception) {
        viewModel.snackbarHostState.showSnackbar("发生错误：${e.message}")
    }

    changePasswordPageVisible = false
    DashboardViewModel.snackbarHostState.showSnackbar("密码修改成功")
}