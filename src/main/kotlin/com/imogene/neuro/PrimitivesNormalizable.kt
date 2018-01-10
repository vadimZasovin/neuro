package com.imogene.neuro

internal fun Double.normalize() = Math.tanh(0.5 * this)

internal fun Float.normalize() = toDouble().normalize()

internal fun Long.normalize() = toDouble().normalize()

internal fun Int.normalize() = toDouble().normalize()

internal fun Short.normalize() = toDouble().normalize()

internal fun Byte.normalize() = toDouble().normalize()

internal fun Boolean.normalize() = if(this) 1.0 else -1.0