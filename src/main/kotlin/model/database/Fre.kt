package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.*
import java.time.LocalDateTime

/**
 * 财政收支
 *
 */
interface Fre: Entity<Fre> {
    companion object: Entity.Factory<Fre>()

    /**
     * 财政收支编号
     */
    val id: Int

    /**
     * 收支类型（采购、销售）
     */
    val type: String

    /**
     * 收支时间
     */
    val time: LocalDateTime

    /**
     * 收支总额
     */
    val total: Float

    /**
     * 经手人
     */
    val user: User

    /**
     * 供应商
     */
    val supplier: Supplier?

    /**
     * 会员
     */
    val member: Member?
}


/**
 * 财政收支信息表
 */
object Fres: Table<Fre>("fre") {
    /**
     * 收支编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 财政收支类型
     */
    val type = varchar("type").bindTo { it.type }

    /**
     * 时间
     */
    val time = datetime("time").bindTo { it.time }

    /**
     * 收支总额
     */
    val total = float("total").bindTo { it.total }

    /**
     * 经手人工号
     */
    val uid = int("uid").references(Users) { it.user }

    /**
     * 供应商编号
     */
    val sid = int("sid").references(Suppliers) { it.supplier }

    /**
     * 会员编号
     */
    val mid = int("mid").references(Members) { it.member }
}


val Database.fres get() = this.sequenceOf(Fres)