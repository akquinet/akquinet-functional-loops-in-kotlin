package de.akquinet.fp.functionalloops

// Imperative
// =======================

fun attackImperative(checkPassword: (String) -> Boolean) : String? =
        dictionaryAttackImperative(createLettersAndNumbersDictionaryImperative(), checkPassword)

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
}
// Functional 1
// =======================

fun attackFunctional(checkPassword: (String) -> Boolean) : String? =
    createLettersAndNumbersDictionaryFunctional().find(checkPassword)

fun createLettersAndNumbersDictionaryFunctional(): Sequence<String> =
    generateSequence("a", ::computeNextPassword)


data class ComputePlusOnEachElementState(
    val remainingElements: List<Char>
    , val computedElements: List<Char>
    , val isCurrentElementALetter: Boolean
    , val isOverunFromLastElement: Boolean) {
    fun isTerminated(): Boolean =
        remainingElements.isEmpty() && !isOverunFromLastElement
}

data class ComputePlusOnEachElementState2(
    val index: Int
    , val lengthOfPassword : Int
    , val currentPassword : Map<Int, Char>
    , val isCurrentElementALetter: Boolean
    , val isOverunFromLastElement: Boolean) {
    fun isTerminated(): Boolean =
        index >= lengthOfPassword

}

fun computeNextPassword(password: String): String {
    val elementsFromRightToLeft = password.toList().reversed()
    val seed = ComputePlusOnEachElementState(
        elementsFromRightToLeft,
        emptyList<Char>(),
        true,
        true
    )
    return generateSequence(seed, ::computePlusOnEachElement)
        .filter(ComputePlusOnEachElementState::isTerminated)
        //.take(password.length)
        .first()
        .computedElements
        .joinToString("")
}

fun computePlusOnEachElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState =
    if (state.isOverunFromLastElement) {
        increaseElement(state)
    } else {
        finishComputation(state)
    }

private fun increaseElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState {
    return if (state.remainingElements.isNotEmpty()) {
        increaseCurrentElement(state)
    } else {
        addNewElement(state)
    }
}

private fun finishComputation(state: ComputePlusOnEachElementState) =
    ComputePlusOnEachElementState(emptyList(), state.remainingElements.reversed().plus(
        state.computedElements), false, false)

private fun addNewElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState {
    return ComputePlusOnEachElementState(emptyList(),
        listOf(if (state.isCurrentElementALetter) 'a' else '0').plus(state.computedElements),
        false, false)
}

private fun increaseCurrentElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState {
    val element = state.remainingElements.first()
    val (nextElement, overrun) =
        if (state.isCurrentElementALetter)
            letterPlusOne(element)
        else
            digitPlusOne(element)
    return ComputePlusOnEachElementState(
        state.remainingElements.drop(1),
        listOf(nextElement).plus(state.computedElements),
        !state.isCurrentElementALetter,
        overrun)
}

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
