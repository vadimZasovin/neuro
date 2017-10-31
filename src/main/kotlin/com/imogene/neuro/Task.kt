package com.imogene.neuro

class TaskTemplate{

    private var neuronsCounter = 0

    private val normalizers = mutableMapOf<MutableRange, Normalizer<*>>()

    fun addVariable() : TaskTemplate {
        neuronsCounter++
        return this
    }

    fun addVariables(count: Int) : TaskTemplate {
        neuronsCounter += count
        return this
    }

    fun addVariable(normalizer: Normalizer<*>) : TaskTemplate {
        return addVariables(1, normalizer)
    }

    fun addVariables(count: Int, normalizer: Normalizer<*>) : TaskTemplate {
        val first = neuronsCounter
        neuronsCounter += count
        val last = neuronsCounter
        val range = first to last
        normalizers.put(range, normalizer)
        return this
    }

    internal fun createInputLayer(transferFunction: TransferFunction) : Layer {
        return Layer(neuronsCounter, layerInitializer = { Neuron(aggregationFunctionSum(), transferFunction) })
    }
}

class Task internal constructor(private val normalizers: Map<IntRange, Normalizer<*>>){

    val inputs = mutableListOf<Double>()

    fun addVariable(value: Double) : Task {
        return this
    }

    fun addVariables(vararg values: Double) : Task {
        return this
    }

    fun addVariable(value: Any) : Task {
        return this
    }
}