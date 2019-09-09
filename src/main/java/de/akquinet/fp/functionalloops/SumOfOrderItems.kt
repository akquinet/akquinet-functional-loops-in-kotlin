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

fun filterOrderItemsWithMinimumAmountImperativ(items: List<OrderItem>, amount: Int)
    : List<OrderItem> {
    val result = mutableListOf<OrderItem>()
    val iterator = items.listIterator()
    while (iterator.hasNext()) {
        val orderItem = iterator.next()
        if (orderItem.amount >= amount) {
            result.add(orderItem)
        }
    }
    return result
}

fun filterOrderItemsWithMinimumAmountFunctional(items: List<OrderItem>, amount: Int)
    : List<OrderItem> =
    items.fold(emptyList())
    { list, orderItem ->
        if (orderItem.amount >= amount)
            list.plus(orderItem)
        else list
    }

fun filterOrderItemsWithMinimumAmountFunctional2(items: List<OrderItem>, amount: Int)
    : List<OrderItem> =
    items.filter { item -> item.amount >= amount }


fun sumByFunctional(items: List<OrderItem>): Int = items.sumBy(OrderItem::amount)
