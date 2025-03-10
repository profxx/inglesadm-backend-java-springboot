package br.com.dom.inglesadm_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.dom.inglesadm_backend.model.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

        // Listar lançamentos do mês atual
        @Query(value = "SELECT L.data_pagamento, A.nome, L.valor, L.status, " +
               "A.id AS alunoId, L.id_lancamento AS lancamentoId " +
               "FROM lancamento AS L " +
               "INNER JOIN aluno AS A ON L.id_aluno = A.id " +
               "WHERE MONTH(L.data_pagamento) = MONTH(CURRENT_DATE) " +
               "AND YEAR(L.data_pagamento) = YEAR(CURRENT_DATE) " +
               "AND A.valor > 0 " +
               "AND L.valor > 0 " +
               "ORDER BY L.status, L.data_pagamento DESC", 
       nativeQuery = true)
        List<Object[]> findPaymentsForCurrentMonth();

        // Listar os lançamentos por mês e ano
        @Query(value = "SELECT * FROM lancamento WHERE MONTH(data_pagamento) = :month AND YEAR(data_pagamento) = :year", nativeQuery = true)
        List<Billing> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

        // Pegar o faturamento, somando os valores de todos os alunos que não são
        // inativos
        @Query(value = "SELECT SUM(valor) FROM aluno WHERE dia != 'Inativo'", nativeQuery = true)
        Double calculateRevenue();

}
