package com.imogene.neuro.impl

import com.imogene.neuro.*

internal open class MultiLayerSplitStructureImpl(final override val structures: Array<MultiLayerStructure>)
    : MultiLayerSplitStructure {

    init {
        if(structuresCount < 2){
            throw IllegalArgumentException("Split structure must have at least two inner structures.")
        }
        structures.forEach {
            if(it.outputLayer.size != 1){
                throw IllegalArgumentException("Each inner structure's output layer must have a single neuron.")
            }
        }
    }

    override val size get() = structures.sumBy { it.size }

    override var memory: MultiLayerSplitMemory
        get() = MultiLayerSplitMemory(structuresCount){
            structures[it].memory
        }
        set(value) {
            if(value.size != structuresCount){
                throw IllegalArgumentException("Size of the specified memory (${value.size}) " +
                        "is not equal to the number of inner structures ($structuresCount)")
            }
            value.forEachIndexed { index, memory ->
                structures[index].memory = memory
            }
        }

    override var biases: MultiLayerSplitBiases
        get() = MultiLayerSplitBiases(structuresCount){
            structures[it].biases
        }
        set(value) {
            if(value.size != structuresCount){
                throw IllegalArgumentException("Size of the specified biases array (${value.size}) " +
                        "is not equal to the number of inner structures ($structuresCount)")
            }
            value.forEachIndexed { index, biases ->
                structures[index].biases = biases
            }
        }

    override fun signal(inputs: DoubleArray) = DoubleArray(structuresCount){
        this[it].signal(inputs)[0]
    }
}