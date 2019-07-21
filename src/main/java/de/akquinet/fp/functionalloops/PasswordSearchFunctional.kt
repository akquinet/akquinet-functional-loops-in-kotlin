package de.akquinet.fp.functionalloops


fun dictionaryAttackFunctional(dictionary: Sequence<String>, checkPassword: (String) -> Boolean): String? =
    dictionary.find(checkPassword)

fun createLettersAndNumbersDictionaryFunctional(): Sequence<String> =
    sequenceOf("a", "b")

