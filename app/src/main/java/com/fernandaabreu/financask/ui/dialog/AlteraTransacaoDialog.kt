package com.fernandaabreu.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.delegate.TransacaoDelegate
import com.fernandaabreu.financask.extension.converteParaCalendar
import com.fernandaabreu.financask.extension.formataParaBrasileiro
import com.fernandaabreu.financask.model.Tipo
import com.fernandaabreu.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog( private val  context: Context,
                             viewGroup: ViewGroup) : FormularioTransacaoDialog(context, viewGroup) {
    override val tituloEBotaoPositivo: String
        get() = "Alterar"


    fun configuraDialog(transacao: Transacao ,delegate: (transacao: Transacao) -> Unit) {
        val tipo: Tipo = transacao.tipo
        super.configuraDialog(tipo,delegate)
        InicializaCampos(transacao)
    }

    fun InicializaCampos(transacao: Transacao) {
         val tipo = transacao.tipo
        inicializaCampoValor(transacao)
        inicializaData(transacao)
        inicializaCategoria(tipo, transacao)
    }

    fun inicializaCategoria(tipo: Tipo, transacao: Transacao) {
        val categoriaRetornadas = context.resources.getStringArray(categoriasPor(tipo))
        val posicaoCategoria = categoriaRetornadas.indexOf(transacao.categoria)
        campoCategoria.setSelection(posicaoCategoria, true)
    }

    fun inicializaData(transacao: Transacao) {
        campoData.setText(transacao.data.formataParaBrasileiro())
    }

    fun inicializaCampoValor(transacao: Transacao) {
        campoValor.setText(transacao.valor.toString())
    }


    override fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            R.string.altera_despesa
        } else {
            R.string.altera_receita
        }
    }









}