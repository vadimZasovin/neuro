package com.imogene.neuro

internal class DoubleNormalizer : Normalizer<Double> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Double) = func(value)
}

internal class FloatNormalizer : Normalizer<Float> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Float) = func(value.toDouble())
}

internal class LongNormalizer : Normalizer<Long> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Long) = func(value.toDouble())
}

internal class IntNormalizer : Normalizer<Int> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Int) = func(value.toDouble())
}

internal class ShortNormalizer : Normalizer<Short> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Short) = func(value.toDouble())
}

internal class ByteNormalizer : Normalizer<Byte> {

    private val func = transferFunctionHyperbolicTangent()

    override fun normalize(value: Byte) = func(value.toDouble())
}

internal fun doubleNormalizer() : DoubleNormalizer{
    return DoubleNormalizer()
}
