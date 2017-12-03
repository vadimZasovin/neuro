package com.imogene.neuro.learning

import com.imogene.neuro.*
import com.imogene.neuro.impl.ClassificationNetImpl
import com.imogene.neuro.impl.MultiLayerNetImpl

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

open class UnsupervisedLearningManager internal constructor(
        private val rule: UnsupervisedLearningRule, structure: Structure) {

    private val inputLayerSize : Int = if(structure is MultiLayerStructure){
        structure.inputLayer.size
    }else{
        (structure as MultiLayerSplitStructure)[0][0].size
    }

    init {
        rule.initialize(structure)
    }

    private var _averageWeightsChange : Double = -1.0

    val averageWeightsChange get() = _averageWeightsChange

    fun example(example: DoubleArray){
        if(example.size != inputLayerSize){
            throw IllegalArgumentException(
                    "The size of the specified " +
                    "example array (${example.size}) " +
                    "is not equal to size of the " +
                    "input layer ($inputLayerSize).")
        }
        _averageWeightsChange = rule.apply(example)
    }
}

class TaskSolverUnsupervisedLearningManager internal constructor(
        rule: UnsupervisedLearningRule, structure: Structure,
        private val normalizers : Map<MutableRange, Normalizer<*>>?,
        private val nominalVariables : Map<Int, NominalVariable<*>>?) :
        UnsupervisedLearningManager(rule, structure) {

    fun example(build: TaskBuilder.() -> Unit){
        val builder = TaskBuilder(normalizers, nominalVariables)
        builder.build()
        example(builder.inputs)
    }
}