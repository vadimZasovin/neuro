package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure

interface UnsupervisedLearningRule {

    fun apply(structure: MultiLayerStructure, example: DoubleArray)

    fun apply(structure: MultiLayerSplitStructure, example: DoubleArray)
}