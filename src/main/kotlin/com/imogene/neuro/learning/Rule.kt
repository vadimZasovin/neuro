package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure
import com.imogene.neuro.prepareMemory

abstract class Rule {

    abstract fun apply(structure: MultiLayerStructure)

    abstract fun apply(structure: MultiLayerSplitStructure)

    protected fun prepareMemory(structure: MultiLayerStructure) = structure.prepareMemory()
}