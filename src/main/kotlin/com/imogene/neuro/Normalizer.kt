package com.imogene.neuro

interface Normalizer<in T>{

    fun normalize(value: T) : Double
}