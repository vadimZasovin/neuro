package com.imogene.neuro

typealias NeuronMemory = DoubleArray

class Neuron(private val aggregationFunction: AggregationFunction = aggregationFunctionSum(),
             private val transferFunction: TransferFunction = transferFunctionLogistic()){

    constructor(aggregationFunction: AggregationFunction = aggregationFunctionSum(),
                transferFunction: TransferFunction = transferFunctionLogistic(),
                size: Int, memoryInitializer: (Int) -> Double = {0.0}) : this(aggregationFunction, transferFunction){
        memory = NeuronMemory(size, memoryInitializer)
    }

    private var _memory : NeuronMemory? = null

    var memory : NeuronMemory
        get() {
            if(_memory == null){
                throw IllegalStateException("Memory is not initialized")
            }
            return _memory!!
        }
        set(value) {
            _memory = value
        }


    var bias = 0.0

    fun signal(inputs: DoubleArray) : Double{
        if(inputs.size != memory.size){
            throw IllegalStateException("The number of inputs must be equal to memory size. " +
                    "Number of inputs is ${inputs.size}, memory size is ${memory.size}.")
        }

        val excitation = aggregationFunction(inputs, memory, bias)
        return transferFunction(excitation)
    }
}