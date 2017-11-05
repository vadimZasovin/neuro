package com.imogene.neuro.impl

import com.imogene.neuro.*

internal class MultiLayerNetImpl(layers: Array<LayerStructure>) :
        MultiLayerStructureImpl(layers), MultiLayerTaskSolverNet{

    override fun solve(inputs: DoubleArray) : DoubleArray {
        val size = inputs.size
        if(size != inputLayer.size){
            throw IllegalArgumentException("The size of the inputs array ($size) is not equal " +
                    "to the size of the input layer (${inputLayer.size}).")
        }
        var result = DoubleArray(size){
            val neuron = inputLayer[it]
            val input = inputs[it]
            neuron.signal(doubleArrayOf(input))
        }
        (1 until layersCount)
                .asSequence()
                .map { this[it] }
                .forEach { result = it.signal(result) }
        return result
    }

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