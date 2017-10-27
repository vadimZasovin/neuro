package com.imogene.neuro

interface InputLayerBuilder{

    fun setTaskTemplate(taskTemplate: TaskTemplate) : HiddenPartBuilder
}

class TaskTemplate{

    fun addDoubleVariable() : TaskTemplate {
        return this
    }

    fun <T> addVariable() : TaskTemplate {
        return this
    }

    internal fun createInputLayer() : Layer {
        return Layer(1)
    }
}

interface HiddenPartBuilder{

    fun addHiddenLayer(layer: Layer) : HiddenPartBuilder

    fun addHiddenLayer(size: Int,
                       aggregationFunction: AggregationFunction,
                       transferFunction: TransferFunction) : HiddenPartBuilder

    fun buildOutputLayer() : OutputLayerBuilder
}

interface OutputLayerBuilder{

    fun setOutputLayer(layer: Layer) : Finish
}

interface Finish{

    fun build() : NeuralNetwork
}

internal class NeuralNetworkBuilder : InputLayerBuilder, HiddenPartBuilder, OutputLayerBuilder, Finish{

    private val layers = mutableListOf<Layer>()

    override fun setTaskTemplate(taskTemplate: TaskTemplate): HiddenPartBuilder {
        layers.add(taskTemplate.createInputLayer())
        return this
    }

    override fun addHiddenLayer(layer: Layer): HiddenPartBuilder {
        layers.add(layer)
        return this
    }

    override fun addHiddenLayer(size: Int,
                                aggregationFunction: AggregationFunction,
                                transferFunction: TransferFunction): HiddenPartBuilder {
        return addHiddenLayer(Layer(size, aggregationFunction, transferFunction))
    }

    override fun buildOutputLayer() = this

    override fun setOutputLayer(layer: Layer): Finish {
        layers.add(layer)
        return this
    }

    override fun build() = NeuralNetwork(layers.toTypedArray())
}