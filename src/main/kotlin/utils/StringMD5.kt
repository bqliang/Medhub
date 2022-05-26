package utils

import java.security.MessageDigest

// MD5 转换器
private val messageDigest by lazy { MessageDigest.getInstance("MD5") }


/**
 * 生成字符串的 MD5
 *
 * @return
 */
private fun String.md5(): String {

    // 输入的字符串转换成字节数组
    val inputByteArray = this.toByteArray()

    // 得到传入字节数组的哈希值字节数组
    val hashInBytes = messageDigest.digest(inputByteArray)

    val stringBuilder = StringBuilder()

    /**
     * %x -> 整数类型（十六进制）
     * 02 -> 不足两位则补 0
     */
    for (byte in hashInBytes) {
        stringBuilder.append(String.format("%02x", byte))
    }

    return stringBuilder.toString()
}

/**
 * 定义一个外挂属性
 */
val String.md5 get() = this.md5()