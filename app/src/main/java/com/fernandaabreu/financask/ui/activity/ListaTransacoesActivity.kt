package com.fernandaabreu.financask.ui.activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.fernandaabreu.financask.DAO.TransacaoDAO
import com.fernandaabreu.financask.R
import com.fernandaabreu.financask.delegate.TransacaoDelegate
import com.fernandaabreu.financask.ui.adapter.LIstaTransacoesAdapter
import com.fernandaabreu.financask.model.Tipo
import com.fernandaabreu.financask.model.Transacao
import com.fernandaabreu.financask.ui.dialog.AdicionaTransacaoDialog
import com.fernandaabreu.financask.ui.dialog.AlteraTransacaoDialog
import com.fernandaabreu.financask.ui.view.ResumoView
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal
import java.util.Calendar


class ListaTransacoesActivity: AppCompatActivity() {

    private val windowDecorview : View by lazy {
        window.decorView
    }
    private val viewGroupActivity : ViewGroup by lazy {
        windowDecorview as ViewGroup
    }
    private val dao = TransacaoDAO()
    private val transacoes = dao.transacoes
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
        AdicionaTransacaoDialog(this,  viewGroupActivity)
                .configuraDialog(tipo) { transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)

                }
    }

    fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }


    private fun atualizaTransacoes() {
        configuraLista()
        configuraViewResumo()
    }



    private fun configuraViewResumo() {
        val view: View = windowDecorview
        val resumoView= ResumoView(view, transacoes,this)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = LIstaTransacoesAdapter(transacoes, this)
        with(lista_transacoes_listview){
            adapter=listaTransacoesAdapter
            setOnItemClickListener{parent, view, position, id ->
                val transacao = transacoes[position]
                chamaDialogDeAtualização(transacao, position)

            }

            setOnCreateContextMenuListener { menu, view, menuInfo ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }

    }

    fun chamaDialogDeAtualização(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(this, viewGroupActivity)
                .configuraDialog(transacao) { transacaoAlterada ->

                    altera( transacaoAlterada,position)

                }
    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDoMenu = item?.itemId
        if (idDoMenu == 1) {
            val adapterMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterMenuInfo.position
            remove(posicaoDaTransacao)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicaoDaTransacao: Int) {
        dao.remove(posicaoDaTransacao)
        atualizaTransacoes()
    }

    fun altera(transacao: Transacao,position: Int) {
        dao.altera(transacao,position)
        atualizaTransacoes()
    }

    private fun trasacoesDeExemplo() {

        //transacoes.add(Transacao(tipo = Tipo.DESPESA, data = Calendar.getInstance(), valor = BigDecimal(20.5)))
        //stransacoes.add(Transacao(BigDecimal(20.5), "Economia", Tipo.RECEITA, Calendar.getInstance()))
    }

}