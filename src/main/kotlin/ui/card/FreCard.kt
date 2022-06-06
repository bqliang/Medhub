package ui.card

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.database.Fre
import utils.string
import viewmodel.FreDetailsPageViewmodel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FreCard(fre: Fre) = Card(
    elevation = 4.dp,
    onClick = { FreDetailsPageViewmodel.startFreDetailsPage(fre) },
    content = {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Row {
                    Text(
                        text = fre.type,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        painter = painterResource(if (fre.type == "采购") "local_shipping_white_24dp.svg" else "shopping_cart_checkout_white_24dp.svg"),
                        contentDescription = null
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 4.dp))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${fre.total} ￥"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = when (fre.type) {
                        "采购" -> "供应商：${fre.supplier!!.name}（${fre.supplier!!.id}）"
                        "销售" -> "会员：${fre.member!!.name}（${fre!!.member!!.id}）"
                        else -> ""
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "时间：${fre.time.string}"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "经手人：${fre.user.name}（${fre.user.id}）"
                )
            }
            Text(
                modifier = Modifier.align(Alignment.TopEnd),
                text = String.format("%08d", fre.id),
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Thin
            )
        }
    }
)