package de.akquinet.fp.functionalloops


fun dictionaryAttackImperative(dictionary: Iterator<String>, checkPassword: (String) -> Boolean): String {
    var entry : String = ""
    do {
        entry = dictionary.next()
    } while(! checkPassword(entry))
    return entry
}

fun createLettersAndNumbersDictionaryImperative(): Iterator<String> =
        LettersAndNumbersDictionaryImperative()

class LettersAndNumbersDictionaryImperative : Iterator<String> {
    private var nextEntry: String = "a"

    override fun hasNext(): Boolean = true

    override fun next(): String {
        val result = nextEntry

        var nextEntryArray = nextEntry.toCharArray()
        var index = 0
        do {
            val ele = nextEntryArray[index]
            val (nextEle, overrun) = when {
                ele.isDigit() -> digitPlusOne(ele)
                ele.isLetter() -> letterPlusOne(ele)
                else -> throw RuntimeException("This should never happen. Ele = $ele")
            }
            nextEntryArray[index] = nextEle
            index++

            if (overrun && (index == nextEntry.length)) {
                val nextEle = if (index % 2 == 1) '0' else 'a'
                val nextEleArray = CharArray(1, { _ -> nextEle })
                nextEntryArray = nextEleArray + nextEntryArray
            }
        } while (overrun && (index < nextEntry.length))
        nextEntry = String(nextEntryArray);

        return result
    }

    fun digitPlusOne(ele: Char): Pair<Char, Boolean> =
            if (ele == '9')
                Pair('0', true)
            else
                Pair(ele + 1, false)

    fun letterPlusOne(ele: Char): Pair<Char, Boolean> =
            if (ele == 'z')
                Pair('a', true)
            else
                Pair(ele + 1, false)
}
