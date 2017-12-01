package com.imogene.neuro

import com.imogene.neuro.learning.GeneralizedHebbianAlgorithm
import java.util.*

fun main(args: Array<String>){
    val afSum = AggregationFunctions.sum()
    val tfEmpty = TransferFunctions.empty()
    val tfSigmoid = TransferFunctions.logistic()
    val tfTan = TransferFunctions.tanh()

    val net = NeuralNetwork {
        layer(20, afSum, tfEmpty)  // input layer
        layer(8, afSum, tfSigmoid) // first hidden layer
        layer {                        // second hidden layer
            neuron(afSum, tfSigmoid)
            neuron(afSum, tfTan)
            neuron(afSum, tfSigmoid)
            neuron(afSum, tfTan)
        }
    }

    val example = randomizedArray(20)

    net.learn(GeneralizedHebbianAlgorithm()){
        (0..50000).forEach { example(example) }
    }

    println()
    println()

    val answer = net.solve(example)
    answer.forEach(::println)
}

private fun randomizedArray(size: Int) : DoubleArray {
    val random = Random()
    return DoubleArray(size){
        random.nextDouble()
    }
}