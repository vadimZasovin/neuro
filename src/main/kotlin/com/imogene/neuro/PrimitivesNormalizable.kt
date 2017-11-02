package com.imogene.neuro

fun Double.normalize() = Math.tanh(0.5 * this)

fun Float.normalize() = toDouble().normalize()

fun Long.normalize() = toDouble().normalize()

fun Int.normalize() = toDouble().normalize()

fun Short.normalize() = toDouble().normalize()

fun Byte.normalize() = toDouble().normalize()