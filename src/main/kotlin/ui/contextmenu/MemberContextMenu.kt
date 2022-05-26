package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import db
import model.database.Member
import model.database.members
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import viewmodel.MemberListPageViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun MemberContextMenu(member: Member, content: @Composable () -> Unit)
        = ContextMenuArea(
    items = {
        listOf(
            ContextMenuItem("创建收入订单") {
                // TODO 使用该会员创建收入订单
            }
        )
    },
    content = content
)