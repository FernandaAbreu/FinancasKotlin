package com.fernandaabreu.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao> ){
    val receita get() = BigDecimal(somaPor(Tipo.RECEITA))

    val despesa get() = BigDecimal(somaPor(Tipo.DESPESA))


    fun somaPor(tipo: Tipo): Double {
        return transacoes
                .filter { it.tipo == tipo}
                .sumByDouble { it.valor.toDouble() }
    }


    val total: BigDecimal
        get() = receita.subtract(despesa)

}