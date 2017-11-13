package com.imogene.neuro

import com.imogene.neuro.impl.MultiLayerNetImpl
import java.util.*

fun main(args: Array<String>){
    val afSum = AggregationFunctions.sum()
    val tfEmpty = TransferFunctions.empty()
    val tfSigmoid = TransferFunctions.logistic()
    val tfTan = TransferFunctions.tanh()

    val net = NeuralNetwork("белый", "черный", "азиат") {
        layer(20, afSum, tfEmpty)  // input layer
        layer(8, afSum, tfSigmoid) // first hidden layer
        layer {                        // second hidden layer
            neuron(afSum, tfSigmoid)
            neuron(afSum, tfTan)
            neuron(afSum, tfSigmoid)
            neuron(afSum, tfTan)
        }
    }

    net.prepareBiases()
    net.prepareMemory()

    val random = Random()
    val answer = net.solve(DoubleArray(20, { // input vector (size = 20 = input layer size)
        random.nextDouble()
    }))

    assert(answer.hasDefiniteResult && answer.definiteResult.value == "белый")
    println(answer.ambiguousResultsCount)
}