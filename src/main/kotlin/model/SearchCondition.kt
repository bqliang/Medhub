package model

enum class UserSearchCondition(val str: String) {
    ID("工号"),
    NAME("姓名"),
    PHONE("手机号")
}

enum class MedicineSearchCondition(val str: String) {
    ID("药品编号"),
    NAME("药品名称"),
    CODE("批准文号"),
    MANUFACTURER("生产厂家")
}

enum class MemberSearchCondition(val str: String) {
    ID("会员编号"),
    NAME("姓名"),
    PHONE("手机号")
}

enum class SupplierSearchCondition(val str: String) {
    ID("编号"),
    NAME("名称"),
    ADDRESS("地址"),
    PHONE("电话"),
    EMAIL("电子邮箱")
}