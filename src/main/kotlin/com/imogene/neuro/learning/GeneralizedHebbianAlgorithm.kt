package com.imogene.neuro.learning

import com.imogene.neuro.*

class GeneralizedHebbianAlgorithm : UnsupervisedLearningRule {

    private var memoryInitialized = false

    override fun apply(structure: MultiLayerStructure, example: DoubleArray) {
        if(!memoryInitialized){
            structure.initMemory()
            structure.initBiases()
            memoryInitialized = true
        }

        val inputLayer = structure.inputLayer
        val normalized = inputLayer.signal(example)
        
        (1 until structure.layersCount)
                .asSequence()
                .map { structure[it] }
                .forEach {

                }
    }

    override fun apply(structure: MultiLayerSplitStructure, example: DoubleArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}