package br.com.bd_notifica.dto;

import br.com.bd_notifica.enums.GrauDePrioridade;
import lombok.Data;

@Data
public class TicketCreateDTO {
    private String problema;
    private String descricao;
    private GrauDePrioridade prioridade;
    private Integer salaId;
    private Integer categoriaId;
}