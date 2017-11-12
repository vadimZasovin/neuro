package com.imogene.neuro

interface AggregationFunction {

    fun aggregate(inputs: DoubleArray, memory: DoubleArray, bias: Double) : Double
}