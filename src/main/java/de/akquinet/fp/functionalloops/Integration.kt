package de.akquinet.fp.functionalloops

typealias IntegrationType = (Double, Double, Int, (Double) -> Double) -> Double

fun integrateImperative(start :Double, end : Double, precision: Int, f : (Double) -> Double) : Double {
    val step = (end-start) / precision
    var result = 0.0
    var x = start
    for ( i in 0 .. precision) {
        result += f(x) * step
        x += step
    }
    return result
}

fun integrateFunctional(start :Double, end : Double, precision: Int, f : (Double) -> Double) : Double {

    return 0.0
}
