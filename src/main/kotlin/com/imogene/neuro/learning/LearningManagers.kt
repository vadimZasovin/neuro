package com.imogene.neuro.learning

import com.imogene.neuro.MultiLayerSplitStructure
import com.imogene.neuro.MultiLayerStructure
import com.imogene.neuro.inputLayer

open class SupervisedLearningManager {

    internal val rule : SupervisedLearningRule

    internal lateinit var structure : MultiLayerStructure

    internal lateinit var splitStructure : MultiLayerSplitStructure

    internal constructor(rule: SupervisedLearningRule, structure: MultiLayerStructure){
        this.rule = rule
        this.structure = structure
    }

    internal constructor(rule: SupervisedLearningRule, structure: MultiLayerSplitStructure){
        this.rule = rule
        splitStructure = structure
    }

    fun example(example: DoubleArray, answer: DoubleArray){
        if(this::structure.isInitialized){
            rule.apply(structure, example, answer)
        }else{
            rule.apply(splitStructure, example, answer)
        }
    }
}

open class TaskSolverSupervisedLearningManager : SupervisedLearningManager {

    internal constructor(rule: SupervisedLearningRule, structure: MultiLayerStructure) : super(rule, structure)

    internal constructor(rule: SupervisedLearningRule, structure: MultiLayerSplitStructure) : super(rule, structure)
}

open class UnsupervisedLearningManager {

    internal val rule : UnsupervisedLearningRule

    internal lateinit var structure : MultiLayerStructure

    internal lateinit var splitStructure : MultiLayerSplitStructure

    internal constructor(rule: UnsupervisedLearningRule, structure: MultiLayerStructure){
        this.rule = rule
        this.structure = structure
    }

    internal constructor(rule: UnsupervisedLearningRule, structure: MultiLayerSplitStructure){
        this.rule = rule
        splitStructure = structure
    }

    fun example(example: DoubleArray){
        if(this::structure.isInitialized){
            checkExampleSize(example)
            rule.apply(structure, example)
        }else{
            checkExampleSizeForSplit(example)
            rule.apply(structure, example)
        }
    }

    private fun checkExampleSize(example: DoubleArray){
        checkExampleSize(structure, example)
    }

    private fun checkExampleSize(structure: MultiLayerStructure, example: DoubleArray){
        val exampleSize = example.size
        val inputLayer = structure.inputLayer
        val inputLayerSize = inputLayer.size
        if(exampleSize != inputLayerSize){
            throw IllegalArgumentException("The size of " +
                    "the example array ($exampleSize) is " +
                    "not equal to input layer size " +
                    "($inputLayerSize)")
        }
    }

    private fun checkExampleSizeForSplit(example: DoubleArray){
        splitStructure.structures.forEach{
            checkExampleSize(it, example)
        }
    }
}

class TaskSolverUnsupervisedLearningManager : UnsupervisedLearningManager {

    internal constructor(rule: UnsupervisedLearningRule, structure: MultiLayerStructure) : super(rule, structure)

    internal constructor(rule: UnsupervisedLearningRule, structure: MultiLayerSplitStructure) : super(rule, structure)
}