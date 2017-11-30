package com.imogene.neuro

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



    val random = Random()
    val answer = net.solve(DoubleArray(20, { // input vector (size = 20 = input layer size)
        random.nextDouble()
    }))

    answer.results.forEach { println(it.value + " " + it.probabilityPercent) }
}