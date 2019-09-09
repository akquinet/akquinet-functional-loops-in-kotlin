package de.akquinet.fp.functionalloops

import kotlin.system.measureTimeMillis


fun main() {
    println("Starting password benchmark")

    fun benchmark(f: ((String) -> Boolean) -> String?, checker: (String) -> Boolean) {
        val numberOfRepetitions = 2
        System.gc()
        val averageExecutionTimeNs = (0..numberOfRepetitions)
            .map {
                try {
                    measureTimeMillis { f(checker) }
                } catch (e: Throwable) {
                    println("An Exception occured = ${e}")
                    Long.MIN_VALUE
                }
            }
            .average()
        println("average $averageExecutionTimeNs")
    }

    val passwordChecks = listOf<(String) -> Boolean>(
        { password: String -> ("0a" == password) }
        , { password: String -> ("0a0a" == password) }
      //  , { password: String -> ("0a0a0a" == password) }
        , { password: String -> ("0a0a0a0a" == password) }
    )
    val attackFunctions = listOf(
        ::attackImperative
        //, ::attackFunctionalSlow
        //, ::attackFunctional
    )

    attackFunctions.forEach { f ->
        println("benchmarking function $f")
        passwordChecks.forEach { checker ->
            benchmark(f, checker)
        }
    }
}
