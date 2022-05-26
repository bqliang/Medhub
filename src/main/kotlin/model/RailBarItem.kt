package model

import androidx.compose.runtime.State

enum class RailBarItem(val string: String, val iconPath: String) {
    MEDICINE("药品管理", "medication_white_24dp.svg"),
    FRE("财政收支", "account_balance_white_24dp.svg"),
    MEMBER("会员管理", "group_white_24dp.svg"),
    SUPPLIER("供应商管理", "apartment_white_24dp.svg"),
    CATEGORY("分类管理", "category_white_24dp.svg"),
    USER("员工管理", "badge_white_24dp.svg")
}