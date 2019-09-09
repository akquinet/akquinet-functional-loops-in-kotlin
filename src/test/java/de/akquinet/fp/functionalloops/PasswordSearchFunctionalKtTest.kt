package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordSearchFunctionalKtTest {

    @Test
    fun testDictionaryAttackFunctional() {
        val correctPassword = "b3c"
        val checkPassword =
                { password : String -> password == correctPassword }
        val password = attackFunctional(checkPassword)
        assertEquals(correctPassword, password)
    }

    @Test
    fun testPasswordPlusOne() {
        val second = SeedPassword.plusOne()
        assertEquals(Password(listOf(LetterElem('b'))), second)

        val third = second.plusOne()
        assertEquals(Password(listOf(LetterElem('c'))), third)

        val twentySeventh = third.plus(24)
        assertEquals(Password(listOf(LetterElem('a'), DigitElem('0'))), twentySeventh)
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
        assertEquals(password1, password1)
        assertEquals(password1, password2)

    }
}
