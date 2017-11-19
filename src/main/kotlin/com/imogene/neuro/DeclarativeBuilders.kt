package com.imogene.neuro

import com.imogene.neuro.impl.ClassificationNetImpl
import com.imogene.neuro.impl.MultiLayerNetImpl

class LayerBuilder internal constructor() {

    internal val neurons = mutableListOf<Neuron>()

    fun neuron(neuron: Neuron){
        neurons.add(neuron)
    }

    fun neuron(aggregationFunction: AggregationFunction, transferFunction: TransferFunction){
        neuron(Neuron(aggregationFunction, transferFunction))
    }
}

class LayersBuilder internal constructor() {

    internal val layers = mutableListOf<Layer>()

    fun layer(layer: Layer){
        layers.add(layer)
    }

    fun layer(size: Int, initializer: (Int) -> Neuron){
        layer(Layer(size, initializer))
    }

    fun layer(size: Int, aggregationFunction: AggregationFunction, transferFunction: TransferFunction){
        layer(Layer(size, aggregationFunction, transferFunction))
    }

    fun layer(build: LayerBuilder.() -> Unit){
        val builder = LayerBuilder()
        builder.build()
        layer(Layer(builder.neurons))
    }

    fun layers(layers: Iterable<Layer>){
        this.layers.addAll(layers)
    }
}

@Suppress("FunctionName")
fun NeuralNetwork(build: LayersBuilder.() -> Unit) : MultiLayerNet {
    val builder = LayersBuilder()
    builder.build()
    return MultiLayerNetImpl(builder.layers)
}

@Suppress("FunctionName")
fun NeuralNetwork(taskTemplate: TaskTemplate, build: LayersBuilder.() -> Unit) : MultiLayerTaskSolverNet {
    val inputLayer = Layer(taskTemplate.inputNeurons)
    val builder = LayersBuilder()
    builder.layer(inputLayer)
    builder.build()
    val net = MultiLayerNetImpl(builder.layers)
    net.normalizers = taskTemplate.normalizers
    net.nominalVariables = taskTemplate.nominalVariables
    return net
}

@Suppress("FunctionName")
fun <T> NeuralNetwork(vararg possibleValues: T, build: LayersBuilder.() -> Unit) : ClassificationNet<T> {
    val builder = LayersBuilder()
    builder.build()
    builder.layer(possibleValues.size, AggregationFunctions.sum(), TransferFunctions.tanh())
    return ClassificationNetImpl(builder.layers, *possibleValues)
}

@Suppress("FunctionName")
fun <T> NeuralNetwork(taskTemplate: TaskTemplate,
                      vararg possibleValues: T,
                      build: LayersBuilder.() -> Unit) : ClassificationTaskSolverNet<T> {
    val inputLayer = Layer(taskTemplate.inputNeurons)
    val builder = LayersBuilder()
    builder.layer(inputLayer)
    builder.build()
    builder.layer(possibleValues.size, AggregationFunctions.sum(), TransferFunctions.tanh())
    val net = ClassificationNetImpl(builder.layers, *possibleValues)
    net.normalizers = taskTemplate.normalizers
    net.nominalVariables = taskTemplate.nominalVariables
    return net
}