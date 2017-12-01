package com.imogene.neuro.learning

import com.imogene.neuro.*
import kotlin.math.abs

abstract class HebbianRule : UnsupervisedLearningRule {

    override var learningRate: Double = 0.03

    private lateinit var structure : MultiLayerStructure

    /*
    The number of weights in the net.
    Used to calculate the average
    change in weights after demonstrating
    a single example to the net. This
    property is initialized in the
    initialize() method.
     */
    private var weightsCount = 0

    /*
    Weights change per layer.
    Used to calculate the average
    change in weights after demonstrating
    a single example to the net.
     */
    private var weightsChange = 0.0

    override fun initialize(structure: Structure) {
        if(structure is MultiLayerStructure){
            this.structure = structure
            structure.initMemory()
            structure.initBiases()

            weightsCount = structure.layers.sumBy {
                it.neurons.sumBy {
                    it.memory.size
                }
            }
        }else{
            throw IllegalStateException(
                    "Hebbian rule can not be " +
                    "applied to split structure.")
        }
    }

    override fun apply(example: DoubleArray) : Double {
        val inputLayer = structure.inputLayer
        val inputLayerSize = inputLayer.size
        var inputs = DoubleArray(inputLayerSize){
            val neuron = inputLayer[it]
            val input = example[it]
            neuron.signal(doubleArrayOf(input))
        }
        var sumChange = 0.0
        (1 until structure.layersCount)
                .asSequence()
                .map { structure[it] }
                .forEach {
                    inputs = signal(it, inputs)
                    sumChange += weightsChange
                }
        return sumChange / weightsCount
    }

    private fun signal(layer: LayerStructure, inputs: DoubleArray) : DoubleArray {
        // reset change, since it is calculated per layer
        weightsChange = 0.0
        // calculate output vector immediately,
        // because it is used in calculating
        // weights decay
        val outputs = layer.signal(inputs)
        val neurons = layer.neurons
        neurons.forEachIndexed { i, neuron ->
            val weights = neuron.memory
            // neuron index is the index of
            // it's output signal in the
            // outputs array
            val output = outputs[i]
            weights.forEachIndexed { j, weight ->
                val decay = getWeightDecay(layer, i, j, inputs, outputs)
                val input = inputs[j]
                val delta = learningRate * (output * input - decay)
                weights[j] = weight + delta

                // update change value
                weightsChange += abs(delta)
            }
        }
        return outputs
    }

    protected abstract fun getWeightDecay(layer: LayerStructure, position: Int,
                                          weightIndex: Int, inputs: DoubleArray,
                                          outputs: DoubleArray) : Double
}