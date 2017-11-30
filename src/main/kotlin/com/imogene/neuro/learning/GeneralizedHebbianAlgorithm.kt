package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure
import com.imogene.neuro.initBiases
import com.imogene.neuro.initMemory

class GeneralizedHebbianAlgorithm : LearningRule {

    override fun apply(structure: MultiLayerStructure) {
        structure.initMemory()
        structure.initBiases()
    }

    override fun apply(structure: MultiLayerSplitStructure) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}