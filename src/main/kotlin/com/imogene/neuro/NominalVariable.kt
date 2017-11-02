package com.imogene.neuro

internal class NominalVariable<in T>(private vararg val values: T) {

    init {
        if(values.size < 2){
            throw IllegalArgumentException("Nominal variable must have at least two possible values")
        }
    }

    val size
        get() = values.size

    fun indexOf(value: T) : Int {
        val result = values.indexOf(value)
        if(result == -1){
            throw IllegalArgumentException("This NominalVariable does not include specified value.")
        }
        return result
    }
}