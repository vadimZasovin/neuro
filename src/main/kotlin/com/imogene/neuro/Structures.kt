package com.imogene.neuro

interface Structure {

    val size : Int

    fun signal(inputs: DoubleArray) : DoubleArray
}

interface LayerStructure : Structure {

    val neurons : Array<Neuron>

    var memory : LayerMemory

    var biases : LayerBiases
}

interface MultiLayerStructure : Structure {

    val layers : Array<LayerStructure>

    var memory : MultiLayerMemory

    var biases : MultiLayerBiases
}

interface MultiLayerSplitStructure : Structure {

    val structures : Array<MultiLayerStructure>

    var memory : MultiLayerSplitMemory

    var biases : MultiLayerSplitBiases
}