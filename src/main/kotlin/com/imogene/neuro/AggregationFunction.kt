package com.imogene.neuro

interface AggregationFunction {

    fun aggregate(inputs: DoubleArray, memory: NeuronMemory, bias: Bias) : Double
}