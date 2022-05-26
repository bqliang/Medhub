package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * 药品
 *
 */
interface Medicine: Entity<Medicine> {
    companion object : Entity.Factory<Medicine>()

    /**
     * 药品编号
     */
    val id: Int

    /**
     * 批准文号
     */
    var code: String

    /**
     * 药品名称
     */
    var name: String

    /**
     * 所属子分类
     */
    var category: SubCategory

    /**
     * 采购单价
     */
    var purchasePrice: Float

    /**
     * 销售单价
     */
    var price: Float

    /**
     * 库存
     */
    var inventory: Int

    /**
     * 库存下限
     */
    var limit: Int

    /**
     * 规格
     */
    var specification: String?

    /**
     * 包装
     */
    var packing: String?

    /**
     * 医保类型
     */
    var medicare: String

    /**
     * 生产厂家
     */
    var manufacturer: String

    /**
     * 生产地址
     */
    var address: String?
}


/**
 * 药品信息表
 */
object Medicines: Table<Medicine>("medicine") {
    /**
     * 药品编号
     */
    val id = int("id").primaryKey().bindTo { it.id }

    /**
     * 批准文号
     */
    val code = varchar("code").bindTo { it.code }

    /**
     * 药品名称
     */
    val name = varchar("name").bindTo { it.name }

    /**
     * 所属子分类编号
     */
    val cid = int("cid").references(SubCategories){ it.category }

    /**
     * 采购价格
     */
    val purchasePrice = float("purchase_price").bindTo { it.purchasePrice }

    /**
     * 销售单价
     */
    val price = float("price").bindTo { it.price }

    /**
     * 库存
     */
    val inventory = int("inventory").bindTo { it.inventory }

    /**
     * 库存下限
     */
    val limit = int("limit").bindTo { it.limit }

    /**
     * 规格
     */
    val specification = varchar("specification").bindTo { it.specification }

    /**
     * 包装
     */
    val packing = varchar("packing").bindTo { it.packing }

    /**
     * 医保类型
     */
    val medicare = varchar("medicare").bindTo { it.medicare }

    /**
     * 生产厂家
     */
    val manufacturer = varchar("manufacturer").bindTo { it.manufacturer }

    /**
     * 生产地址
     */
    val address = varchar("address").bindTo { it.address }
}


val Database.medicines get() = this.sequenceOf(Medicines)
