package store.controller

import store.model.PromotionType
import store.util.DateUtils.stringToLocalDate
import java.io.File

class PromtionTypeController {
    private val promotionFilePath = "src/main/resources/promotions.md"

    fun loadPromotionType(): List<PromotionType> =
        File(promotionFilePath).readLines()
            .filterNot { it.startsWith("name") }
            .map { parsePromtionType(it) }

    private fun parsePromtionType(line: String): PromotionType {
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