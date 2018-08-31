package com.fernandaabreu.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.extension.formataParaBrasileiro
import com.fernandaabreu.financask.extension.formataValorParaBrasileiro
import com.fernandaabreu.financask.extension.limitaEmAte
import com.fernandaabreu.financask.model.Tipo
import com.fernandaabreu.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


class LIstaTransacoesAdapter (private val transacoes: List<Transacao>,
                              private val context: Context) : BaseAdapter(){


    private val limitedaCategoria = 14


    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
       return transacoes.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflate = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)
        val trans=getItem(position)
        adicionaValor(inflate, trans)

        adicionaIcone(trans, inflate)

        adicionaCategoria(inflate, trans)

        adicionaData(inflate, trans)
        return inflate

    }

    private fun adicionaData(inflate: View, trans: Transacao) {
        inflate.transacao_data.text = trans.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(inflate: View, trans: Transacao) {
        inflate.transacao_categoria.text = trans.categoria.limitaEmAte(limitedaCategoria)
    }

    private fun adicionaIcone(trans: Transacao, inflate: View) {
        val icone= iconePorTipo(trans.tipo)
        inflate.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconePorTipo(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            R.drawable.icone_transacao_item_receita
        } else {
            R.drawable.icone_transacao_item_despesa
        }
    }

    private fun adicionaValor(inflate: View, trans: Transacao) {
        inflate.transacao_valor.text = trans.valor.formataValorParaBrasileiro()

        val cor: Int = corPorTipo(trans.tipo)

        inflate.transacao_valor.setTextColor(cor);
    }

    private fun corPorTipo(tipo: Tipo): Int {
        return if (tipo == Tipo.RECEITA) {
            ContextCompat.getColor(context, R.color.receita)
        } else {
            ContextCompat.getColor(context, R.color.despesa)
        }
    }


}