package de.akquinet.fp.functionalloops

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PasswordSearchFunctionalKtTest {

    @Test
    fun testDictionaryAttackFunctional() {
        val correctPassword = "1a2b3c"
        val checkPassword =
                { password : String -> password.equals(correctPassword) }
        val dictionary = createLettersAndNumbersDictionaryFunctional()
        val password = dictionaryAttackFunctional(dictionary, checkPassword)
        Assertions.assertEquals(correctPassword, password)
    }


    @Test
    fun testCreateLettersAndNumbersDictionary() {
        val dictionary = createLettersAndNumbersDictionaryFunctional()
        val passwordList = dictionary
                .take(100)
                .toList()
        Assertions.assertEquals("a", passwordList[0])
        Assertions.assertEquals("b", passwordList[1])
        Assertions.assertEquals("z", passwordList[25])
        Assertions.assertEquals("0a", passwordList[26])
        Assertions.assertEquals("9a", passwordList[35])
        Assertions.assertEquals("0b", passwordList[36])
    }

}
