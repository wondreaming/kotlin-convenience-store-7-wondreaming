package store.controller

import store.model.PromotionType
import store.util.DateUtils.stringToLocalDate
import java.io.File

class PromotionTypeController {
    private val promotionFilePath = "src/main/resources/promotions.md"

    fun loadPromotionType(): List<PromotionType> =
        File(promotionFilePath).readLines()
            .filterNot { it.startsWith("name") }
            .map { parsePromotionType(it) }

    private fun parsePromotionType(line: String): PromotionType {
        val (name, buyQuantity, freeQuantity, startDate, endDate) = line.split(",")
        return PromotionType(
            name = name,
            buyQuantity = buyQuantity.toInt(),
            freeQuantity = freeQuantity.toInt(),
            startDate = stringToLocalDate(startDate),
            endDate = stringToLocalDate(endDate),
        )
    }
}