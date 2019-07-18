package de.akquinet.fp.functionalloops


fun dictionaryAttackImperative(dictionary: Iterator<String>, checkPassword: (String) -> Boolean): String {
    return ""
}

fun createLettersAndNumbersDictionary(): Iterator<String> =
        listOf<String>().iterator()

class LettersAndNumbersDictionary : Iterator<String> {
    private var currentEntry: String = "a"

    override fun hasNext(): Boolean = true

    override fun next(): String {
        val result = currentEntry

        var overrun = false
        var index = 0
        do {
            val ele = currentEntry[index]
            val (nextEle, overrun) = when {
                        ele.isDigit() -> digitPlusOne(ele)
                        ele.isLetter() -> letterPlusOne(ele)
                        else -> throw RuntimeException("This should never happen.")
                    }
        } while (overrun)

        return result
    }

    private fun digitPlusOne(ele: Char): Pair<Char, Boolean> {
        return Pair(ele, false)
    }

    private fun letterPlusOne(ele: Char): Pair<Char, Boolean> {
        return Pair(ele, false)
    }
}
