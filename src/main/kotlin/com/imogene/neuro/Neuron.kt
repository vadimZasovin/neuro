package com.imogene.neuro

class Neuron(var aggregationFunction: AggregationFunction, var transferFunction: TransferFunction){

    constructor(aggregationFunction: AggregationFunction,
                transferFunction: TransferFunction,
                size: Int, memoryInitializer: (Int) -> Double = {0.0})
            : this(aggregationFunction, transferFunction){
        memory = NeuronMemory(size, memoryInitializer)
    }

    private var _memory : NeuronMemory? = null

    var memory : NeuronMemory
        get() = _memory ?: throw IllegalStateException("Memory is not initialized")
        set(value) {
            _memory = value
        }

    var bias : Bias = 0.0

    private fun aggregate(inputs: DoubleArray) : Double {
        if(inputs.size != memory.size){
            throw IllegalStateException("The number of inputs (${inputs.size}) " +
                    "is not equal to memory size (${memory.size}).")
        }
        return aggregationFunction.aggregate(inputs, memory, bias)
    }

    private fun transfer(value: Double) = transferFunction.transfer(value)

    fun signal(inputs: DoubleArray) = transfer(aggregate(inputs))

    internal fun copy() = Neuron(aggregationFunction, transferFunction)
}