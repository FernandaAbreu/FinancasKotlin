package com.fernandaabreu.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
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

abstract class FormularioTransacaoDialog(
          private val context: Context,
          private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoData = viewCriada.form_transacao_data
    protected val campoCategoria = viewCriada.form_transacao_categoria

    abstract protected val tituloEBotaoPositivo: String

    fun configuraDialog(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo,delegate)
    }



    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo= tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloEBotaoPositivo
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

                    delegate(transacaoCriada)
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


    abstract  protected  fun tituloPor(tipo: Tipo) : Int



    protected fun categoriasPor(tipo: Tipo): Int {
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