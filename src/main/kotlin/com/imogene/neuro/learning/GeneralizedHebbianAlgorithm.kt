package com.imogene.neuro.learning

import com.imogene.neuro.LayerStructure

class GeneralizedHebbianAlgorithm : HebbianRule() {

    override fun getWeightDecay(layer: LayerStructure, position: Int,
                                weightIndex: Int, inputs: DoubleArray,
                                outputs: DoubleArray) : Double {
        val output = outputs[position]
        val neurons = layer.neurons
        var sum = 0.0
        for (k in 0..position){
            val neuron = neurons[k]
            val weights = neuron.memory
            val weight = weights[weightIndex]
            sum += weight * outputs[k]
        }
        return output * sum
    }
}