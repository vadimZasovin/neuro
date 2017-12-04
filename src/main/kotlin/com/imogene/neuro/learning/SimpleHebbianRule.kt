package com.imogene.neuro.learning

import com.imogene.neuro.LayerStructure

class SimpleHebbianRule : HebbianRule() {

    override fun getWeightDecay(layer: LayerStructure, position: Int,
                                weightIndex: Int, inputs: DoubleArray,
                                outputs: DoubleArray) : Double = 0.0
}