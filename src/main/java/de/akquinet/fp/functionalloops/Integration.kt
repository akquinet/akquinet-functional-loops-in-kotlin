package de.akquinet.fp.functionalloops

typealias IntegrationType = (Double, Double, Long, (Double) -> Double) -> Double

fun integrateImperative(start :Double, end : Double, precision: Long, f : (Double) -> Double) : Double {
    val step = (end-start) / precision
    var result = 0.0
    var x = start
    for ( i in 0 .. precision) {
        result += f(x) * step
        x += step
    }
    return result
}

fun integrateFunctional(start :Double, end : Double, precision: Long, f : (Double) -> Double) : Double {
    val step = (end-start) / precision
    return ( 0 .. precision)
            .map { index -> start + index * step}
            .map { x -> f(x) * step }
            .sum()
}

fun integrateFunctionalSequence(start :Double, end : Double, precision: Long, f : (Double) -> Double) : Double {
    val step = (end-start) / precision
    return ( 0 .. precision).asSequence()
            .map { index -> start + index * step}
            .map { x -> f(x) * step }
            .sum()
}
