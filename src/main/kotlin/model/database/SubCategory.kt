package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * 药品子分类
 *
 */
interface SubCategory: Entity<SubCategory> {
    companion object: Entity.Factory<SubCategory>()

    /**
     * 子分类编号
     */
    val id: Int

    /**
     * 子分类名
     */
    var name: String

    /**
     * 所属分类
     */
    var category: Category
}


/**
 * 子分类信息表
 */
object SubCategories: Table<SubCategory>("subcategory"){
    /**
     * 子分类编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 子分类名
     */
    val name = varchar("name").bindTo { it.name }

    /**
     * 所属分类
     */
    val cid = int("cid").references(Categories){ it.category }
}


val Database.subCategories get() = this.sequenceOf(SubCategories)