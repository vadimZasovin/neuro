package com.imogene.neuro

operator fun LayerStructure.get(index: Int) = neurons[index]


val MultiLayerStructure.layersCount get() = layers.size

val MultiLayerStructure.inputLayer get() = layers.first()

val MultiLayerStructure.outputLayer get() = layers.last()

operator fun MultiLayerStructure.get(index: Int) = layers[index]

operator fun MultiLayerStructure.get(layerIndex: Int, neuronIndex: Int) = this[layerIndex][neuronIndex]

val MultiLayerStructure.hiddenLayersCount get() = layersCount - 2


val MultiLayerSplitStructure.structuresCount get() = structures.size

val MultiLayerSplitStructure.layersCount get() = structures.sumBy { it.layersCount }

operator fun MultiLayerSplitStructure.get(index: Int) = structures[index]

operator fun MultiLayerSplitStructure.get(structureIndex: Int, layerIndex: Int) = this[structureIndex][layerIndex]

operator fun MultiLayerSplitStructure.get(structureIndex: Int, layerIndex: Int, neuronIndex: Int) =
        this[structureIndex][layerIndex][neuronIndex]