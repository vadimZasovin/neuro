package com.imogene.neuro

class TaskTemplate internal constructor(
        internal val inputNeurons: List<Neuron>,
        internal val normalizers: Map<MutableRange, Normalizer<*>>,
        internal val nominalVariables: Map<Int, NominalVariable<*>>)

class TaskTemplateBuilder internal constructor(){

    private var neuronsCounter = 0

    private val normalizers = mutableMapOf<MutableRange, Normalizer<*>>()

    private val nominalVariables = mutableMapOf<Int, NominalVariable<*>>()

    fun variable(){
        neuronsCounter++
    }

    fun variables(count: Int){
        neuronsCounter += count
    }

    fun variable(normalizer: Normalizer<*>){
        variables(1, normalizer)
    }

    fun variables(count: Int, normalizer: Normalizer<*>){
        val first = neuronsCounter
        neuronsCounter += count
        val last = neuronsCounter
        val range = first to last
        normalizers.put(range, normalizer)
    }

    fun <T> nominalVariable(vararg possibleValues: T){
        val variable = NominalVariable(*possibleValues)
        nominalVariables.put(neuronsCounter, variable)
        val size = if(variable.size == 2) 1 else variable.size
        neuronsCounter += size
    }

    internal fun build(transferFunction: TransferFunction) : TaskTemplate{
        val aggregationFunction = AggregationFunctions.sum()
        val inputNeurons = initList(neuronsCounter) {
            Neuron(aggregationFunction, transferFunction, 1, { 1.0 })
        }
        return TaskTemplate(inputNeurons, normalizers, nominalVariables)
    }
}

@Suppress("FunctionName")
fun TaskTemplate(build: TaskTemplateBuilder.() -> Unit) : TaskTemplate {
    val builder = TaskTemplateBuilder()
    builder.build()
    return builder.build(TransferFunctions.empty())
}

class TaskBuilder internal constructor(
        private val normalizers: Map<MutableRange, Normalizer<*>>?,
        private val nominalVariables: Map<Int, NominalVariable<*>>?){

    private val _inputs = mutableListOf<Double>()

    fun variable(value: Double) {
        val normalized : Double
        val normalizer = findNormalizer<Double>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Double) {
        val count = values.size
        val normalizer = findNormalizer<Double>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Float) {
        val normalized : Double
        val normalizer = findNormalizer<Float>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Float) {
        val count = values.size
        val normalizer = findNormalizer<Float>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Long) {
        val normalized : Double
        val normalizer = findNormalizer<Long>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Long) {
        val count = values.size
        val normalizer = findNormalizer<Long>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Int) {
        val normalized : Double
        val normalizer = findNormalizer<Int>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Int) {
        val count = values.size
        val normalizer = findNormalizer<Int>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Short) {
        val normalized : Double
        val normalizer = findNormalizer<Short>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Short) {
        val count = values.size
        val normalizer = findNormalizer<Short>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Byte) {
        val normalized : Double
        val normalizer = findNormalizer<Byte>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Byte) {
        val count = values.size
        val normalizer = findNormalizer<Byte>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun variable(value: Boolean) {
        val normalized : Double
        val normalizer = findNormalizer<Boolean>()
        normalized = normalizer?.normalize(value) ?: value.normalize()
        _inputs.add(normalized)
    }

    fun variables(vararg values: Boolean) {
        val count = values.size
        val normalizer = findNormalizer<Boolean>(count)
        values.mapTo(_inputs){ normalizer?.normalize(it) ?: it.normalize() }
    }

    fun <T> variable(value: T) {
        val normalizer = getNormalizer<T>()
        val normalized = normalizer.normalize(value)
        _inputs.add(normalized)
    }

    fun <T> variables(vararg values: T) {
        val count = values.size
        val normalizer = getNormalizer<T>(count)
        values.mapTo(_inputs){ normalizer.normalize(it) }
    }

    fun <T> nominalVariable(value: T) {
        val variable = getNominalVariableDefinition<T>()
        val size = variable.size
        val index = variable.indexOf(value)
        if(size == 2){
            _inputs.add(index.toDouble())
        }else{
            (0 until size).mapTo(_inputs) { if(it == index) 1.0 else 0.0 }
        }
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

    private val position get() = _inputs.size

    private fun getCurrentRange(size: Int) : MutableRange {
        range.first = position
        range.last = position + size
        return range
    }

    private val range by lazy(LazyThreadSafetyMode.NONE) {
        MutableRange(0, 0)
    }

    internal val inputs get() = _inputs.toDoubleArray()
}