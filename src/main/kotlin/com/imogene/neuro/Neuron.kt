package com.imogene.neuro

class Neuron(val aggregationFunction: AggregationFunction = aggregationFunctionSum(),
             val transferFunction: TransferFunction = transferFunctionLogistic()){

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

    var bias : Bias = 0.0

    fun aggregate(inputs: DoubleArray) : Double {
        if(inputs.size != memory.size){
            throw IllegalStateException("The number of inputs (${inputs.size}) " +
                    "is not equal to memory size (${memory.size}).")
        }
        return aggregationFunction(inputs, memory, bias)
    }

    fun transfer(value: Double) = transferFunction.transfer(value)

    fun signal(inputs: DoubleArray) = transfer(aggregate(inputs))
}