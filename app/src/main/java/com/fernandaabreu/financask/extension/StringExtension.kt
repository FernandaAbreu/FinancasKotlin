package com.fernandaabreu.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int): String{
    if(this.length > caracteres){
        val primeiroCaracter = 0
        return  "${this.substring(primeiroCaracter,caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val formatoBBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataconvertida = formatoBBrasileiro.parse(this)

    val data = Calendar.getInstance()
    data.time = dataconvertida
    return data
}