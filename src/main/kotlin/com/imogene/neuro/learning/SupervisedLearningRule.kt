package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure

interface SupervisedLearningRule {

    fun apply(structure: MultiLayerStructure, example: DoubleArray, answer: DoubleArray)

    fun apply(structure: MultiLayerSplitStructure, example: DoubleArray, answer: DoubleArray)
}