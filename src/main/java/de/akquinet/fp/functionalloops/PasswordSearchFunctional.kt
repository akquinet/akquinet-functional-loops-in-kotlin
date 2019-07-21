package de.akquinet.fp.functionalloops


fun dictionaryAttackFunctional(dictionary: Sequence<String>, checkPassword: (String) -> Boolean): String? =
        dictionary.find(checkPassword)

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

fun computePlusOnEachElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState {
    return if (state.isOverunFromLastElement) {
        if (state.remainingElements.isNotEmpty()) {
            increaseCurrentElement(state)
        } else {
            // add new Element - todo
            ComputePlusOnEachElementState(emptyList(),
                    listOf(if (state.isCurrentElementALetter) 'a' else '0').plus(state.computedElements),
                    false, false)
        }
    } else {
        ComputePlusOnEachElementState(emptyList(), state.remainingElements.reversed().plus(
                state.computedElements), false, false)
    }
}

private fun increaseCurrentElement(state: ComputePlusOnEachElementState): ComputePlusOnEachElementState {
    val element = state.remainingElements.first()
    val (nextElement, overrun) =
            if (state.isCurrentElementALetter) letterPlusOne(element) else digitPlusOne(element)
    // add new Element
    return ComputePlusOnEachElementState(
            state.remainingElements.drop(1),
            listOf(nextElement).plus(state.computedElements),
            !state.isCurrentElementALetter,
            overrun)
}
/*


    val (nextRemainingElements, nextComputedElements) =
            if (overrun) {
                val isLastElement = remainingElements.size > 1
                if (isLastElement)
                    Pair(
                            remainingElements.drop(1),
                            listOf(nextElement).plus(computedElements))
                else
                    Pair(
                            emptyList<Char>(),
                            listOf(if (isLetter) '0' else 'a', nextElement).plus(computedElements))
            } else
                Pair(
                        emptyList<Char>(),
                        remainingElements.reversed().plus(
                                computedElements)
                )
    return Triple(nextRemainingElements, nextComputedElements, !isLetter)

 */


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
