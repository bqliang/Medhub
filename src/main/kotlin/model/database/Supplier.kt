package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


/**
 * 供应商
 *
 */
interface Supplier : Entity<Supplier>, Stakeholder {
    companion object : Entity.Factory<Supplier>()

    /**
     * 供应商编号
     */
    val id: Int

    /**
     * 企业名称
     */
    var name: String

    /**
     * 企业地址
     */
    var address: String

    /**
     * 联系电话
     */
    var phone: String

    /**
     * 电子邮箱
     */
    var email: String?
}


/**
 * 供应商信息表
 */
object Suppliers : Table<Supplier>("supplier") {
    /**
     * 供应商编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 企业名
     */
    var name = varchar("name").bindTo { it.name }

    /**
     * 企业地址
     */
    var address = varchar("address").bindTo { it.address }

    /**
     * 联系电话
     */
    var phone = varchar("phone").bindTo { it.phone }

    /**
     * 电子邮箱
     */
    var email = varchar("email").bindTo { it.email }
}


val Database.suppliers get() = this.sequenceOf(Suppliers)
