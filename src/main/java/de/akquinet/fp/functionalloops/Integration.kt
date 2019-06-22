package de.akquinet.fp.functionalloops

typealias IntegrationType = (Double, Double, Int, (Double) -> Double) -> Double

val integrateImperative: IntegrationType = { start, end, precision, f ->
    val step = (end-start) / precision
    var result = 0.0
    var x = start
    for ( i in 0 .. precision) {
        result += f(x) * step
        x += step
    }
    result
}
