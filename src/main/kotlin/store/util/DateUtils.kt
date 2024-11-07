package store.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val DATE_PATTERN = "yyyy-MM-dd"

    fun stringToLocalDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
        return LocalDate.parse(dateString, formatter)
    }
}