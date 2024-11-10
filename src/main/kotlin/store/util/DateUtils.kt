package store.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    private const val TEN = 10
    private const val DATE_TIME_FORMAT = "%s 00:00:00"

    fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
        val formattedDateTimeString = if (dateTimeString.length == TEN) {
            DATE_TIME_FORMAT.format(dateTimeString)
        } else {
            dateTimeString
        }
        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        return LocalDateTime.parse(formattedDateTimeString, formatter)
    }
}