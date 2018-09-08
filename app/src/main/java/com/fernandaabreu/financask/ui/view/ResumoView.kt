package com.fernandaabreu.financask.ui.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.extension.formataValorParaBrasileiro
import com.fernandaabreu.financask.model.Resumo
import com.fernandaabreu.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal


class ResumoView(private val view: View,
                 transacoes: List<Transacao>,
                 private val context: Context){


    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza(){
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita(){
        val totalReceita = resumo.receita
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceita.formataValorParaBrasileiro()
        }
        // when  you need fix null
        /*view?.let {
            with(it.resumo_card_receita) {
                setTextColor(corReceita)
                text = totalReceita.formataValorParaBrasileiro()
            }
        }*/

    }



    private fun adicionaDespesa(){
        val totalDespesa = resumo.receita
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesa.formataValorParaBrasileiro()
        }

    }

    private fun adicionaTotal(){
       val total= resumo.total
       val cor = getColorBy(total)
        with(view.resumo_card_total) {
            setTextColor(cor)
            text = total.formataValorParaBrasileiro()
        }
    }

   private fun getColorBy(valor: BigDecimal): Int {
        return if (valor >= BigDecimal.ZERO) {
            corReceita
        } else {
            corDespesa
        }
    }

}