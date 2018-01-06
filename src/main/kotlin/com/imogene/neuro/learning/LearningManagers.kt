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
     * examples (via [example] and [examples] methods) should
     * be stored in the internal temporal list. This examples
     * then can be used as a new learning epoch via [repeat]
     * method.
     *
     * Disabling this will also clear all the examples, that
     * were specified previously.
     *
     * Accumulation is enabled by default.
     */
    var isAccumulationEnabled = true
        set(value) {
            if(!value && field){
                resetAccumulation()
            }
            field = value
        }

    /*
    Used internally to accumulate examples specified with
    example() and examples() methods. This works if
    isAccumulationEnabled property is set to true.
     */
    private lateinit var _examples: MutableList<DoubleArray>

    fun example(example: DoubleArray){
        checkSize(example)
        if(isAccumulationEnabled){
            ensureExamples()
            _examples.add(example)
        }
        _averageWeightsChange = rule.apply(example)
    }

    private fun checkSize(example: DoubleArray){
        if(example.size != inputLayerSize){
            throw IllegalArgumentException(
                    "The size of the specified " +
                    "example array (${example.size}) " +
                    "is not equal to size of the " +
                    "input layer ($inputLayerSize).")
        }
    }

    private fun ensureExamples(){
        if(!this::_examples.isInitialized){
            _examples = mutableListOf()
        }
    }

    fun examples(examples: List<DoubleArray>){
        if(isAccumulationEnabled){
            ensureExamples()
            _examples.addAll(examples)
        }
        examples.forEach {
            checkSize(it)
            _averageWeightsChange = rule.apply(it)
        }
    }

    fun repeat(){
        if(!isAccumulationEnabled){
            throw IllegalStateException(
                    "Epochs are not enabled " +
                    "for this learning manager.")
        }
        if(this::_examples.isInitialized){
            if(_examples.isNotEmpty()){
                _examples.shuffle()
                examples(_examples)
            }
        }else{
            throw IllegalStateException(
                    "Epoch examples are not specified.")
        }
    }

    fun resetAccumulation(){
        if(this::_examples.isInitialized){
            _examples.clear()
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