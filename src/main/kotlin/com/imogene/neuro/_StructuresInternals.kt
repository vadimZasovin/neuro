package com.imogene.neuro

internal fun LayerStructure.prepareMemory(size: Int){
    memory = LayerMemory(this.size){
        NeuronMemory(size){
            1.0
            // TODO implement random filling
        }
    }
}

internal fun LayerStructure.prepareBiases(){
    biases = DoubleArray(size){
        1.0
        // TODO implement random filling
    }
}

internal fun MultiLayerStructure.prepareMemory(){
    var layerSize = inputLayer.size
    for(i in 1 until layersCount){
        val layer = layers[i]
        layer.prepareMemory(layerSize)
        layerSize = layer.size
    }
}

internal fun MultiLayerStructure.prepareBiases() = layers.forEach { it.prepareBiases() }