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
}
