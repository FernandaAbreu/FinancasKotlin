package com.fernandaabreu.financask.delegate

import com.fernandaabreu.financask.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao : Transacao)
}