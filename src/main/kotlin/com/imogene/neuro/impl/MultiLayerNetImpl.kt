package com.imogene.neuro.impl

import com.imogene.neuro.*
import com.imogene.neuro.learning.LearningRule

internal class MultiLayerNetImpl(layers: List<LayerStructure>) :
        MultiLayerStructureImpl(layers), MultiLayerTaskSolverNet{

    override fun solve(inputs: DoubleArray) = signal(inputs)

    var normalizers : Map<MutableRange, Normalizer<*>>? = null

    var nominalVariables : Map<Int, NominalVariable<*>>? = null

    override fun solve(build: TaskBuilder.() -> Unit) : DoubleArray {
        val builder = TaskBuilder(normalizers, nominalVariables)
        builder.build()
        return solve(builder.inputs)
    }

    override fun split(): MultiLayerSplitTaskSolverNet {
        val structure = super.split()
        val net = MultiLayerSplitNetImpl(structure.structures)
        net.normalizers = normalizers
        net.nominalVariables = nominalVariables
        return net
    }

    override fun <T : LearningRule> learn(rule: T) = rule.apply {
        rule.apply(this@MultiLayerNetImpl)
    }
}