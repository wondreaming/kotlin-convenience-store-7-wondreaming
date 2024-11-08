package store.controller

import store.model.PromotionType
import store.util.DateUtils.stringToLocalDateTime
import java.io.File

class PromotionTypeController {
    private val promotionFilePath = PROMOTION_FILE_PATH

    fun loadPromotionType(): List<PromotionType> =
        File(promotionFilePath).readLines()
            .filterNot { it.startsWith(HEADER_NAME) }
            .map { parsePromotionType(it) }

    private fun parsePromotionType(line: String): PromotionType {
        val (name, buyQuantity, freeQuantity, startDate, endDate) = line.split(DELIMITER)
        return PromotionType(
            name = name,
            buyQuantity = buyQuantity.toInt(),
            freeQuantity = freeQuantity.toInt(),
            startDate = stringToLocalDateTime(startDate),
            endDate = stringToLocalDateTime(endDate),
        )
    }

    companion object {
        private const val PROMOTION_FILE_PATH = "src/main/resources/promotions.md"
        private const val HEADER_NAME = "name"
        private const val DELIMITER = ","
    }
}