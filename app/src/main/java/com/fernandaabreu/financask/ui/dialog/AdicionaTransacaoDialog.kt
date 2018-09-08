package com.fernandaabreu.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.model.Tipo

class AdicionaTransacaoDialog( context: Context,
                                viewGroup: ViewGroup) : FormularioTransacaoDialog(context, viewGroup){
    override val tituloEBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            R.string.adiciona_despesa
        } else {
            R.string.adiciona_receita
        }
    }
}