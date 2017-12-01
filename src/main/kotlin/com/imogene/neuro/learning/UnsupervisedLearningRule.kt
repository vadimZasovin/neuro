package com.imogene.neuro.learning

import com.imogene.neuro.Structure

interface UnsupervisedLearningRule {

    var learningRate : Double

    fun initialize(structure: Structure)

    fun apply(example: DoubleArray) : Double
}