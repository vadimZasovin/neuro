package com.imogene.neuro.impl

import com.imogene.neuro.*

internal class ClassificationSplitNetImpl<out T>(
        structures: Array<MultiLayerStructure>,
        private vararg val possibleValues: T) :
        MultiLayerSplitStructureImpl(structures),
        ClassificationSplitTaskSolverNet<T> {

    override var probabilityThreshold: Double = 0.85
        set(value) {
            if(value < 0 || value > 1){
                throw IllegalArgumentException("Probability threshold must be >= 0 and <= 1.")
            }
            field = value
        }

    private val minSignal : Double
    private val maxSignal : Double

    init {
        val transferFunction = this[0].outputLayer[0].transferFunction
        minSignal = transferFunction.min
        maxSignal = transferFunction.max
    }

    override fun solve(inputs: DoubleArray): ClassificationAnswer<out T> {
        val outputs = signal(inputs)
        return ClassificationAnswer.from(possibleValues, outputs, probabilityThreshold, minSignal, maxSignal)
    }

    var normalizers : Map<MutableRange, Normalizer<*>>? = null

    var nominalVariables : Map<Int, NominalVariable<*>>? = null

    override fun solve(build: TaskBuilder.() -> Unit) : ClassificationAnswer<out T> {
        val builder = TaskBuilder(normalizers, nominalVariables)
        builder.build()
        return solve(builder.inputs)
    }
}