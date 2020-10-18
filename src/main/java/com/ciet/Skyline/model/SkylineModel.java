package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkylineModel {

    //private ConexaoDB conn = new ConexaoDB("localhost", "5432", "SkylineDB", "skyline_user", "12345");
    private ConexaoDB conn = new ConexaoDB();
    
    private boolean cpfNotExist(ConexaoDB conn, String cpf){
        try {
            String query = "SELECT COUNT(*) FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(query);
            while(result.next()){
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    
    public boolean cadastroCliente(String nome, String cpf){
        boolean response = false;
        
        this.conn.connect();
        if(this.cpfNotExist(this.conn, cpf)){
            String query = "INSERT INTO \"public\".\"Clientes\"(nome, cpf, saldo) VALUES('"+ nome +"','"+ cpf +"','0.0');";
            this.conn.update(query);
            response = true;
        }
        this.conn.disconnect();
        return response;
    }

    public void creditarSaldo(String cpf, double saldo){
        conn.connect();



        conn.disconnect();
    }

    public void compraBTC(){
        conn.connect();



        conn.disconnect();
    }
}
