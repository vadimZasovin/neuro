package com.imogene.neuro

import com.imogene.neuro.impl.MultiLayerNetImpl

class Neuro private constructor(){

    companion object {

        fun create(layers: Array<LayerStructure>) : MultiLayerNet = MultiLayerNetImpl(layers)
    }
}