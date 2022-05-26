package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * 药品分类
 *
 */
interface Category: Entity<Category> {
    companion object: Entity.Factory<Category>()

    /**
     * 分类编号
     */
    val id: Int

    /**
     * 分类名
     */
    var name: String
}


/**
 * 分类信息表
 */
object Categories: Table<Category>("category"){
    /**
     * 分类编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 分类名
     */
    val name = varchar("name").bindTo { it.name }
}


val Database.categories get() = this.sequenceOf(Categories)

