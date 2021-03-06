package com.imogene.neuro

import com.imogene.neuro.learning.GeneralizedHebbianAlgorithm
import java.util.*

fun main(args: Array<String>){
    val afSum = AggregationFunctions.sum()
    val tfSigmoid = TransferFunctions.logistic()

    val rule = GeneralizedHebbianAlgorithm()
    val taskTemplate = TaskTemplate {
        variables(4)
    }

    val net = NeuralNetwork(taskTemplate) {
        layer(6, afSum, tfSigmoid)  // hidden layer
        layer(4, afSum, tfSigmoid)  // output layer
    }

    val manager = net.learn(rule)
    with(manager){
        (0..100).forEach {
            example {
                variable(200)
                variable(15)
                variable(0.99)
                variable(13)
            }
        }
        println(averageWeightsChange)
        repeat()
        println(averageWeightsChange)
    }

    println()
    println()

    var answer = net.solve {
        variable(190)
        variable(10)
        variable(1.0)
        variable(13)

    }
    answer.forEach(::println)

    println()
    println()

    answer = net.solve {
        variable(300)
        variable(5)
        variable(0.56)
        variable(18)

    }
    answer.forEach(::println)
}

private fun randomizedArray(size: Int) : DoubleArray {
    val random = Random()
    return DoubleArray(size){
        random.nextDouble()
    }
}