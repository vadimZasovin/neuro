package com.imogene.neuro

abstract class TransferFunction(val min: Double, val max: Double) {

    constructor() : this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)

    val boundedBelow get() = min != Double.NEGATIVE_INFINITY

    val boundedAbove get() = max != Double.POSITIVE_INFINITY

    val bounded = boundedBelow && boundedAbove

    abstract fun transfer(value: Double) : Double
}