package logic

import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
import viewmodel.*

fun deleteMember() = scope.launch(Dispatchers.IO) {
    try {
        db.members
            .find { it.id eq HandleMemberPageViewModel.id }!!
            .delete()
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    searchMember()

    memberPageState = MemberPageState.List

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")
}


fun deleteUser() = scope.launch(Dispatchers.IO) {
    try {
        db.users
            .find { it.id eq HandleUserPageViewModel.id }!!
            .delete()
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    searchUsers()

    userPageState = UserPageState.List

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")
}


fun deleteMedicine() = scope.launch(Dispatchers.IO) {
    try {
        db.medicines
            .find { it.id eq HandleMedicinePageViewModel.id }!!
            .delete()
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    searchMedicine()

    medicinePageState = MedicinePageState.List

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")
}


fun deleteSupplier() = scope.launch(Dispatchers.IO) {
    try {
        db.suppliers
            .find { it.id eq HandleSupplierPageViewModel.id }!!
            .delete()
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    searchSupplier()

    supplierPageState = SupplierPageState.List

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")
}


fun deleteSubCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel
    if (viewModel.curSubCategoryId == -1){
        DashboardViewModel.snackbarHostState
            .showSnackbar("您尚未选中任何子分类")
        return@launch
    }

    try {
        db.subCategories.find { it.id eq viewModel.curSubCategoryId }!!.delete()
    }catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")

    getSubCategoriesByCid(viewModel.curCategoryId)
}

fun deleteCategory() = scope.launch(Dispatchers.IO) {
    val viewModel = CategoryListPageViewModel
    if (viewModel.curCategoryId == -1){
        DashboardViewModel.snackbarHostState
            .showSnackbar("您尚未选中任何父分类")
        return@launch
    }

    try {
        db.categories.find { it.id eq viewModel.curCategoryId }!!.delete()
    }catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("删除失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState
        .showSnackbar("删除成功")
}