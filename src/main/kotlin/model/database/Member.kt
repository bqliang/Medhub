package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDate

/**
 * 会员
 *
 */
interface Member : Entity<Member>, Stakeholder {
    companion object : Entity.Factory<Member>()

    /**
     * 会员编号
     */
    val id: Int

    /**
     * 会员姓名
     */
    var name: String

    /**
     * 手机号
     */
    var phone: String

    /**
     * 性别
     */
    var sex: String

    /**
     * 注册日期
     */
    val regdate: LocalDate
}


/**
 * 会员信息表
 */
object Members : Table<Member>("member") {
    /**
     * 会员编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 会员姓名
     */
    val name = varchar("name").bindTo { it.name }

    /**
     * 手机号
     */
    val phone = varchar("phone").bindTo { it.phone }

    /**
     * 性别
     */
    val sex = varchar("sex").bindTo { it.sex }

    /**
     * 注册日期
     */
    val regdate = date("regdate").bindTo { it.regdate }

}


val Database.members get() = sequenceOf(Members)
