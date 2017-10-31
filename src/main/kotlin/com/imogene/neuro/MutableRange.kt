package com.imogene.neuro

internal data class MutableRange(var first: Int, var last: Int)

internal infix fun Int.to(to: Int) = MutableRange(this, to)