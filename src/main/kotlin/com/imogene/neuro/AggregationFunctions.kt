package com.imogene.neuro

object AggregationFunctions {

    private val cache = mutableMapOf<String, TestA>()

    fun create(cacheKey: String?, func: (DoubleArray, DoubleArray, Double) -> Double) = if(cacheKey != null){
        cache[cacheKey] ?: createInternal(cacheKey, func)
    }else{
        createInternal(cacheKey, func)
    }

    private fun createInternal(cacheKey: String?, func: (DoubleArray, DoubleArray, Double) -> Double) : TestA {
        val result = object : TestA {
            override fun aggregate(inputs: DoubleArray, memory: DoubleArray, bias: Double)
                    = func(inputs, memory, bias)
        }
        if(cacheKey != null){
            cache.put(cacheKey, result)
        }
        return result
    }

    fun sum() = create("SUM"){inputs: DoubleArray, memory: DoubleArray, bias: Double ->
        var result = 0.0
        for ((index, input) in inputs.withIndex()){
            val weight = memory[index]
            result += input * weight
        }
        result += bias
        result
    }
}