package model

enum class FreType(val str: String, val searchKeyword: String) {
    All("所有", ""),
    Purchase("采购", "采购"),
    Sales("销售", "销售")
}