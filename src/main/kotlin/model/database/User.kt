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
 * 系统用户
 *
 */
interface User: Entity<User> {
    companion object: Entity.Factory<User>()

    /**
     * 工号
     */
    val id: Int

    /**
     * 密码
     */
    var password: String

    /**
     * 用户类型
     */
    var type: String

    /**
     * 姓名
     */
    var name: String

    /**
     * 性别
     */
    var sex: String

    /**
     * 手机号
     */
    var phone: String

    /**
     * 入职日期
     */
    var entry: LocalDate

    /**
     * 身份证号码
     */
    var idNum: String
}


/**
 * 系统用户信息表
 *
 */
object Users: Table<User>("user") {
    /**
     * 工号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 密码
     *
     * 插入、更新时直接写入，但读取比对密码时应注意比对的是密码的 MD5
     */
    val password = varchar("password").bindTo { it.password }

    /**
     * 职位
     */
    val type = varchar("type").bindTo { it.type }

    /**
     * 姓名
     */
    val name = varchar("name").bindTo { it.name }

    /**
     * 性别
     */
    val sex = varchar("sex").bindTo { it.sex }

    /**
     * 手机号
     */
    val phone = varchar("phone").bindTo { it.phone }

    /**
     * 入职日期
     */
    val entry = date("entry").bindTo { it.entry }

    /**
     * 身份证号码
     */
    val idNum = varchar("id_num").bindTo { it.idNum }
}


val Database.users get() = this.sequenceOf(Users)