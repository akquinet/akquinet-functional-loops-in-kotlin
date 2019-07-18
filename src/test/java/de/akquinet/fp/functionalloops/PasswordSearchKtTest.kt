package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordSearchKtTest {

    @Test
    fun testDictionaryAttackPositiveImperative() {
        val correctPassword = "1a2b3c"
        val checkPassword =
                { password : String -> password.equals(password) }
        val dictionary = createLettersAndNumbersDictionary()
        val password = dictionaryAttackImperative(dictionary, checkPassword)
        assertEquals(correctPassword, password)
    }

    @Test
    fun testCreateLettersAndNumbersDictionary() {
        val dictionary = createLettersAndNumbersDictionary()
                .asSequence()
        val passwordList = dictionary
                .take(100)
                .toList()
        assertEquals("a", passwordList[0])
        assertEquals("b", passwordList[1])
        assertEquals("z", passwordList[25])
        assertEquals("a0", passwordList[26])
        assertEquals("a9", passwordList[35])
        assertEquals("b0", passwordList[36])
    }
}
