package store.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateUtils {
    fun stringToLocalDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.parse(dateString, formatter)
    }
}