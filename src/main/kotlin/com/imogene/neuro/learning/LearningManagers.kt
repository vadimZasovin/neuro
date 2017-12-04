package com.imogene.neuro.learning

import com.imogene.neuro.*

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
        // here we assume that all the structures in the
        // split structure have the same configuration
        // (i.e. the same number of layers and neurons)
        // because an split structure can be created only
        // by calling split() method on a regular net and
        // the implementation of the split() method in
        // built in structures an networks guarantees
        // the identical configuration for it's structures.
        (structure as MultiLayerSplitStructure)[0][0].size
    }

    init {
        rule.initialize(structure)
    }

    private var _averageWeightsChange : Double = -1.0

    val averageWeightsChange get() = _averageWeightsChange

    var learningRate
        get() = rule.learningRate
        set(value){
            rule.learningRate = value
        }

    /**
     * This property is used to control whether the specified
     * examples should be stored in the internal temporal list
     * and then mixed and used as a new learning epoch via
     * [newEpoch] method.
     *
     * Disabling this will also clear all the examples, that were
     * specified previously.
     *
     * Epochs are enabled by default.
     */
    var epochsEnabled = true
        set(value) {
            if(!value){
                resetEpoch()
            }
            field = value
        }

    private lateinit var epochExamples: MutableList<DoubleArray>

    fun example(example: DoubleArray){
        if(example.size != inputLayerSize){
            throw IllegalArgumentException(
                    "The size of the specified " +
                    "example array (${example.size}) " +
                    "is not equal to size of the " +
                    "input layer ($inputLayerSize).")
        }

        if(epochsEnabled){
            ensureEpochExamples()
            epochExamples.add(example)
        }

        _averageWeightsChange = rule.apply(example)
    }

    private fun ensureEpochExamples(){
        if(!this::epochExamples.isInitialized){
            epochExamples = mutableListOf()
        }
    }

    fun examples(examples: List<DoubleArray>){
        val epochsEnabled = this.epochsEnabled
        if(epochsEnabled){
            ensureEpochExamples()
            epochExamples.addAll(examples)
            this.epochsEnabled = false
        }
        examples.forEach { example(it) }
        this.epochsEnabled = epochsEnabled
    }

    fun newEpoch(){
        if(!epochsEnabled){
            throw IllegalStateException(
                    "Epochs are not enabled " +
                    "for this learning manager.")
        }
        if(this::epochExamples.isInitialized){
            if(epochExamples.isNotEmpty()){
                epochExamples.shuffle()
                epochsEnabled = false
                examples(epochExamples)
                epochsEnabled = true
            }
        }else{
            throw IllegalStateException(
                    "Epoch examples are not specified.")
        }
    }

    fun resetEpoch(){
        if(this::epochExamples.isInitialized){
            epochExamples.clear()
        }
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