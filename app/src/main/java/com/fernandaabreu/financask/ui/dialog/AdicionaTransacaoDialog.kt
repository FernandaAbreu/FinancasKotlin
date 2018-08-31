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

class AdicionaTransacaoDialog(val context: Context,
                              val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()

    fun configuraDialog(tipo: Tipo,transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo,transacaoDelegate)
    }

    private val campoValor = viewCriada.form_transacao_valor

    private val campoData = viewCriada.form_transacao_data

    private val campoCategoria = viewCriada.form_transacao_categoria

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        val titulo= tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton("Adicionar"
                ) { _, _ ->
                    val valorEmTexto = campoValor.text.toString()
                    val dataEmTexto = campoData.text.toString()
                    val categoriaEmTexto = campoCategoria.selectedItem.toString()


                    val valor = obtemBigDecimalPorString(valorEmTexto)

                    val data= dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = tipo,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    transacaoDelegate.delegate(transacaoCriada)
                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

    fun obtemBigDecimalPorString(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context,
                    "Falha na conversão de valor",
                    Toast.LENGTH_LONG)
                    .show()
            BigDecimal.ZERO
        }
    }


    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
                .createFromResource(context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    private fun tituloPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            R.string.adiciona_despesa
        } else {
            R.string.adiciona_receita
        }
    }

    private fun categoriasPor(tipo: Tipo): Int {
        return if (tipo == Tipo.DESPESA) {
            R.array.categorias_de_despesa
        } else {
            R.array.categorias_de_receita
        }
    }

    private fun configuraCampoData() {
        val ano = 2017
        val mes = 9
        val dia = 18

        val hoje = Calendar.getInstance()
        campoData
                .setText(hoje.formataParaBrasileiro())
        campoData
                .setOnClickListener {
                    DatePickerDialog(context,
                            DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                                val dataSelecionada = Calendar.getInstance()
                                dataSelecionada.set(ano, mes, dia)
                                campoData
                                        .setText(dataSelecionada.formataParaBrasileiro())
                            }
                            , ano, mes, dia)
                            .show()
                }
    }

    private fun criaLayout(): View {
        val viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.form_transacao,
                        viewGroup,
                        false)
        return viewCriada
    }
}