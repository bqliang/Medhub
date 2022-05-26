package ui.contextmenu

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import db
import model.database.User
import model.database.users
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import viewmodel.UserListPageViewModel

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
fun UserContextMenu(user: User, content: @Composable () -> Unit)
    = ContextMenuArea(
        items = {
            listOf(
                ContextMenuItem("删除") {
                    UserListPageViewModel.users.remove(user)
                    db.users.find { it.id eq user.id }?.delete()
                },
                ContextMenuItem("修改信息") {

                }
            )
        },
        content = content
    )