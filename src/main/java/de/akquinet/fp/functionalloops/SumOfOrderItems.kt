package de.akquinet.fp.functionalloops

data class OrderItem(val orderNumber: String, val amount: Int)

fun sumImperativ(items: List<OrderItem>): Int {
    var result = 0
    for (i in 0 until items.size) {
        result += items[i].amount
    }
    return result
}

fun sumFunctional(items: List<OrderItem>): Int =
        items
                .map(OrderItem::amount)
                .sum()
