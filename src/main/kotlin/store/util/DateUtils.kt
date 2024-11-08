package store.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    fun stringToLocalDateTime(dateTimeString: String): LocalDateTime {
        val formattedDateTimeString = if (dateTimeString.length == 10) {
            "$dateTimeString 00:00:00"
        } else {
            dateTimeString
        }

        val formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)
        return LocalDateTime.parse(formattedDateTimeString, formatter)
    }
}