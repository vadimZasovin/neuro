package com.imogene.neuro

typealias NeuronMemory = DoubleArray

typealias LayerMemory = Array<NeuronMemory>

typealias MultiLayerMemory = Array<LayerMemory>

typealias MultiLayerSplitMemory = Array<MultiLayerMemory>


typealias Bias = Double

typealias LayerBiases = DoubleArray

typealias MultiLayerBiases = Array<LayerBiases>

typealias MultiLayerSplitBiases = Array<MultiLayerBiases>