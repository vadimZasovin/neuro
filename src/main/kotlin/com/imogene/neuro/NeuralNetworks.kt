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

    fun solve(inputs: DoubleArray) : T
}

interface ClassificationTaskSolverNet<out T> : ClassificationNet<T> {

    fun newTask() : Task

    fun solve(task: Task) : T
}

interface ClassificationSplitNet<out T> : MultiLayerSplitStructure {

    fun solve(inputs: DoubleArray) : T
}

interface ClassificationSplitTaskSolverNet<out T> : ClassificationSplitNet<T> {

    fun newTask() : Task

    fun solve(task: Task) : T
}