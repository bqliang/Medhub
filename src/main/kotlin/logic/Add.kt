package logic

import checkoutWindowState
import db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import loginUserId
import medicinePageState
import memberPageState
import model.*
import model.database.*
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.maxBy
import scope
import supplierPageState
import userPageState
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


fun addUser() = scope.launch(Dispatchers.IO) {
    val viewModel = HandleUserPageViewModel
    val user = User {
        name = viewModel.name
        sex = viewModel.sex
        phone = viewModel.phone
        idNum = viewModel.idNum
        type = viewModel.type
        entry = viewModel.entry
    }

    try {
        db.users.add(user)
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("添加失败，error: ${e.message}")
        return@launch
    }

    userPageState = UserPageState.List
    searchUsers()
    DashboardViewModel.snackbarHostState
        .showSnackbar("添加成功")
}


fun addFre() = scope.launch(Dispatchers.IO) {
    val viewModel = CheckoutPageViewModel

    val fre = Fre {
        id = db.fres.maxBy { it.id }?:100 + 1
        total = viewModel.list.sumOf { it.subTotal.toDouble() }.toFloat()
        user = db.users.find { it.id eq loginUserId }!!
        if (checkoutWindowState == CheckoutPageState.Sales) {
            member = viewModel.member
            type = "销售"
        } else if (checkoutWindowState == CheckoutPageState.Purchase) {
            supplier = viewModel.supplier
            type = "采购"
        }
    }

    try {
        db.useTransaction {
            db.fres.add(fre)
            viewModel.list.forEach { medicine ->
                val freItem = FreItem {
                    this.fre = fre
                    this.medicine = medicine
                    this.price = if (checkoutWindowState == CheckoutPageState.Sales) medicine.price else medicine.purchasePrice
                    this.quantity = medicine.checkoutQuantity!!
                    this.subTotal = medicine.subTotal
                }
                db.freItems.add(freItem)
            }
        }
        CheckoutPageViewModel.closeCheckoutWindow()
        DashboardViewModel.snackbarHostState.showSnackbar("保存成功")
        searchMedicine()
        searchFre()
    } catch (e : Exception) {
        DashboardViewModel.snackbarHostState.showSnackbar("保存失败：${e.message}")
    }
}