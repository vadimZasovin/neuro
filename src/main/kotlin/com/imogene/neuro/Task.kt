package com.imogene.neuro

private val normalizerFunction = transferFunctionHyperbolicTangent()

private fun Double.normalize() = normalizerFunction(this)

private fun Float.normalize() = normalizerFunction(this.toDouble())

private fun Long.normalize() = normalizerFunction(this.toDouble())

private fun Int.normalize() = normalizerFunction(this.toDouble())

private fun Short.normalize() = normalizerFunction(this.toDouble())

private fun Byte.normalize() = normalizerFunction(this.toDouble())

class Task(private val range: ClosedRange<Double> = (-1.0) .. (1.0)){

    private val _inputs: MutableList<Double> = mutableListOf()

    fun addVariable(d: Double) : Task {
        val normalized = d.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addVariable(f: Float) : Task {
        val normalized = f.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addVariable(l: Long) : Task {
        val normalized = l.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addVariable(i: Int) : Task {
        val normalized = i.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addVariable(s: Short) : Task {
        val normalized = s.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addVariable(b: Byte) : Task {
        val normalized = b.normalize()
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun <T> addVariable(variable: T, normalizer: (T) -> Double) : Task {
        val normalized = normalizer(variable)
        checkNormalizedValue(normalized)
        _inputs.add(normalized)
        return this
    }

    fun addRawSignal(signal: Double) : Task {
        checkNormalizedValue(signal)
        _inputs.add(signal)
        return this
    }

    private fun checkNormalizedValue(value: Double){
        if(value !in range){
            throw IllegalArgumentException("Normalized value $value is not in the allowed range $range")
        }
    }

    internal val inputs
        get() = _inputs.toDoubleArray()
}