package com.imogene.neuro

typealias LayerMemory = Array<NeuronMemory>

class Layer(internal val neurons: Array<Neuron>) {

    constructor(size: Int, layerInitializer: (Int) -> Neuron) : this(Array(size, layerInitializer))

    constructor(size: Int,
                aggregationFunction: AggregationFunction = aggregationFunctionSum(),
                transferFunction: TransferFunction = transferFunctionLogistic())
            : this(size = size, layerInitializer = { Neuron(aggregationFunction, transferFunction) })

    init {
        if(neurons.isEmpty()){
            throw IllegalArgumentException("The layer mast not be empty")
        }
    }

    var memory : LayerMemory
        get() {
            return LayerMemory(size){
                neurons[it].memory
            }
        }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified memory is not equal to size of the layer. " +
                        "Size of the specified memory is ${value.size}, layer size is $size.")
            }
            value.forEachIndexed { index, neuronMemory ->
                neurons[index].memory = neuronMemory
            }
        }

    fun updateMemory(update: (neuronIndex: Int, memoryIndex: Int) -> Double){
        neurons.forEachIndexed { neuronIndex, neuron ->
            val neuronMemory = neuron.memory
            neuronMemory.forEachIndexed { memoryIndex, _ ->
                neuronMemory[memoryIndex] = update(neuronIndex, memoryIndex)
            }
        }
    }

    internal fun prepareMemory(size: Int){
        memory = LayerMemory(this.size){
            NeuronMemory(size)
        }
    }

    var biases : DoubleArray
        get() {
            return DoubleArray(size){
                neurons[it].bias
            }
        }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified biases array is not equal to size of the layer. " +
                        "Size of the specified biases array is ${value.size}, size of the layer is $size.")
            }
            value.forEachIndexed { index, bias ->
                neurons[index].bias = bias
            }
        }

    val size
        get() = neurons.size

    fun getNeuronAt(index: Int) = neurons[index]

    fun signal(inputs: DoubleArray) : DoubleArray{
        return DoubleArray(size){
            val neuron = neurons[it]
            neuron.signal(inputs)
        }
    }
}