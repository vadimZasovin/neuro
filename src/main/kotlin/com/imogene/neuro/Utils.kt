package com.imogene.neuro

internal fun <T> initList(size: Int, init: (Int) -> T) : List<T> {
    val list = ArrayList<T>(size)
    (0 until size).mapTo(list) { init(it) }
    return list
}