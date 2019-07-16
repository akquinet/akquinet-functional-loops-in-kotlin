package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SumOfOrderItemsKtTest {

    private val testItems = listOf(
            OrderItem("OE1234", 10),
            OrderItem("OE56789",15))

    private val sumOfOrderedItems = 25

    @Test
    fun sumImperativ() {
        assertEquals(sumOfOrderedItems, sumImperativ(testItems))
    }

    @Test
    fun sumFunctional() {
        assertEquals(sumOfOrderedItems, sumFunctional(testItems))
    }

    @Test
    fun sumByFunctional() {
        assertEquals(sumOfOrderedItems, sumByFunctional(testItems))
    }

    @Test
    fun filterOrderItemsWithMinimumAmountImperativ() {
        testFilterOrderItemsWithMinimumAmount(::filterOrderItemsWithMinimumAmountImperativ)
    }

    @Test
    fun filterOrderItemsWithMinimumAmountFunctional() {
        testFilterOrderItemsWithMinimumAmount(::filterOrderItemsWithMinimumAmountFunctional)
    }

    private fun testFilterOrderItemsWithMinimumAmount(testFunction: (List<OrderItem>, Int) -> List<OrderItem>) {
        assertEquals(listOf(testItems[1]), testFunction(testItems, 12))
        assertEquals(testItems, testFunction(testItems, 5))
    }
}
