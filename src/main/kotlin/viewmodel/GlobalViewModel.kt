import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import model.MedicinePageState
import model.MemberPageState
import model.SupplierPageState
import org.apache.commons.dbcp2.BasicDataSource
import org.ktorm.database.Database
import java.util.prefs.Preferences


val preferences: Preferences =  Preferences.userRoot().node("MedHub")


var isDark by mutableStateOf(preferences.getBoolean("dark_theme", false))

var loginSuccessful by mutableStateOf(false)


// 连接池对象
private val basicDataSource = BasicDataSource().apply {
    driverClassName = "com.mysql.cj.jdbc.Driver"
    url = "jdbc:mysql://112.74.75.19:3306/medhub"
    username = "bqliang"
    password = "mysql"
}

// 数据库对象
val db = Database.connect(basicDataSource)



private val job by lazy { Job() }
val scope by lazy { CoroutineScope(job) }


var memberPageState by mutableStateOf(MemberPageState.List)
var medicinePageState by mutableStateOf(MedicinePageState.List)
var supplierPageState by mutableStateOf(SupplierPageState.List)


