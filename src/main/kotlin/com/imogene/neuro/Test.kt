package com.imogene.neuro

import com.imogene.neuro.impl.MultiLayerNetImpl
import java.util.*

data class CustomVariable(private val value: Int)

fun main(args: Array<String>){
    println(TransferFunctions.logistic().min)
}