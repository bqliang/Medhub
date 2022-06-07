package logic

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy
import db
import kotlinx.coroutines.launch
import model.database.*
import org.ktorm.entity.map
import scope
import utils.desktopPath
import viewmodel.DashboardViewModel

fun exportMembers() = scope.launch {
    val pathName = "${desktopPath}\\会员信息表_${System.currentTimeMillis()}.xlsx"

    try {
        EasyExcel.write(pathName)
            .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
            .head(
                arrayListOf(
                    arrayListOf("会员编号"),
                    arrayListOf("姓名"),
                    arrayListOf("性别"),
                    arrayListOf("手机号"),
                    arrayListOf("注册日期")
                ) as List<List<String>>
            )
            .sheet()
            .doWrite { db.members.map { arrayListOf(it.id, it.name, it.sex, it.phone, it.regdate.toString()) } }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("导出失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState
        .showSnackbar("导出成功：${pathName}")
}


fun exportMedicines() = scope.launch {
    val pathName = "${desktopPath}\\药品信息表_${System.currentTimeMillis()}.xlsx"

    try {
        EasyExcel.write(pathName)
            .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
            .head(
                arrayListOf(
                    arrayListOf("药品编号"),
                    arrayListOf("批准文号"),
                    arrayListOf("药品名称"),
                    arrayListOf("分类"),
                    arrayListOf("子分类"),
                    arrayListOf("采购单价"),
                    arrayListOf("销售单价"),
                    arrayListOf("库存"),
                    arrayListOf("库存下限"),
                    arrayListOf("规格"),
                    arrayListOf("包装"),
                    arrayListOf("医保类型"),
                    arrayListOf("生产厂家"),
                    arrayListOf("生产地址"),
                ) as List<List<String>>
            )
            .sheet()
            .doWrite {
                db.medicines.map {
                    arrayListOf(
                        it.id,
                        it.code,
                        it.name,
                        it.category.category.name,
                        it.category.name,
                        it.purchasePrice,
                        it.price,
                        it.inventory,
                        it.limit,
                        it.specification,
                        it.packing,
                        it.medicare,
                        it.manufacturer,
                        it.address
                    )
                }
            }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("导出失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState
        .showSnackbar("导出成功：${pathName}")
}


fun exportSuppliers() = scope.launch {
    val pathName = "${desktopPath}\\供应商信息表_${System.currentTimeMillis()}.xlsx"

    try {
        EasyExcel.write(pathName)
            .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
            .head(
                arrayListOf(
                    arrayListOf("供应商编号"),
                    arrayListOf("名称"),
                    arrayListOf("地址"),
                    arrayListOf("联系电话"),
                    arrayListOf("电子邮箱")
                ) as List<List<String>>
            )
            .sheet()
            .doWrite { db.suppliers.map { arrayListOf(it.id, it.name, it.address, it.phone, it.email) } }
    } catch (e: Exception) {
        DashboardViewModel.snackbarHostState
            .showSnackbar("导出失败：${e.message}")
        return@launch
    }

    DashboardViewModel.snackbarHostState
        .showSnackbar("导出成功：${pathName}")
}


fun exportFres() = scope.launch {
    val pathName = "${desktopPath}\\财政收支信息表_${System.currentTimeMillis()}.xlsx"

    try {
        val excelWriter = EasyExcel
            .write(pathName)
            .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
            .build()

        val sheet1 = EasyExcel
            .writerSheet(1, "财政收支单")
            .head(
                arrayListOf(
                    arrayListOf("收支编号"),
                    arrayListOf("类型"),
                    arrayListOf("总金额"),
                    arrayListOf("经手人"),
                    arrayListOf("供应商"),
                    arrayListOf("客户"),
                    arrayListOf("时间")
                ) as List<List<String>>
            )
            .build()

        val data1 = db.fres.map {
            arrayListOf(
                it.id,
                it.type,
                it.total,
                it.user.name,
                if (it.type == "销售") "" else it.supplier!!.name,
                if (it.type == "销售") it.member!!.name else "",
                it.time.toString()
            )
        }

        val sheet2 = EasyExcel
            .writerSheet(2, "财政流水")
            .head(
                arrayListOf(
                    arrayListOf("所属财政编号"),
                    arrayListOf("类型"),
                    arrayListOf("药品编号"),
                    arrayListOf("药品名称"),
                    arrayListOf("价格"),
                    arrayListOf("数量"),
                    arrayListOf("小计"),
                    arrayListOf("时间"),
                ) as List<List<String>>
            )
            .build()

        val data2 = db.freItems.map {
            arrayListOf(
                it.fre.id,
                it.fre.type,
                it.medicine.id,
                it.medicine.name,
                it.price,
                it.quantity,
                it.subTotal,
                it.fre.time.toString()
            )
        }

        excelWriter.write(data1, sheet1)
        excelWriter.write(data2, sheet2)
        excelWriter.finish()

    } catch (e: Exception) {
        e.printStackTrace()
        DashboardViewModel.snackbarHostState.showSnackbar("导出失败：${e.message}")
        return@launch
    }
    DashboardViewModel.snackbarHostState.showSnackbar("导出成功：${pathName}")
}