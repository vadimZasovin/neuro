package com.imogene.neuro

import java.util.*

fun LayerStructure.initMemory(inputsCount: Int,
                                       init: (neuronIndex: Int,
                                              weightIndex: Int) -> Double){
    memory = LayerMemory(this.size){
        @Suppress("UnnecessaryVariable")
        val neuronIndex = it
        NeuronMemory(inputsCount){
            init(neuronIndex, it)
        }
    }
}

fun LayerStructure.initMemory(inputsCount: Int){
    val random = Random()
    initMemory(inputsCount){ _, _ ->
        getRandomValue(random)
    }
}

private fun getRandomValue(random: Random) : Double {
    var rv = random.nextDouble()
    val sign = if(rv > 0.5) 1 else -1
    rv = random.nextDouble()
    var result = sign * rv / 10
    if(result == 0.0){
        result = getRandomValue(random)
    }
    return result
}

fun LayerStructure.initBiases(init: (neuronIndex: Int) -> Double){
    biases = LayerBiases(size){
        init(it)
    }
}

fun LayerStructure.initBiases(){
    val random = Random()
    initBiases {
        getRandomValue(random)
    }
}

fun MultiLayerStructure.initMemory(init: (layerIndex: Int,
                                          neuronIndex: Int,
                                          weightIndex: Int) -> Double){
    inputLayer.initMemory(1){
        neuronIndex, weightIndex ->
        init(0, neuronIndex, weightIndex)
    }
    var layerSize = inputLayer.size
    (1 until layersCount)
            .asSequence()
            .map { this[it] }
            .forEachIndexed { layerIndex, layer ->
                layer.initMemory(layerSize){ neuronIndex, weightIndex ->
                    init(layerIndex, neuronIndex, weightIndex)
                }
                layerSize = layer.size
            }
}

fun MultiLayerStructure.initMemory(){
    val random = Random()
    initMemory { _, _, _ ->
        getRandomValue(random)
    }
}

fun MultiLayerStructure.initBiases(init: (layerIndex: Int,
                                          neuronIndex: Int) -> Double){
    (1 until layersCount)
            .asSequence()
            .map { this[it] }
            .forEachIndexed { layerIndex, layer ->
                layer.initBiases {
                    init(layerIndex, it)
                }
            }
}

fun MultiLayerStructure.initBiases(){
    val random = Random()
    initBiases { _, _ ->
        getRandomValue(random)
    }
}

fun MultiLayerSplitStructure.initMemory(init: (structureIndex: Int,
                                               layerIndex: Int,
                                               neuronIndex: Int,
                                               weightIndex: Int) -> Double){
    structures.forEachIndexed { structureIndex, structure ->
        structure.initMemory { layerIndex, neuronIndex, weightIndex ->
            init(structureIndex, layerIndex, neuronIndex, weightIndex)
        }
    }
}

fun MultiLayerSplitStructure.initMemory(){
    val random = Random()
    initMemory { _, _, _, _ ->
        getRandomValue(random)
    }
}

fun MultiLayerSplitStructure.initBiases(init: (structureIndex: Int,
                                               layerIndex: Int,
                                               neuronIndex: Int) -> Double){
    structures.forEachIndexed { structureIndex, structure ->
        structure.initBiases { layerIndex, neuronIndex ->
            init(structureIndex, layerIndex, neuronIndex)
        }
    }
}

fun MultiLayerSplitStructure.initBiases(){
    val random = Random()
    initBiases { _, _, _ ->
        getRandomValue(random)
    }
}