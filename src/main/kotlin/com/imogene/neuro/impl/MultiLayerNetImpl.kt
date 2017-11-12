package com.imogene.neuro.impl

import com.imogene.neuro.*

internal class MultiLayerNetImpl(layers: Array<LayerStructure>) :
        MultiLayerStructureImpl(layers), MultiLayerTaskSolverNet{

    override fun solve(inputs: DoubleArray) = signal(inputs)

    var normalizers : Map<MutableRange, Normalizer<*>>? = null

    var nominalVariables : Map<Int, NominalVariable<*>>? = null

    override fun solve(build: TaskBuilder.() -> Unit) : DoubleArray {
        val builder = TaskBuilder(normalizers, nominalVariables)
        builder.build()
        return solve(builder.inputs)
    }
}