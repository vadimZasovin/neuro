package com.imogene.neuro

typealias AggregationFunction = (inputs: DoubleArray, memory: NeuronMemory, bias: Double) -> Double

fun aggregationFunctionSum() : AggregationFunction = { inputs, memory, bias ->
    var result = 0.0
    for ((index, input) in inputs.withIndex()){
        val weight = memory[index]
        result += input * weight
    }
    result += bias
    result
}

typealias TransferFunction = (Double) -> Double

fun transferFunctionEmpty() : TransferFunction = { it }

fun transferFunctionLinear(k: Double) : TransferFunction = { k * it }

fun transferFunctionSemiLinear(k: Double) : TransferFunction = {
    if(it < 0){
        0.0
    }else{
        k * it
    }
}

fun transferFunctionWithSaturation(range: ClosedRange<Double>) : TransferFunction = {
    when {
        it in range -> it
        it < range.start -> range.start
        else -> range.endInclusive
    }
}

fun transferFunctionWithThreshold(threshold: Long) : TransferFunction = {
    if(it >= threshold) 1.0 else 0.0
}

fun transferFunctionLogistic(k: Double = 0.5) : TransferFunction = {
    1 / (1 + Math.exp(-k * it))
}

fun transferFunctionHyperbolicTangent(k: Double = 0.5) : TransferFunction = {
    Math.tanh(k * it)
}