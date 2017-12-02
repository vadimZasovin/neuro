package com.imogene.neuro

object AggregationFunctions {

    fun sum() = object : AggregationFunction {
        override fun aggregate(inputs: DoubleArray, memory: NeuronMemory, bias: Double): Double {
            var result = 0.0
            for ((index, input) in inputs.withIndex()){
                val weight = memory[index]
                result += input * weight
            }
            result += bias
            return result
        }
    }
}