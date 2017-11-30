package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure
import com.imogene.neuro.initMemory

interface LearningRule {

    fun apply(structure: MultiLayerStructure)

    fun apply(structure: MultiLayerSplitStructure)
}