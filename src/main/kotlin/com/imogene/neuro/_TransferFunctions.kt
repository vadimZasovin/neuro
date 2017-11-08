package com.imogene.neuro

fun TransferFunction.transfer(value: Double) = invoke(value)

val TransferFunction.max get() = transfer(Double.MAX_VALUE)

val TransferFunction.min get() = transfer(-Double.MAX_VALUE)

val TransferFunction.range get() = max - min

internal fun TransferFunction.check(){

}