package com.imogene.neuro

object AggregationFunctions {

    fun sum() = object : AggregationFunction {
        override fun aggregate(inputs: DoubleArray, memory: NeuronMemory, bias: Double): Double {
            var result = 0.0
            inputs.forEachIndexed { i, input ->
                val weight = memory[i]
                result += input * weight
            }
            result += bias
            return result
        }
    }
}