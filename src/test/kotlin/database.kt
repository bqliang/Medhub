import model.database.categories
import org.ktorm.dsl.eq
import org.ktorm.entity.find

fun main() {
    db.categories.find { it.name eq "未分类" }?.delete()
}