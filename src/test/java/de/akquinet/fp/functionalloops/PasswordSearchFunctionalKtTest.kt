package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordSearchFunctionalKtTest {

    @Test
    fun testDictionaryAttackFunctional() {
        val correctPassword = "1a2b3c"
        val checkPassword =
                { password : String -> password.equals(correctPassword) }
        val dictionary = createLettersAndNumbersDictionaryFunctional()
        val password = dictionaryAttackFunctional(dictionary, checkPassword)
        assertEquals(correctPassword, password)
    }

    @Test
    fun testComputeNextPassword() {
        assertEquals("b", computeNextPassword("a"))
        assertEquals("0a", computeNextPassword("z"))
        assertEquals("0b", computeNextPassword("0a"))
        assertEquals("1a", computeNextPassword("0z"))
    }
}
