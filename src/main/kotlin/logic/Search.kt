package logic

import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.MedicineSearchCondition
import model.MemberSearchCondition
import model.SupplierSearchCondition
import model.UserSearchCondition
import model.database.*
import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.entity.filter
import org.ktorm.entity.sortedByDescending
import org.ktorm.entity.toList
import scope
import viewmodel.*

/**
 * 搜索用户
 *
 */
fun searchUsers() = scope.launch(Dispatchers.IO) {
    val viewModel = UserListPageViewModel
    val keyword = viewModel.searchKeyword.trim()

    val users = db.users
        .filter {
            when (viewModel.userSearchCondition) {
                UserSearchCondition.ID -> it.id like "%$keyword%"
                UserSearchCondition.NAME -> it.name like "%$keyword%"
                UserSearchCondition.PHONE -> it.phone like "%$keyword%"
            }
        }
        .toList()

    viewModel.refresh(users)
}


/**
 * 搜索药品
 *
 */
fun searchMedicine() = scope.launch(Dispatchers.IO) {
    val viewModel = MedicineListPageViewModel
    val keyword = viewModel.searchKeyword.trim()

    val medicines = db.medicines
        .filter {
            when (viewModel.medicineSearchCondition) {
                MedicineSearchCondition.NAME -> it.name like "%$keyword%"
                MedicineSearchCondition.ID -> it.id like "%$keyword%"
                MedicineSearchCondition.CODE -> it.code like "%$keyword%"
                MedicineSearchCondition.MANUFACTURER -> it.manufacturer like "%$keyword%"
            }
        }
        .toList()

    viewModel.refresh(medicines)
}


/**
 * 搜索会员
 *
 */
fun searchMember() = scope.launch(Dispatchers.IO) {
    val viewModel = MemberListPageViewModel
    val keyword = viewModel.searchKeyword.trim()

    val members = db.members
        .filter {
            when (viewModel.memberSearchCondition) {
                MemberSearchCondition.ID -> it.id like "%$keyword%"
                MemberSearchCondition.NAME -> it.name like "%$keyword%"
                MemberSearchCondition.PHONE -> it.phone like "%$keyword%"
            }
        }
        .toList()

    viewModel.refresh(members)
}


/**
 * 搜索财政收支
 *
 */
fun searchFre() = scope.launch(Dispatchers.IO) {
    val viewModel = FreListViewModel
    val keyword = viewModel.searchKeyword.trim()

    val fres = db.fres
        .filter { it.type like "%${viewModel.fresType.searchKeyword}%" }
        .filter { it.id like "%${keyword}%" }
        .sortedByDescending { it.time }
        .toList()

    viewModel.refresh(fres)
}


/**
 * 搜索分类
 *
 */
fun searchCategory() = scope.launch(Dispatchers.IO) {
    val categories = db.categories.toList()
    HandleMedicinePageViewModel.categories.apply {
        clear()
        addAll(categories)
    }

    CategoryListPageViewModel.categories.apply {
        clear()
        addAll(categories)
    }
}


/**
 * 搜索子分类
 *
 */
fun searchSubCategory() = scope.launch(Dispatchers.IO) {
    val subCategories = db.subCategories
        .filter { it.cid eq HandleMedicinePageViewModel.catrgory!!.id }
        .toList()

    HandleMedicinePageViewModel.subCategories.apply {
        clear()
        addAll(subCategories)
    }
}


/**
 * 搜索供应商
 *
 */
fun searchSupplier() = scope.launch(Dispatchers.IO) {
    val viewModel = SupplierListPageViewModel
    val keyword = viewModel.searchKeyword.trim()

    val suppliers = db.suppliers
        .filter {
            when (viewModel.supplierSearchCondition) {
                SupplierSearchCondition.ID -> it.id like "%$keyword%"
                SupplierSearchCondition.NAME -> it.name like "%$keyword%"
                SupplierSearchCondition.ADDRESS -> it.address like "%$keyword%"
                SupplierSearchCondition.PHONE -> it.phone like "%$keyword%"
                SupplierSearchCondition.EMAIL -> it.email like "%$keyword%"
            }
        }
        .toList()

    viewModel.refresh(suppliers)
}


fun getSubCategoriesByCid(cid: Int) = scope.launch(Dispatchers.IO) {
    val subCategories = db.subCategories
        .filter { it.cid eq cid }
        .toList()

    CategoryListPageViewModel.subCategories.apply {
        clear()
        addAll(subCategories)
    }
}