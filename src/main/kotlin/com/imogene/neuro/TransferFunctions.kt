package com.imogene.neuro

object TransferFunctions {

    private val cache = mutableMapOf<String, TestF>()

    fun create(min: Double, max: Double, cacheKey: String?, func: (Double) -> Double) = if(cacheKey != null){
        cache[cacheKey] ?: createInternal(min, max, cacheKey, func)
    }else{
        createInternal(min, max, cacheKey, func)
    }

    private fun createInternal(min: Double, max: Double, cacheKey: String?, func: (Double) -> Double) : TestF {
        val result = object : TestF {
            override fun transfer(value: Double) = func(value)
            override val min: Double get() = min
            override val max: Double get() = max
        }
        if(cacheKey != null){
            cache.put(cacheKey, result)
        }
        return result
    }

    fun empty() = create(-Double.MAX_VALUE, Double.MAX_VALUE, "EMPTY"){ it }

    fun linear(k: Double) = create(-Double.MAX_VALUE, Double.MAX_VALUE, "LINEAR$k"){ it * k }

    fun semiLinear(k: Double) = create(0.0, Double.MAX_VALUE, cacheKey = "SEMI_LINEAR$k"){
        if(it < 0){
            0.0
        }else{
            k * it
        }
    }

    fun withSaturation(range: ClosedRange<Double>) : TestF {
        val min = range.start
        val max = range.endInclusive
        val cacheKey = "WITH_SATURATION$min-$max"
        return create(min, max, cacheKey){
            when {
                it in range -> it
                it < range.start -> range.start
                else -> range.endInclusive
            }
        }
    }

    fun withThreshold(threshold: Double) = create(0.0, 1.0, "THRESHOLD$threshold"){
        if(it >= threshold) 1.0 else 0.0
    }

    fun logistic(k: Double = 0.5) = create(0.0, 1.0, "LOGISTIC$k"){
        1 / (1 + Math.exp(-k * it))
    }

    fun tanh(k: Double = 0.5) = create(-1.0, 1.0, "TANH$k"){ Math.tanh(k * it) }
}