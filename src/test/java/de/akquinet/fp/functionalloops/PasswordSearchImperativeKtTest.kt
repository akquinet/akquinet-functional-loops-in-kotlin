package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class PasswordSearchImperativeKtTest {

    @Test
    fun testDictionaryAttackPositiveImperative() {
        val correctPassword = "1a2b3c"
        val checkPassword =
                { password : String -> password == correctPassword }
        val dictionary = createLettersAndNumbersDictionaryImperative()
        val password = dictionaryAttackImperative(dictionary, checkPassword)
        assertEquals(correctPassword, password)
    }

    @Test
    fun testCreateLettersAndNumbersDictionary() {
        val dictionary = createLettersAndNumbersDictionaryImperative()
                .asSequence()
        val passwordList = dictionary
                .take(100)
                .toList()
        assertEquals("a", passwordList[0])
        assertEquals("b", passwordList[1])
        assertEquals("z", passwordList[25])
        assertEquals("0a", passwordList[26])
        assertEquals("9a", passwordList[35])
        assertEquals("0b", passwordList[36])
    }
}
