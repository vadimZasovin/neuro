package com.imogene.neuro

import com.imogene.neuro.learning.*

interface MultiLayerNet : MultiLayerStructure {

    fun solve(inputs: DoubleArray) : DoubleArray

    override fun split() : MultiLayerSplitNet

    fun learn(rule: SupervisedLearningRule, manage: SupervisedLearningManager.() -> Unit)

    fun learn(rule: UnsupervisedLearningRule, manage: UnsupervisedLearningManager.() -> Unit)
}

interface MultiLayerTaskSolverNet : MultiLayerNet {

    fun solve(build: TaskBuilder.() -> Unit) : DoubleArray

    override fun split() : MultiLayerSplitTaskSolverNet

    fun learnTasks(rule: SupervisedLearningRule, manage: TaskSolverSupervisedLearningManager.() -> Unit)

    fun learnTasks(rule: UnsupervisedLearningRule, manage: TaskSolverUnsupervisedLearningManager.() -> Unit)
}

interface MultiLayerSplitNet : MultiLayerSplitStructure {

    fun solve(inputs: DoubleArray) : DoubleArray


}

interface MultiLayerSplitTaskSolverNet : MultiLayerSplitNet {

    fun solve(build: TaskBuilder.() -> Unit) : DoubleArray


}

interface ClassificationNet<out T> : MultiLayerStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<T>

    override fun split() : ClassificationSplitNet<T>
}

interface ClassificationTaskSolverNet<out T> : ClassificationNet<T> {

    override var probabilityThreshold : Double

    fun solve(build: TaskBuilder.() -> Unit) : ClassificationAnswer<T>

    override fun split() : ClassificationSplitTaskSolverNet<T>
}

interface ClassificationSplitNet<out T> : MultiLayerSplitStructure {

    var probabilityThreshold : Double

    fun solve(inputs: DoubleArray) : ClassificationAnswer<T>
}

interface ClassificationSplitTaskSolverNet<out T> : ClassificationSplitNet<T> {

    override var probabilityThreshold : Double

    fun solve(build: TaskBuilder.() -> Unit) : ClassificationAnswer<T>
}