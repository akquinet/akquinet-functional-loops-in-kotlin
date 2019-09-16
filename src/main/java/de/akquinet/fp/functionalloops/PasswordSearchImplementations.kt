package de.akquinet.fp.functionalloops

// Imperative
// =======================

fun attackImperative(checkPassword: (String) -> Boolean): String? =
    dictionaryAttackImperative(createLettersAndNumbersDictionaryImperative(), checkPassword)

fun dictionaryAttackImperative(dictionary: Iterator<String>, checkPassword: (String) -> Boolean): String {
    var entry: String
    do {
        entry = dictionary.next()
    } while (!checkPassword(entry))
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
            val (elePlusOne, overrun) = plusOne(ele)
            nextEntryArray[index] = elePlusOne
            index++
            if (overrun && (index == nextEntry.length)) {
                val nextEle = lowestElemOnIdex(index)
                val nextEleArray = CharArray(1) { nextEle }
                nextEntryArray = nextEleArray + nextEntryArray
            }
        } while (overrun && (index < nextEntry.length))
        nextEntry = String(nextEntryArray)

        return result
    }


    companion object {
        private fun plusOne(ele: Char): Pair<Char, Boolean> =
            when {
                ele.isDigit() -> digitPlusOne(ele)
                ele.isLetter() -> letterPlusOne(ele)
                else -> throw RuntimeException("This should never happen. Ele = $ele")
            }

        private fun lowestElemOnIdex(index: Int) = if (index % 2 == 1) '0' else 'a'
    }
}

// Functional
// =======================

fun attackFunctional(checkPassword: (String) -> Boolean): String? =
    generateSequence(SeedPassword, Password::plusOne)
        .first { password -> checkPassword(password.toString()) }
        .toString()

sealed class PasswordElem(private val element: Char) {
    abstract fun plusOne(): Pair<PasswordElem, Boolean>
    override fun toString(): String {
        return element.toString()
    }

    override fun equals(other: Any?): Boolean =
        (other is PasswordElem) && (other.element == element)

    override fun hashCode(): Int = element.hashCode()
    abstract fun nextLowestElem(): List<PasswordElem>
}

class LetterElem(private val letter: Char) : PasswordElem(letter) {
    override fun nextLowestElem(): List<PasswordElem> = listOf(DigitElem('0'))

    override fun plusOne(): Pair<PasswordElem, Boolean> =
        if (letter == 'z')
            Pair(LetterElem('a'), true)
        else
            Pair(LetterElem(letter + 1), false)

}

class DigitElem(private val letter: Char) : PasswordElem(letter) {
    override fun nextLowestElem(): List<PasswordElem> = listOf(LetterElem('a'))

    override fun plusOne(): Pair<PasswordElem, Boolean> =
        if (letter == '9')
            Pair(DigitElem('0'), true)
        else
            Pair(DigitElem(letter + 1), false)
}

class Password(val elements: List<PasswordElem>) {
    fun plusOne(): Password =
        if (elements.isEmpty()) {
            SeedPassword // do I need this empty check?
        } else {
            computePasswordPlusOne(elements)
        }

    fun plus(n: Int): Password =
        (1..n).fold(this) { password: Password, _: Int -> password.plusOne() }

    override fun toString(): String =
        elements.asReversed().joinToString(separator = "")

    override fun equals(other: Any?): Boolean =
        (other is Password) && (other.elements == elements)

    override fun hashCode(): Int = elements.hashCode()

    companion object {
        private fun computePasswordPlusOne(elements: List<PasswordElem>): Password {
            val (increasedFirstElem, overflow) = elements.first().plusOne()
            val increasedFirstElemList = listOf(increasedFirstElem)
            val elementsRemainder = elements.drop(1)
            return if (overflow) {
                val remainderPlusOne =
                    if (elementsRemainder.isNotEmpty()) {
                        Password(elementsRemainder).plusOne().elements
                    } else {
                        increasedFirstElem.nextLowestElem()
                    }
                Password(increasedFirstElemList + remainderPlusOne)
            } else {
                Password(increasedFirstElemList +
                    elementsRemainder)
            }
        }
    }
}

val SeedPassword = Password(listOf(LetterElem('a')))


// Utility Function
// =======================

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
