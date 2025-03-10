package br.com.dom.inglesadm_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dom.inglesadm_backend.DTO.BillingDTO;
import br.com.dom.inglesadm_backend.model.Billing;
import br.com.dom.inglesadm_backend.service.BillingService;

@RestController
@RequestMapping("bills")
public class BillingController {

    @Autowired
    private BillingService billingService;


    // Listar os lançamentos do mês apenas
    @GetMapping("currentmonth")
    public List<BillingDTO> getPaymentsForCurrentMonth() {
        return billingService.findPaymentsForCurrentMonth();
    }

    // Inserir novo lançamento
    @PostMapping
    public ResponseEntity<Billing> insertNew(@RequestBody Billing billing){
        Billing billinginserted = billingService.insertNew(billing);
        return ResponseEntity.ok().body(billinginserted);
    }

    // Alterar lançamento
    @PutMapping("/{id}")
    public ResponseEntity<Billing> update(@PathVariable Long id, @RequestBody Billing update){
        Billing billing = billingService.update(id, update);
        return ResponseEntity.ok().body(billing);
    }

    // Calcular o faturamento
    @GetMapping("revenue")
    public ResponseEntity<Double> calculateRevenue(){
        Double revenue = billingService.calculateRevenue();
        return ResponseEntity.ok().body(revenue);
    }


}
