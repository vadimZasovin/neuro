package com.imogene.neuro

interface Normalizable<in T>{

    fun normalize(value: T) : Double
}

class DoubleNormalizable : Normalizable<Double> {

    override fun normalize(value: Double): Double {
        return 0.0
    }
}