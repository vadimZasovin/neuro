package com.imogene.neuro.impl

import com.imogene.neuro.*

internal class MultiLayerNetImpl(layers: Array<LayerStructure>) :
        MultiLayerStructureImpl(layers), MultiLayerTaskSolverNet{

    override fun solve(inputs: DoubleArray) = signal(inputs)

    var normalizers : Map<MutableRange, Normalizer<*>>? = null

    var nominalVariables : Map<Int, NominalVariable<*>>? = null

    override fun newTask() = Task(normalizers, nominalVariables)

    override fun solve(task: Task) : DoubleArray {
        return solve(task.inputs)
    }

    companion object {

        fun startBuilding() : InputLayerBuilder = NeuralNetworkBuilder()
    }
}