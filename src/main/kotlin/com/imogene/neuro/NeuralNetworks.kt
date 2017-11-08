package com.imogene.neuro

interface MultiLayerNet : MultiLayerStructure {

    fun solve(inputs: DoubleArray) : DoubleArray
}

interface MultiLayerTaskSolverNet : MultiLayerNet {

    fun newTask() : Task

    fun solve(task: Task) : DoubleArray
}

interface MultiLayerSplitNet : MultiLayerSplitStructure {

    fun solve(inputs: DoubleArray) : DoubleArray
}

interface MultiLayerSplitTaskSolverNet : MultiLayerSplitNet {

    fun newTask() : Task

    fun solve(task: Task) : DoubleArray
}

interface ClassificationNet<out T> : MultiLayerStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<out T>
}

interface ClassificationTaskSolverNet<out T> : ClassificationNet<T> {

    fun newTask() : Task

    fun solve(task: Task) : ClassificationAnswer<out T>
}

interface ClassificationSplitNet<out T> : MultiLayerSplitStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<out T>
}

interface ClassificationSplitTaskSolverNet<out T> : ClassificationSplitNet<T> {

    fun newTask() : Task

    fun solve(task: Task) : ClassificationAnswer<out T>
}