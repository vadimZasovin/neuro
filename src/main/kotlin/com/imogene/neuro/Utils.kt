package com.imogene.neuro

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

internal fun <T> initList(size: Int, init: (Int) -> T) : List<T> {
    val list = ArrayList<T>(size)
    (0 until size).mapTo(list) { init(it) }
    return list
}

internal object CustomDelegates {

    fun <T> property(prop: KMutableProperty0<T>) = object : ReadWriteProperty<Any?, T> {

        override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            prop.set(value)
        }

        override operator fun getValue(thisRef: Any?, property: KProperty<*>) = prop.get()
    }
}