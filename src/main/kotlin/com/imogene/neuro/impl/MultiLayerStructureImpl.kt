package com.imogene.neuro.impl

import com.imogene.neuro.*

internal open class MultiLayerStructureImpl(
        final override val layers: List<LayerStructure>) : MultiLayerStructure {

    init {
        if(layersCount < 2){
            throw IllegalArgumentException("Neural network must have at least two layers - input layer and output layer.")
        }
    }

    override val size get() = layers.sumBy { it.size }

    override var memory : MultiLayerMemory
        get() = MultiLayerMemory(layersCount) { layers[it].memory }
        set(value) {
            if(value.size != layersCount){
                throw IllegalArgumentException("The size of the specified memory (${value.size}) " +
                        "is not equal to layers count ($layersCount).")
            }
            value.forEachIndexed { index, layerMemory ->
                layers[index].memory = layerMemory
            }
        }

    override var biases : MultiLayerBiases
        get() = MultiLayerBiases(layersCount){ layers[it].biases }
        set(value) {
            if(value.size != layersCount){
                throw IllegalArgumentException("The size of the specified biases array (${value.size}) " +
                        "is not equal to layers count ($layersCount).")
            }
            value.forEachIndexed { index, biases ->
                layers[index].biases = biases
            }
        }

    override fun signal(inputs: DoubleArray): DoubleArray {
        val size = inputs.size
        if(size != inputLayer.size){
            throw IllegalArgumentException("The size of the inputs array ($size) is not equal " +
                    "to the size of the input layer (${inputLayer.size}).")
        }
        var result = DoubleArray(size){
            val neuron = inputLayer[it]
            val input = inputs[it]
            neuron.signal(doubleArrayOf(input)) // TODO optimize for single input?
        }
        (1 until layersCount)
                .asSequence()
                .map { layers[it] }
                .forEach { result = it.signal(result) }
        return result
    }

    override fun split(): MultiLayerSplitStructure {
        val outputsCount = outputLayer.size
        if(outputsCount == 1){
            throw IllegalStateException("It is impossible to split a structure with a single neuron in the output layer.")
        }
        val structures = initList(outputsCount){
            @Suppress("UnnecessaryVariable")
            val structureIndex = it
            val layers = initList(layersCount){
                if(it == layersCount - 1){
                    val neuron = outputLayer[structureIndex].copy()
                    Layer(1, layerInitializer = { neuron })
                }else{
                    this.layers[it].copy()
                }
            }
            MultiLayerStructureImpl(layers)
        }
        return MultiLayerSplitStructureImpl(structures)
    }
}