package ui.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.database.User
import ui.contextmenu.UserContextMenu
import viewmodel.HandleUserPageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(user: User) = Card(
    elevation = 4.dp,
    onClick = { HandleUserPageViewModel.startHandleUserPage(user) },
    content = {
        UserContextMenu(user) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Row {
                        Text(
                            text = user.name,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            painter = painterResource(if (user.sex == "男") "male_white_24dp.svg" else "female_white_24dp.svg"),
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "职位：${user.type}"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "手机号：${user.phone}"
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = String.format("%06d", user.id),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Thin
                )
            }
        }
    }
)
