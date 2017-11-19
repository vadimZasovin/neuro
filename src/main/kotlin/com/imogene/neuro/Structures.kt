package com.imogene.neuro

interface Structure {

    val size : Int

    fun signal(inputs: DoubleArray) : DoubleArray
}

interface LayerStructure : Structure {

    val neurons : List<Neuron>

    var memory : LayerMemory

    var biases : LayerBiases

    fun copy() : LayerStructure
}

interface MultiLayerStructure : Structure {

    val layers : List<LayerStructure>

    var memory : MultiLayerMemory

    var biases : MultiLayerBiases

    fun split() : MultiLayerSplitStructure
}

interface MultiLayerSplitStructure : Structure {

    val structures : List<MultiLayerStructure>

    var memory : MultiLayerSplitMemory

    var biases : MultiLayerSplitBiases
}