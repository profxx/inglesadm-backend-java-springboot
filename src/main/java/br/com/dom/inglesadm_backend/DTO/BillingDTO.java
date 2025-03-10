package br.com.dom.inglesadm_backend.DTO;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class BillingDTO {
    private LocalDateTime dataPagamento;
    private String nomeAluno;
    private Double valor;
    private String status;
    private Long alunoId;
    private Long lancamentoId;

    public BillingDTO(LocalDateTime dataPagamento, String nomeAluno, Double valor, String status, Long alunoId, Long lancamentoId) {
        this.dataPagamento = dataPagamento;
        this.nomeAluno = nomeAluno;
        this.valor = valor;
        this.status = status;
        this.alunoId = alunoId;
        this.lancamentoId = lancamentoId;
    }

}


