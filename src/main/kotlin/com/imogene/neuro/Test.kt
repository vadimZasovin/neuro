package com.imogene.neuro

import java.util.*

data class CustomVariable(private val value: Int)

fun main(args: Array<String>){
    val taskTemplate = TaskTemplate()
            .addVariable()
            .addVariables(9)
            .addVariable(object : Normalizer<CustomVariable>{
                override fun normalize(value: CustomVariable) = 1.0
            })
            .addVariables(2, object : Normalizer<CustomVariable>{
                override fun normalize(value: CustomVariable) = -1.0
            })
            .addNominalVariable("first", "second")
            .addVariable()

    val net = NeuralNetwork.startBuilding()
            .buildInputLayer(taskTemplate)
            .addHiddenLayer(Layer(6))
            .addHiddenLayer(Layer(8))
            .setOutputLayer(Layer(4))
            .build()

    val task = net.newTask()
            .addVariable(5)
            .addVariable(199)
            .addVariables(45.8, 34.7, 67.9, 13.09, 34.9, 89.008, 19.8, 44.9)
            .addVariable(CustomVariable(89))
            .addVariables(CustomVariable(13), CustomVariable(12))
            .addNominalVariable("first")
            .addVariable(true)

    net.prepareMemory()
    val r = Random()
    net.updateMemory { _, _, _ -> r.nextDouble() }
    net.solve(task).forEach { println(it) }
}