package com.imogene.neuro

import com.imogene.neuro.learning.GeneralizedHebbianAlgorithm
import java.util.*

fun main(args: Array<String>){
    val afSum = AggregationFunctions.sum()
    val tfEmpty = TransferFunctions.empty()
    val tfLinear = TransferFunctions.linear(1.0)
    val tfSigmoid = TransferFunctions.logistic()

    val rule = GeneralizedHebbianAlgorithm()
    val example = randomizedArray(4)

    val net = NeuralNetwork {
        layer(4, afSum, tfEmpty)  // input layer
        layer(6, afSum, tfLinear)  // first hidden layer
        layer(4, afSum, tfSigmoid) // output layer
    }.learn(rule){
        (0..100000).forEach { example(example) }
        val newExample = randomizedArray(4)
        (0..100000).forEach { example(newExample) }
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