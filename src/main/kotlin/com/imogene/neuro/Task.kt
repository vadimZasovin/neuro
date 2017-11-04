package com.imogene.neuro

class TaskTemplate{

    private var neuronsCounter = 0

    internal val normalizers = mutableMapOf<MutableRange, Normalizer<*>>()

    internal val nominalVariables = mutableMapOf<Int, NominalVariable<*>>()

    fun addVariable() : TaskTemplate {
        neuronsCounter++
        return this
    }

    fun addVariables(count: Int) : TaskTemplate {
        neuronsCounter += count
        return this
    }

    fun addVariable(normalizer: Normalizer<*>) : TaskTemplate {
        return addVariables(1, normalizer)
    }

    fun addVariables(count: Int, normalizer: Normalizer<*>) : TaskTemplate {
        val first = neuronsCounter
        neuronsCounter += count
        val last = neuronsCounter
        val range = first to last
        normalizers.put(range, normalizer)
        return this
    }

    fun <T> addNominalVariable(vararg possibleValues: T) : TaskTemplate {
        val variable = NominalVariable(*possibleValues)
        nominalVariables.put(neuronsCounter, variable)
        val size = if(variable.size == 2) 1 else variable.size
        neuronsCounter += size
        return this
    }

    internal fun createInputLayer(transferFunction: TransferFunction) : Layer {
        return Layer(neuronsCounter, layerInitializer = {
            Neuron(aggregationFunctionSum(), transferFunction, 1, {1.0})
        })
    }
}

class Task internal constructor(
        private val normalizers: Map<MutableRange, Normalizer<*>>?,
        private val nominalVariables: Map<Int, NominalVariable<*>>?){

    private val _inputs = mutableListOf<Double>()

    fun addVariable(value: Double) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Double>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Double) : Task {
        val count = values.size
        val normalizer = findNormalizer<Double>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Float) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Float>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Float) : Task {
        val count = values.size
        val normalizer = findNormalizer<Float>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Long) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Long>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Long) : Task {
        val count = values.size
        val normalizer = findNormalizer<Long>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Int) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Int>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Int) : Task {
        val count = values.size
        val normalizer = findNormalizer<Int>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Short) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Short>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Short) : Task {
        val count = values.size
        val normalizer = findNormalizer<Short>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Byte) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Byte>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Byte) : Task {
        val count = values.size
        val normalizer = findNormalizer<Byte>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun addVariable(value: Boolean) : Task {
        val normalized : Double
        val normalizer = findNormalizer<Boolean>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
        return this
    }

    fun addVariables(vararg values: Boolean) : Task {
        val count = values.size
        val normalizer = findNormalizer<Boolean>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
        return this
    }

    fun <T> addVariable(value: T) : Task {
        val normalizer = getNormalizer<T>()
        val normalized = normalizer.normalize(value)
        _inputs.add(normalized)
        return this
    }

    fun <T> addVariables(vararg values: T) : Task {
        val count = values.size
        val normalizer = getNormalizer<T>(count)
        values.mapTo(_inputs){ normalizer.normalize(it) }
        return this
    }

    fun <T> addNominalVariable(value: T) : Task {
        val variable = getNominalVariableDefinition<T>()
        val size = variable.size
        val index = variable.indexOf(value)
        if(size == 2){
            _inputs.add(index.toDouble())
        }else{
            (0 until size).mapTo(_inputs) { if(it == index) 1.0 else 0.0 }
        }
        return this
    }

    private fun <T> findNormalizer(rangeSize: Int = 1) : Normalizer<T>? {
        if(normalizers == null){
            throw IllegalStateException("Normalizers are not specified.")
        }
        val range = getCurrentRange(rangeSize)
        @Suppress("UNCHECKED_CAST")
        return normalizers[range] as Normalizer<T>?
    }

    private fun <T> getNormalizer(rangeSize: Int = 1) : Normalizer<T> {
        return try {
            findNormalizer(rangeSize)!!
        }catch (e: NullPointerException){
            throw IllegalStateException("Normalizer at position $position is not found.", e)
        }
    }

    private fun <T> getNominalVariableDefinition() : NominalVariable<T> {
        if(nominalVariables == null){
            throw IllegalStateException("Nominal variables are not specified.")
        }
        return try {
            @Suppress("UNCHECKED_CAST")
            nominalVariables[position] as NominalVariable<T>
        }catch (e: NullPointerException){
            throw IllegalStateException("Nominal variable at position $position is not found.", e)
        }
    }

    private val position
        get() = _inputs.size

    private fun getCurrentRange(size: Int) : MutableRange {
        range.first = position
        range.last = position + size
        return range
    }

    private val range by lazy(LazyThreadSafetyMode.NONE) {
        MutableRange(0, 0)
    }

    internal val inputs
        get() = _inputs.toDoubleArray()
}