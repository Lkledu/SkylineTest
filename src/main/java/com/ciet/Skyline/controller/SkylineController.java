package com.ciet.Skyline.controller;

import com.ciet.Skyline.model.BTC;
import com.ciet.Skyline.model.RestHandler;
import com.ciet.Skyline.model.SkylineModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkylineController {
    private SkylineModel model = new SkylineModel();
    
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s", RestHandler.getBTCDia());
    }
    
    @PostMapping("/cadastro")
    public String cadastro(
        @RequestParam(value = "nome", defaultValue = "nome") String nome,
        @RequestParam(value = "cpf", defaultValue = "00011122233") String cpf) {
        model.cadastroCliente(nome, cpf);
        
        return String.format("Hello %s", RestHandler.getBTCDia());
    }
    
    @GetMapping("/creditar")
    public String creditar(
        @RequestParam(value = "cpf", defaultValue = "nome") String cpf,
        @RequestParam(value = "saldo", defaultValue = "0.0") double saldo) {
        model.creditarSaldo(cpf, saldo);
        
        return String.format("Hello %s", RestHandler.getBTCDia());
    }
    
    @GetMapping("/comprabtc")
    public String comprabtc(
        @RequestParam(value = "cpf", defaultValue = "nome") String cpf,
        @RequestParam(value = "valor", defaultValue = "0.0") double valor) {
        model.creditarSaldo(cpf, valor);
        
        return String.format("Hello %s", RestHandler.getBTCDia());
    }
    
}