package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkylineModel {

    //private ConexaoDB conn = new ConexaoDB("localhost", "5432", "SkylineDB", "skyline_user", "12345");
    private ConexaoDB conn = new ConexaoDB();
    
    private boolean cpfExist(ConexaoDB conn, String cpf){
        try {
            String query = "SELECT COUNT(*) FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(query);
            while(result.next()){ return true; }
            return false;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean cadastroCliente(String nome, String cpf){
        boolean response = false;
        
        this.conn.connect();
        if(!this.cpfExist(this.conn, cpf)){
            String query = "INSERT INTO \"public\".\"Clientes\"(nome, cpf, saldo) VALUES('"+ nome +"','"+ cpf +"','0.0');";
            this.conn.update(query);
            response = true;
        }
        this.conn.disconnect();
        return response;
    }

    public void creditarSaldo(String cpf, double saldo){
        this.conn.connect();
        try{
            double saldoAtual = 0.0;
            String qSelec = "SELECT saldo FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(qSelec);
            while(result.next()){ 
                saldoAtual = result.getDouble("saldo");
                saldo += saldoAtual;
            }
            String query = "UPDATE \"public\".\"Clientes\" SET saldo="+ saldo+" WHERE cpf='"+cpf+"';";
            this.conn.update(query);
        }catch(SQLException e){ e.printStackTrace(); }
        this.conn.disconnect();
    }

    public void compraBTC(String cpf, double valor){
        conn.connect();
        try{
            double saldoAtual = 0.0;
            String qSelec = "SELECT saldo FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(qSelec);
            while(result.next()){ saldoAtual = result.getDouble("saldo"); }
        }catch(Exception e){ e.printStackTrace(); }
        conn.disconnect();
    }
}
