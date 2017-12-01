package com.imogene.neuro.impl

import com.imogene.neuro.*
import com.imogene.neuro.learning.*

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

    override fun learn(rule: SupervisedLearningRule,
                       manage: SupervisedLearningManager.() -> Unit) {

    }

    override fun learn(rule: UnsupervisedLearningRule,
                       manage: UnsupervisedLearningManager.() -> Unit) {
        UnsupervisedLearningManager(rule, this).manage()
    }

    override fun learnTasks(rule: SupervisedLearningRule,
                            manage: TaskSolverSupervisedLearningManager.() -> Unit) {

    }

    override fun learnTasks(rule: UnsupervisedLearningRule,
                            manage: TaskSolverUnsupervisedLearningManager.() -> Unit) {
        TaskSolverUnsupervisedLearningManager(rule, this).manage()
    }
}