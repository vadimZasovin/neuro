package com.imogene.neuro

fun main(args: Array<String>){
    println(transferFunctionLogistic()(-0.1))
    println(inputNeuron().signal(doubleArrayOf(0.354)))

    val layer = Layer(10)
    layer.memory = LayerMemory(10){
        NeuronMemory(4){
            1.0
        }
    }

    println("initial layer memory:")
    layer.memory.forEach{
        it.forEach(::print)
        println()
    }

    layer.updateMemory { neuronIndex, _ ->
        if(neuronIndex % 2 == 0){
            0.3
        }else{
            0.2
        }
    }

    println()
    println()

    println("updated layer memory:")
    layer.memory.forEach{
        it.forEach(::print)
        println()
    }

    //layer.signal(doubleArrayOf(1.0, 0.4, 0.3, 0.2)).forEach(::println)

    IntRange
}