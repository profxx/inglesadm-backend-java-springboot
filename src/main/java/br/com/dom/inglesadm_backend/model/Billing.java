package br.com.dom.inglesadm_backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lancamento")
    private Long idLancamento;

    @Column(name = "conta_recebida")
    private String contaRecebida;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "id_aluno")
    private Long idAluno; // Ajustado para corresponder à sua consulta

    @Column(name = "valor")
    private Double valor;

    @Column(name = "dia_vencimento")
    private int diaVencimento;

    @Column(name = "status")
    private String status;

    // Construtor com parâmetros
    public Billing(String contaRecebida, LocalDateTime dataPagamento, Long alunoId, Double valor, int diaVencimento) {
        this.contaRecebida = contaRecebida;
        this.dataPagamento = dataPagamento;
        this.idAluno = alunoId;
        this.valor = valor;
        this.diaVencimento = diaVencimento;
        this.status = "A Vencer"; // Definido como "A Vencer"
    }
}
