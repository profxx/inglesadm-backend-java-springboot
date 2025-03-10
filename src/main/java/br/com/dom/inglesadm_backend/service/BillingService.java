package br.com.dom.inglesadm_backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.dom.inglesadm_backend.DTO.BillingDTO;
import br.com.dom.inglesadm_backend.model.Billing;
import br.com.dom.inglesadm_backend.model.Aluno;
import br.com.dom.inglesadm_backend.repository.BillingRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private AlunoService alunoService; // Serviço de Alunos

    @PersistenceContext
    private EntityManager entityManager;

    // Criar lançamentos apenas para alunos que ainda não têm um lançamento no mês
    // atual
    // de forma quando instanciar pela primeira vez.
    @PostConstruct
    public void initializeBillings() {
        generateBillingsForCurrentMonth();
    }

    
    // Inserir novo lançamento
    public Billing insertNew(Billing billing) {
        return billingRepository.save(billing);
    }

    // Find by ID
    public Billing findById(Long id) {
        return billingRepository.findById(id).orElse(null);
    }

    // Alterar lançamento
    public Billing update(Long id, Billing current) {
        Billing billing = findById(id);
        billing.setContaRecebida(current.getContaRecebida());
        billing.setDataPagamento(current.getDataPagamento());
        billing.setIdAluno(current.getIdAluno());
        billing.setValor(current.getValor());
        return billingRepository.save(billing);
    }

    @Scheduled(cron = "0 0 0 * * *") // Executa todos os dias à meia-noite
    public void billingsScheduler() {
        // Obter a data atual
        LocalDateTime hoje = LocalDateTime.now();

        // Atualizar status de todos os lançamentos
        List<Billing> todosLancamentos = billingRepository.findAll();
        todosLancamentos.forEach(lancamento -> {
            LocalDateTime dataPagamento = lancamento.getDataPagamento(); // Supondo que o tipo agora seja LocalDateTime

            if (dataPagamento.isAfter(hoje)) {
                lancamento.setStatus("A Vencer");
            } else if (dataPagamento.toLocalDate().isEqual(hoje.toLocalDate())) {
                lancamento.setStatus("Vence Hoje");
            } else if (dataPagamento.isBefore(hoje)) {
                lancamento.setStatus("Vencido");
            }

            // Atualizar status de quitação
            if (lancamento.getContaRecebida() != null && !lancamento.getContaRecebida().isEmpty()) {
                lancamento.setStatus("Quitado");
            }

            // Atualizar para "Atrasado" se estiver vencido e não quitado
            if ("Vencido".equals(lancamento.getStatus()) &&
                    (lancamento.getContaRecebida() == null || lancamento.getContaRecebida().isEmpty())) {
                lancamento.setStatus("Atrasado");
            }

            // Salvar o lançamento com status atualizado
            billingRepository.save(lancamento);
        });

        // Gerar novos lançamentos para o próximo mês
        List<Billing> lancamentosVencidosHoje = todosLancamentos.stream()
                .filter(lancamento -> lancamento.getStatus().equals("Vence Hoje"))
                .collect(Collectors.toList());

        lancamentosVencidosHoje.forEach(lancamento -> {
            LocalDateTime proximaData = lancamento.getDataPagamento().plusMonths(1); // Usando LocalDateTime

            // Ajustar o dia de vencimento
            int ultimoDiaDoMes = proximaData.toLocalDate().lengthOfMonth();
            if (lancamento.getDiaVencimento() > ultimoDiaDoMes) {
                proximaData = proximaData.withDayOfMonth(ultimoDiaDoMes);
            } else {
                proximaData = proximaData.withDayOfMonth(lancamento.getDiaVencimento());
            }

            // Criar e salvar novo lançamento
            Billing novoLancamento = new Billing(
                    lancamento.getContaRecebida(),
                    proximaData,
                    lancamento.getIdAluno(),
                    lancamento.getValor(),
                    lancamento.getDiaVencimento());
            billingRepository.save(novoLancamento);
        });
    }

    // Método para criar lançamentos para todos os alunos ativos e com valor maior
    // que 0
    public void createBillingsForActiveStudents() {
        // Obter todos os alunos ativos e com valor maior que 0 usando o StudentService
        List<Aluno> activeStudents = alunoService.findActiveStudentsOrderedByName();

        // Filtrar os alunos que têm valor maior que 0 e não têm status "inativo"
        activeStudents = activeStudents.stream()
                .filter(student -> student.getValor() != null && student.getValor() > 0)
                .filter(student -> !student.getDia().equals("Inativo"))
                .collect(Collectors.toList());

        // Obter a data de vencimento do mês atual (por exemplo, considerando o dia 1 do
        // mês para vencimento)
        LocalDateTime currentDueDate = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        // Para cada aluno ativo, criar um lançamento de cobrança
        activeStudents.forEach(student -> {
            // Definir conta recebida - você pode querer definir como "" ou um valor
            // específico
            String contaRecebida = ""; // Se necessário, defina o valor apropriado aqui

            // Verificar se o valor é nulo, caso sim, atribuir 0.0 como valor padrão
            Double valorMensalidade = student.getValor() != null ? student.getValor() : 0.0;

            // Criar um novo lançamento
            Billing newBilling = new Billing(
                    contaRecebida, // Conta recebida (string vazia ou valor adequado)
                    currentDueDate, // Data de vencimento
                    student.getId(), // ID do aluno
                    valorMensalidade, // Valor da mensalidade
                    currentDueDate.getDayOfMonth() // Dia de vencimento
            );

            // Salvar o novo lançamento
            billingRepository.save(newBilling);
        });
    }

    // Gerar lançamentos para o mês atual, sem repetir, com filtragem
    public void generateBillingsForCurrentMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentDueDate = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0); // Primeiro dia do
                                                                                                      // mês à                                                                   // meia-noite

        // Buscar alunos ativos e com valor maior que 0
        List<Aluno> activeStudents = alunoService.findActiveStudentsOrderedByName();
        activeStudents = activeStudents.stream()
                .filter(student -> student.getValor() != null && student.getValor() > 0)
                .filter(student -> !student.getDia().equals("Inativo"))
                .collect(Collectors.toList());

        // Buscar IDs dos alunos que já possuem lançamento no mês atual
        List<Long> studentsWithBilling = billingRepository.findByMonthAndYear(
                now.getMonthValue(), now.getYear()).stream()
                .map(Billing::getIdAluno)
                .collect(Collectors.toList());

        // Criar lançamentos apenas para alunos que ainda não têm um lançamento no mês
        // atual
        for (Aluno student : activeStudents) {
            if (!studentsWithBilling.contains(student.getId())) {
                Double valorMensalidade = student.getValor() != null ? student.getValor() : 0.0; // Evitar
                                                                                                 // NullPointerException
                int diaVencimento = Math.min(currentDueDate.toLocalDate().lengthOfMonth(),
                        currentDueDate.getDayOfMonth()); // Garante que não ultrapasse o último dia do mês

                Billing newBilling = new Billing(
                        null, // Conta recebida (ainda não foi quitada)
                        currentDueDate, // Data de vencimento
                        student.getId(), // ID do aluno
                        valorMensalidade, // Valor da mensalidade
                        diaVencimento // Dia de vencimento
                );

                billingRepository.save(newBilling);
            }
        }
    }

    // Gerar o valor do faturamento mensal, baseado na soma dos valores dos alunos
    public Double calculateRevenue() {
        return billingRepository.calculateRevenue();
    }

    // Pegar mensalidades do mês atual apenas usando o REPOSITORY
    public List<BillingDTO> findPaymentsForCurrentMonth() {
        List<Object[]> results = billingRepository.findPaymentsForCurrentMonth();
        return results.stream().map(obj -> new BillingDTO(
                ((java.sql.Timestamp) obj[0]).toLocalDateTime(), // Convertendo para LocalDateTime
                (String) obj[1], // nomeAluno
                (Double) obj[2], // valor
                (String) obj[3], // status
                (Long) obj[4], // alunoId
                (Long) obj[5] // lancamentoId
        )).collect(Collectors.toList());
    }
}
