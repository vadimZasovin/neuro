package com.imogene.neuro

typealias NetMemory = Array<LayerMemory>

class NeuralNetwork(private val layers: Array<Layer>){

    init {
        if(layers.size < 2){
            throw IllegalArgumentException("Neural network must have at least 2 layers - input layer and output layer.")
        }
    }

    var memory : NetMemory
        get() {
            return NetMemory(size){
                layers[it].memory
            }
        }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified memory is not equal to size of the net. " +
                        "Size of the specified memory is ${value.size}, size of the net is $size.")
            }
            value.forEachIndexed { index, layerMemory ->
                layers[index].memory = layerMemory
            }
        }

    fun updateMemory(update: (layerIndex: Int, neuronIndex: Int, memoryIndex: Int) -> Double){
        layers.forEachIndexed { index, layer ->
            layer.updateMemory { neuronIndex, memoryIndex ->
                update(index, neuronIndex, memoryIndex)
            }
        }
    }

    internal fun prepareMemory(){
        var layerSize = inputLayer.size
        for(i in 1 until size){
            val layer = layers[i]
            layer.prepareMemory(layerSize)
            layerSize = layer.size
        }
    }

    var biases : Array<DoubleArray>
        get() {
            return Array(size){
                layers[it].biases
            }
        }
        set(value) {
            if(value.size != size){
                throw IllegalArgumentException("Size of the specified biases array is not equal to size of the net. " +
                        "Size of the specified biases array is ${value.size}, size of the net is $size.")
            }
            value.forEachIndexed { index, biases ->
                layers[index].biases = biases
            }
        }

    val size
        get() = layers.size

    val totalSize : Int
        get() {
            var totalSize = 0
            layers.forEach { totalSize += it.size}
            return totalSize
        }

    val inputLayer
        get() = layers.first()

    val outputLayer
        get() = layers.last()

    fun getLayerAt(index: Int) = layers[index]

    val hiddenLayersCount
        get() = layers.size - 2

    fun getNeuronAt(layerIndex: Int, neuronIndex: Int) = layers[layerIndex].neurons[neuronIndex]

    fun solve(inputs: DoubleArray) : DoubleArray {
        val size = inputs.size
        if(size != inputLayer.size){
            throw IllegalArgumentException("The size of the inputs array ($size) is not equal " +
                    "to the size of the input layer (${inputLayer.size}).")
        }
        var result = DoubleArray(size){
            val neuron = inputLayer.getNeuronAt(it)
            val input = inputs[it]
            neuron.signal(doubleArrayOf(input))
        }
        (1 until this.size)
                .asSequence()
                .map { getLayerAt(it) }
                .forEach { result = it.signal(result) }
        return result
    }

    internal var normalizers : Map<MutableRange, Normalizer<*>>? = null

    fun newTask() = Task(normalizers)

    fun solve(task: Task) : DoubleArray {
        return solve(task.inputs)
    }

    companion object {

        fun startBuilding() : InputLayerBuilder = NeuralNetworkBuilder()
    }
}