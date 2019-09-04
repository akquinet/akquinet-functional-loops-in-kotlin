package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class PasswordSearchFunctionalKtTest {

    @Test
    fun testDictionaryAttackFunctional() {
        val correctPassword = "b3c"
        val checkPassword =
                { password : String -> password.equals(correctPassword) }
        val password = attackFunctional(checkPassword)
        assertEquals(correctPassword, password)
    }

    @Test
    fun testComputeNextPassword() {
        assertEquals("b", computeNextPassword("a"))
        assertEquals("0a", computeNextPassword("z"))
        assertEquals("0b", computeNextPassword("0a"))
        assertEquals("1a", computeNextPassword("0z"))
    }

    @Test
    fun testPasswordPlusOne() {
        val second = SeedPassword.plusOne()
        assertEquals(Password(listOf(LetterElem('b'))), second)
    }

    @Test
    fun testPasswordAsString() {
        assertEquals("a", SeedPassword.toString())
        assertEquals("0a", Password(listOf(LetterElem('a'), DigitElem('0'))).toString())
    }

    @Test
    fun testPasswordEquals() {
        val password1 = Password(listOf(LetterElem('b')))
        val password2 = Password(listOf(LetterElem('b')))
        assertTrue(password1.equals(password1))
        assertTrue(password1.equals(password2))

    }
}
