package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import db
import model.database.Medicine
import model.database.medicines
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import viewmodel.MedicineListPageViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun MedicineContextMenu(medicine: Medicine, content: @Composable () -> Unit) = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("添加到结算单") {

            }
        )
    },
    content = content
)