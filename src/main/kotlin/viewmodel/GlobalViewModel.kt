import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import model.*
import org.apache.commons.dbcp2.BasicDataSource
import org.ktorm.database.Database
import java.util.prefs.Preferences


val preferences: Preferences = Preferences.userRoot().node("MedHub")


var isDark by mutableStateOf(preferences.getBoolean("dark_theme", false))

var loginSuccessful by mutableStateOf(false)

private val DB_HOST by lazy { System.getenv("MEDHUB_DB_HOST") }
private val DB_USERNAME by lazy { System.getenv("MEDHUB_DB_USERNAME") }
private val DB_PASSWORD by lazy { System.getenv("MEDHUB_DB_PASSWORD") }

// 连接池对象
private val basicDataSource by lazy {
    BasicDataSource().apply {
        driverClassName = "com.mysql.cj.jdbc.Driver"
        url = "jdbc:mysql://${DB_HOST}:3306/medhub"
        username = DB_USERNAME
        password = DB_PASSWORD
    }
}

var loginUserId = -1000

// 数据库对象
val db = Database.connect(basicDataSource)


private val job by lazy { Job() }
val scope by lazy { CoroutineScope(job) }


var memberPageState by mutableStateOf(MemberPageState.List)
var userPageState by mutableStateOf(UserPageState.List)
var medicinePageState by mutableStateOf(MedicinePageState.List)
var supplierPageState by mutableStateOf(SupplierPageState.List)
var frePageState by mutableStateOf(FrePageState.List)
var checkoutWindowState by mutableStateOf(CheckoutPageState.Invisible)
var changePasswordPageVisible by mutableStateOf(false)