package com.imogene.neuro

operator fun LayerStructure.get(index: Int) = neurons[index]

operator fun LayerStructure.set(index: Int, neuron: Neuron){
    neurons[index] = neuron
}


val MultiLayerStructure.layersCount get() = layers.size

val MultiLayerStructure.inputLayer get() = layers.first()

val MultiLayerStructure.outputLayer get() = layers.last()

operator fun MultiLayerStructure.get(index: Int) = layers[index]

operator fun MultiLayerStructure.get(layerIndex: Int, neuronIndex: Int) = this[layerIndex][neuronIndex]

operator fun MultiLayerStructure.set(index: Int, structure: LayerStructure){
    layers[index] = structure
}

operator fun MultiLayerStructure.set(layerIndex: Int, neuronIndex: Int, neuron: Neuron){
    this[layerIndex][neuronIndex] = neuron
}

val MultiLayerStructure.hiddenLayersCount get() = layersCount - 2


val MultiLayerSplitStructure.structuresCount get() = structures.size

val MultiLayerSplitStructure.layersCount get() = structures.sumBy { it.layersCount }

operator fun MultiLayerSplitStructure.get(index: Int) = structures[index]

operator fun MultiLayerSplitStructure.get(structureIndex: Int, layerIndex: Int) = this[structureIndex][layerIndex]

operator fun MultiLayerSplitStructure.get(structureIndex: Int, layerIndex: Int, neuronIndex: Int) =
        this[structureIndex][layerIndex][neuronIndex]

operator fun MultiLayerSplitStructure.set(index: Int, structure: MultiLayerStructure){
    this[index] = structure
}

operator fun MultiLayerSplitStructure.set(structureIndex: Int, layerIndex: Int, layerStructure: LayerStructure){
    this[structureIndex][layerIndex] = layerStructure
}

operator fun MultiLayerSplitStructure.set(structureIndex: Int, layerIndex: Int, neuronIndex: Int, neuron: Neuron){
    this[structureIndex][layerIndex][neuronIndex] = neuron
}