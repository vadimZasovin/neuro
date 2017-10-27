package com.imogene.neuro

fun inputLayer(size: Int) = Layer(size, layerInitializer = { inputNeuron() })