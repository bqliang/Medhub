package model.database

import org.ktorm.database.Database
import org.ktorm.entity.Entity
import org.ktorm.entity.sequenceOf
import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.int

/**
 * 财政收支流水
 *
 */
interface FreItem: Entity<FreItem> {
    companion object: Entity.Factory<FreItem>()

    /**
     * 所属财政收支
     */
    var fre: Fre

    /**
     * 药品
     */
    var medicine: Medicine

    /**
     * 彼时单价
     */
    var price: Float

    /**
     * 数量
     */
    var quantity: Int

    /**
     * 金额小计
     */
    var subTotal: Float
}


object FreItems: Table<FreItem>("fre_item") {

    /**
     * 所属财政收支编号
     */
    val fre_id = int("fre_id").primaryKey().references(Fres) { it.fre }

    /**
     * 药品编号
     */
    val mid = int("mid").primaryKey().references(Medicines) { it.medicine }

    /**
     * 彼时药品单价
     */
    val price = float("price").bindTo { it.price }

    /**
     * 数量
     */
    val quantity = int("quantity").bindTo { it.quantity }

    /**
     * 金额小计
     */
    val subTotal = float("subtotal").bindTo { it.subTotal }
}


val Database.freItems get() = this.sequenceOf(FreItems)