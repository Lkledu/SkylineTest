package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkylineModel {

    private ConexaoDB conn = new ConexaoDB("localhost", "5432", "SkylineDB", "skyline_user", "12345");
    
    private boolean cpfExist(String cpf){
        try {
            this.conn.connect();
            String query = "SELECT COUNT(*) FROM Clientes WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(query);
            while(result.next()){
                return true;
            }
            this.conn.disconnect();
        } catch (SQLException e) { e.printStackTrace(); }
        
        return false;
    }
    
    public void cadastroCliente(String nome, String cpf){
        if(this.cpfExist(cpf)){
            this.conn.connect();
            String query = "INSERT INTO Clientes VALUES('"+ nome +"','"+ cpf +"','0.0');";
            this.conn.query(query);
        }
        this.conn.disconnect();
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
