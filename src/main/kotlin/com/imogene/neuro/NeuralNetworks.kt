package com.imogene.neuro

interface MultiLayerNet : MultiLayerStructure {

    fun solve(inputs: DoubleArray) : DoubleArray
}

interface MultiLayerTaskSolverNet : MultiLayerNet {

    fun solve(build: TaskBuilder.() -> Unit) : DoubleArray
}

interface MultiLayerSplitNet : MultiLayerSplitStructure {

    fun solve(inputs: DoubleArray) : DoubleArray
}

interface MultiLayerSplitTaskSolverNet : MultiLayerSplitNet {

    fun solve(build: TaskBuilder.() -> Unit) : DoubleArray
}

interface ClassificationNet<out T> : MultiLayerStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<out T>
}

interface ClassificationTaskSolverNet<out T> : ClassificationNet<T> {

    override var probabilityThreshold : Double

    fun solve(build: TaskBuilder.() -> Unit) : ClassificationAnswer<out T>
}

interface ClassificationSplitNet<out T> : MultiLayerSplitStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<out T>
}

interface ClassificationSplitTaskSolverNet<out T> : ClassificationSplitNet<T> {

    override var probabilityThreshold : Double

    fun solve(build: TaskBuilder.() -> Unit) : ClassificationAnswer<out T>
}