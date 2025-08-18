package br.com.bd_notifica.enums;

// Status das requisições de material
public enum StatusProtocolo {
    PENDENTE,    // Aguardando análise
    EM_ANALISE,  // Sendo avaliado
    APROVADO,    // Aprovado para entrega
    REJEITADO,   // Não aprovado
    FINALIZADO   // Entregue ao solicitante
}