package com.imogene.neuro

interface InputLayerBuilder{

    fun buildInputLayer(taskTemplate: TaskTemplate,
                        transferFunction: TransferFunction = transferFunctionEmpty()) : LayersBuilder
}

interface LayersBuilder {

    fun addHiddenLayer(layer: Layer) : LayersBuilder

    fun addHiddenLayer(size: Int,
                       aggregationFunction: AggregationFunction,
                       transferFunction: TransferFunction) : LayersBuilder

    fun setOutputLayer(layer: Layer) : Finish

    fun setOutputLayer(size: Int,
                       aggregationFunction: AggregationFunction,
                       transferFunction: TransferFunction) : Finish
}

interface Finish{

    fun build() : NeuralNetwork
}

internal class NeuralNetworkBuilder : InputLayerBuilder, LayersBuilder, Finish {

    private val layers = mutableListOf<Layer>()

    private var normalizers : Map<MutableRange, Normalizer<*>>? = null

    private var nominalVariables: Map<Int, NominalVariable<*>>? = null

    override fun buildInputLayer(taskTemplate: TaskTemplate,
                                 transferFunction: TransferFunction): LayersBuilder {
        layers.add(taskTemplate.createInputLayer(transferFunction))
        normalizers = taskTemplate.normalizers
        nominalVariables = taskTemplate.nominalVariables
        return this
    }

    override fun addHiddenLayer(layer: Layer): LayersBuilder {
        layers.add(layer)
        return this
    }

    override fun addHiddenLayer(size: Int,
                                aggregationFunction: AggregationFunction,
                                transferFunction: TransferFunction): LayersBuilder {
        return addHiddenLayer(Layer(size, aggregationFunction, transferFunction))
    }

    override fun setOutputLayer(layer: Layer): Finish {
        layers.add(layer)
        return this
    }

    override fun setOutputLayer(size: Int,
                                aggregationFunction: AggregationFunction,
                                transferFunction: TransferFunction) : Finish {
        return setOutputLayer(Layer(size, aggregationFunction, transferFunction))
    }

    override fun build() : NeuralNetwork {
        val net = NeuralNetwork(layers.toTypedArray())
        net.normalizers = normalizers
        net.nominalVariables = nominalVariables
        return net
    }
}