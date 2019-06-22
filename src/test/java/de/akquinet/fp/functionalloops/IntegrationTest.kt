package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class IntegrationTest {

    @Test
    fun integrateImperative() {
        val integrationF = integrateImperative
        val const1 : (Double) -> Double = { x -> 1.0}
        assertEquals(1.0, integrationF(0.0,1.0, 1000, const1))
        assertEquals(1.0, integrationF(0.0,1.0, 10000, const1))
        assertEquals(1.0, integrationF(-1.0,1.0, 100, const1))
    }
}
