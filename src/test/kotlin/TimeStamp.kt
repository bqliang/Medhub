import model.database.fres
import org.ktorm.entity.forEach
import utils.string

fun main() {

    db.fres.forEach {
        println(it.time.string)
    }
}