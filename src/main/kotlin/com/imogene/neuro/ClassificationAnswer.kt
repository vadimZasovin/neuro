package com.imogene.neuro

class ClassificationAnswer<out T> private constructor(
        val definiteResult : Result<T>?,
        val results : List<Result<T>>){

    val matchingThresholdResultsCount get() = results.count { it.matchingThreshold }

    val matchingThresholdResults get() = results.filter { it.matchingThreshold }

    val ambiguousResultsCount get() = when {
        definiteResult != null -> 0
        matchingThresholdResultsCount > 1 -> {
            val first = results[0]
            val probability = first.probability
            results.count { it.probability == probability }
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

            var results = initList(size){
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

            results = results.sortedByDescending { it.probability }
            val bestResult = results[0]
            var definiteResult : Result<T>? = null
            if(bestResult.matchingThreshold){
                if(results.size == 1 || bestResult.probability > results[1].probability){
                    definiteResult = bestResult
                }
            }

            return ClassificationAnswer(definiteResult, results)
        }
    }
}