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
import model.SupplierPageState
import model.database.Supplier
import model.database.User
import supplierPageState
import ui.contextmenu.SupplierContextMenu
import ui.contextmenu.UserContextMenu
import viewmodel.HandleSupplierPageViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SupplierCard(supplier: Supplier) = Card(
    elevation = 4.dp,
    onClick = {
        HandleSupplierPageViewModel.setData(supplier)
        supplierPageState = SupplierPageState.Edit
    },
    content = {
        SupplierContextMenu(supplier) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Column(modifier = Modifier.align(Alignment.TopStart)) {
                    Text(
                        text = supplier.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("地址：${supplier.address}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("联系电话：${supplier.phone}")

                    Spacer(modifier = Modifier.height(6.dp))

                    if (!supplier.email.isNullOrBlank()){
                        Text("邮箱：${supplier.email}")
                    }
                }
                Text(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = String.format("%03d", supplier.id),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Thin
                )
            }
        }
    }
)
