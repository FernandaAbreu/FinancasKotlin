package com.fernandaabreu.financask.ui.activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.delegate.TransacaoDelegate
import com.fernandaabreu.financask.ui.adapter.LIstaTransacoesAdapter
import com.fernandaabreu.financask.model.Tipo
import com.fernandaabreu.financask.model.Transacao
import com.fernandaabreu.financask.ui.dialog.AdicionaTransacaoDialog
import com.fernandaabreu.financask.ui.view.ResumoView
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar


class ListaTransacoesActivity: AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        trasacoesDeExemplo()
        configuraViewResumo()
        configuraLista()
        configuraFab()


    }

    fun configuraFab() {
        lista_transacoes_adiciona_receita.setOnClickListener {

            chamaAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {

            chamaAdicao(Tipo.DESPESA)
        }
    }

    fun chamaAdicao(tipo : Tipo) {
        AdicionaTransacaoDialog(this, window.decorView as ViewGroup)
                .configuraDialog(tipo, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        atualizaTransacoes(transacao)
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
    }


    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraLista()
        configuraViewResumo()
    }

    private fun configuraViewResumo() {
        val view: View = window.decorView
        val resumoView= ResumoView(view, transacoes,this)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter =
                LIstaTransacoesAdapter(transacoes, this)
    }

    private fun trasacoesDeExemplo() {

        transacoes.add(Transacao(tipo = Tipo.DESPESA, data = Calendar.getInstance(), valor = BigDecimal(20.5)))
        transacoes.add(Transacao(BigDecimal(20.5), "Economia", Tipo.RECEITA, Calendar.getInstance()))
    }

}