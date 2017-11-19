package com.imogene.neuro

class Layer(override val neurons: List<Neuron>) : LayerStructure {

    constructor(size: Int, layerInitializer: (Int) -> Neuron) : this(initList(size, layerInitializer))

    constructor(size: Int, aggregationFunction: AggregationFunction, transferFunction: TransferFunction)
            : this(size = size, layerInitializer = { Neuron(aggregationFunction, transferFunction) })

    init {
        if(neurons.isEmpty()){
            throw IllegalArgumentException("The layer must not be empty.")
        }
    }

    override var memory : LayerMemory
        get() = LayerMemory(size){ neurons[it].memory }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified memory (${value.size}) " +
                        "is not equal to size of the layer ($size).")
            }
            value.forEachIndexed { index, neuronMemory ->
                neurons[index].memory = neuronMemory
            }
        }

    override var biases : LayerBiases
        get() = LayerBiases(size){ neurons[it].bias }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified biases array (${value.size}) " +
                        "is not equal to size of the layer ($size).")
            }
            value.forEachIndexed { index, bias ->
                neurons[index].bias = bias
            }
        }

    override val size get() = neurons.size

    override fun signal(inputs: DoubleArray) = DoubleArray(size){ neurons[it].signal(inputs) }

    override fun copy() = Layer(size){ neurons[it].copy() }
}