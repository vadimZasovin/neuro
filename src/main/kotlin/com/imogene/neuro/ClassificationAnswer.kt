package com.imogene.neuro

class ClassificationAnswer<T> private constructor(
        val definiteResult : Result<T>?,
        val results : Array<Result<T>>){

    val hasDefiniteResult get() = definiteResult != null

    val matchingThresholdResultsCount get() = results.count { it.matchingThreshold }

    val matchingThresholdResults get() = results.filter { it.matchingThreshold }

    val ambiguousResultsCount get() = when {
        hasDefiniteResult -> 0
        matchingThresholdResultsCount > 1 -> {
            val first = results[0]
            results.count { it.probability == first.probability }
        }
        else -> 0
    }

    val ambiguousResults get() = if(ambiguousResultsCount > 1){
        val first = results[0]
        results.filter { it.probability == first.probability }
    }else{
        emptyList()
    }

    data class Result<out T> internal constructor(val value: T, val signal: Double,
                                                  val probability: Double, val matchingThreshold: Boolean){

        val probabilityPercent = probability * 100
    }

    internal companion object {

        fun <T> from(possibleValues: Array<T>, signals: DoubleArray,
                     probabilityThreshold: Double,
                     minSignal: Double, maxSignal: Double) : ClassificationAnswer<T> {
            val size = signals.size

            var min = minSignal
            var max = maxSignal
            if(min != 0.0){
                min -= min
                max -= min
            }

            val results = Array(size){
                val value = possibleValues[it]
                val signal = signals[it]

                var s = signal
                if(min != 0.0){
                    s -= min
                }
                val range = max - min
                val probability = s / range
                val matching = probability >= probabilityThreshold
                Result(value, signal, probability, matching)
            }

            results.sortByDescending { it.probability }
            val bestResult = results[0]
            var definiteResult : Result<T>? = null
            if(bestResult.matchingThreshold){
                if(results.size == 1){
                    definiteResult = bestResult
                }else if(bestResult.probability > results[1].probability){
                    definiteResult = bestResult
                }
            }

            return ClassificationAnswer(definiteResult, results)
        }
    }
}