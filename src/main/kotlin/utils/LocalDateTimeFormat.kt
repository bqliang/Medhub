package utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter by lazy { DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss") }

private fun LocalDateTime._format() = dateTimeFormatter.format(this)

val LocalDateTime.string: String get() = this._format()