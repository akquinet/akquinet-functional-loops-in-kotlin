package de.akquinet.fp.functionalloops

import java.util.stream.DoubleStream
import kotlin.system.measureTimeMillis

typealias IntegrationType = (Double, Double, Int, (Double) -> Double) -> Double

fun integrateImperative(start: Double, end: Double, precision: Int,
                        f: (Double) -> Double): Double {
    val step = (end - start) / precision
    var result = 0.0
    var x = start
    for (i in 0 until precision) {
        result += f(x) * step
        x += step
    }
    return result
}

fun integrateFunctional(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    return (0 until precision)
            .map { index -> start + index * step }
            .map { x -> f(x) * step }
            .sum()
}

fun integrateFunctionalCleanCode(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    val xCoordinates = (0 until precision)
            .map { index -> start + index * step }
    val allRectangles = xCoordinates
            .map { x -> f(x) * step }
    return allRectangles
            .sum()
}

fun integrateFunctionalSumBy(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    return (0 until precision).sumByDouble { index ->
        val x = start + index * step
        f(x) * step
    }
}

fun integrateFunctionalFold(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    return (0 until precision).fold(0.0) { sum, index ->
        val x = start + index * step
        val block = f(x) * step
        sum + block
    }
}

fun integrateFunctionalSequence(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    val xCoordinates = (0 until precision).asSequence()
            .map { index -> start + index * step }
    val allRectangles = xCoordinates
            .map { x -> f(x) * step }
    return allRectangles
            .sum()
}

/*
fun integrateFunctionalSequence2(start :Double, end : Double, precision: Long, f : (Double) -> Double) : Double {
 produces a set of step +start, 2*step + start, ...
*/
fun integrateFunctionalSequence2(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    return generateSequence(start) { x -> x + step }
            .map { x -> f(x) * step }
            .take(precision)
            .sum()
}

fun integrateFunctionalStreams(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    val step = (end - start) / precision
    return DoubleStream
            .iterate(start,
                    { x -> x < end }
                    , { x -> x + step })
            .map { x -> f(x) * step }
            .sum()
}

fun integrateFunctionalJavaStreams(start: Double, end: Double, precision: Int, f: (Double) -> Double): Double {
    return IntegrationJavaStreams.integrate(start, end, precision, f)
}

fun main() {
    println("Starting benchmark")

    fun pow10(x: Int): Int =
            (1 until x).fold(10, { acc, _ -> acc * 10 })

    fun benchmark(precision: Int, f: IntegrationType) {
        val numberOfRepetitions = 5
        fun testFunction(@Suppress("UNUSED_PARAMETER") x: Double): Double = 1.0
        System.gc()
        val averageExecutionTimeNs = (0..numberOfRepetitions)
                .map {
                    try {
                        measureTimeMillis { f(-1.0, 1.0, precision, ::testFunction) }
                    } catch (e: Throwable) {
                        println("An Exception occured = ${e}")
                        Long.MIN_VALUE
                    }
                }
                .average()
        println("precision $precision: average $averageExecutionTimeNs")
    }

    val precisions = (4..8).map(::pow10)
    val functions = listOf(
            ::integrateImperative
//            , ::integrateFunctional
//            , ::integrateFunctionalCleanCode
            , ::integrateFunctionalSequence
//            , ::integrateFunctionalSequence2
            , ::integrateFunctionalSumBy
            , ::integrateFunctionalFold
            , ::integrateFunctionalStreams
            , ::integrateFunctionalJavaStreams
    )

    functions.forEach { f ->
        println("benchmarking function $f")
        precisions.forEach { precision ->
            benchmark(precision, f)
        }
    }
}
