package com.ciet.Skyline.controller;

import com.ciet.Skyline.model.BTC;
import com.ciet.Skyline.model.RestHandler;
import com.ciet.Skyline.model.SkylineModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SkylineController {
    private final SkylineModel model = new SkylineModel();
    
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        BTC btc = RestHandler.getBTCDia();
        return String.format("Hello %s", btc.data.base);
    }
    
    @PostMapping("/cadastro")
    public String cadastro(
        @RequestParam(value = "nome", defaultValue = "nome") String nome,
        @RequestParam(value = "cpf", defaultValue = "00011122233") String cpf) {
        
        return String.format("{\"wasCreated\":\""+ model.cadastroCliente(nome, cpf)+ "\"}");
    }
    
    @PutMapping("/creditar")
    public String creditar(
        @RequestParam(value = "cpf", defaultValue = "00011122233") String cpf,
        @RequestParam(value = "saldo", defaultValue = "0.0") double saldo) {
        
        return String.format("{\"wasCredited\":\""+model.creditarSaldo(cpf, saldo)+"\"}");
    }
    
    @PutMapping("/comprabtc")
    public String comprabtc(
        @RequestParam(value = "cpf", defaultValue = "00011122233") String cpf,
        @RequestParam(value = "valor", defaultValue = "0.0") double valor) {
        
        
        return String.format("{\"wasBought\":\""+model.compraBTC(cpf, valor)+"\"}");
    }
    
    @GetMapping("/getSaldoReal")
    public String getsaldoReal(@RequestParam(value = "cpf", defaultValue = "00011122233") String cpf){
        return String.format("{\"saldoReal\":\""+model.getSaldoReal(cpf)+"\"}");
    }
    
    @GetMapping("/getSaldoBtc")
    public String getsaldoBtc(@RequestParam(value = "cpf", defaultValue = "00011122233") String cpf){
        return String.format("{\"saldoBtc\":\""+model.getSaldoBtc(cpf)+"\"}");
    }
    
    
    @GetMapping("/valorTotalInvestido")
    public String valorTotalInvestido(@RequestParam(value = "cpf", defaultValue = "00011122233") String cpf){
        return String.format("{\"valorTotalInvestido\":\""+model.valorTotalInvestido(cpf)+"\"}");
    }
    
    @GetMapping("/lucroObtido")
    public String lucroObtido(@RequestParam(value = "cpf", defaultValue = "00011122233") String cpf){
        return String.format("{\"lucroObtido\":\""+model.lucroObtido(cpf)+"\"}");
    }
}