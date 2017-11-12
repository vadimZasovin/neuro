package com.imogene.neuro

object TransferFunctions {

    fun empty() = object : TransferFunction(){
        override fun transfer(value: Double) = value
    }

    fun linear(k: Double) : TransferFunction {
        val min : Double
        val max : Double
        if(k == 0.0){
            min = 0.0
            max = 0.0
        }else{
            min = Double.NEGATIVE_INFINITY
            max = Double.POSITIVE_INFINITY
        }
        return object : TransferFunction(min, max){
            override fun transfer(value: Double) = k * value
        }
    }

    fun semiLinear(k: Double) : TransferFunction {
        val min : Double
        val max : Double
        if(k > 0){
            min = 0.0
            max = Double.POSITIVE_INFINITY
        }else{
            if(k == 0.0){
                min = 0.0
            }else{
                min = Double.NEGATIVE_INFINITY
            }
            max = 0.0
        }
        return object : TransferFunction(min, max){
            override fun transfer(value: Double) = if(value < 0){
                0.0
            }else{
                k * value
            }
        }
    }

    fun withSaturation(range: ClosedRange<Double>) : TransferFunction {
        val min = range.start
        val max = range.endInclusive
        return object : TransferFunction(min, max){
            override fun transfer(value: Double) = when {
                value in range -> value
                value < range.start -> range.start
                else -> range.endInclusive
            }
        }
    }

    fun withThreshold(threshold: Double) = object : TransferFunction(0.0, 1.0){
        override fun transfer(value: Double) = if(value >= threshold) 1.0 else 0.0
    }

    fun logistic(k: Double = 0.5) = object : TransferFunction(0.0, 1.0){
        override fun transfer(value: Double) = 1 / (1 + Math.exp(-k * value))
    }

    fun tanh(k: Double = 0.5) = object : TransferFunction(-1.0, 1.0){
        override fun transfer(value: Double) = Math.tanh(k * value)
    }
}