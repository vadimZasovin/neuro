package com.imogene.neuro

import java.util.*

internal fun LayerStructure.prepareMemory(inputsCount: Int, initializer: (Int) -> Double){
    memory = LayerMemory(this.size){
        NeuronMemory(inputsCount, initializer)
    }
}

internal fun LayerStructure.prepareMemory(inputsCount: Int){
    val random = Random()
    prepareMemory(inputsCount){
        random.nextDouble()
    }
}

internal fun LayerStructure.prepareBiases(){
    val random = Random()
    biases = LayerBiases(size){
        random.nextDouble()
    }
}

internal fun MultiLayerStructure.prepareMemory(){
    inputLayer.prepareMemory(1){ 1.0 }
    var layerSize = inputLayer.size
    (1 until layersCount)
            .asSequence()
            .map { this[it] }
            .forEach {
                it.prepareMemory(layerSize)
                layerSize = it.size
            }
}

internal fun MultiLayerStructure.prepareBiases(){
    (1 until layersCount)
            .asSequence()
            .map { this[it] }
            .forEach { it.prepareBiases() }
}