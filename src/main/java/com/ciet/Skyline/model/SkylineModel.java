package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkylineModel {

    private final ConexaoDB conn = new ConexaoDB("localhost", "5432", "SkylineDB", "skyline_user", "12345");
    
    private boolean cpfExist(ConexaoDB conn, String cpf){
        try {
            String query = "SELECT COUNT(*) FROM Clientes WHERE cpf ='"+ cpf +"'";
            ResultSet result = conn.query(query);
            while(result.next()){
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        return false;
    }
    
    public void cadastroCliente(String nome, String cpf){
        conn.connect();
        
        if(this.cpfExist(conn, cpf)){
            String query = "INSERT INTO Clientes VALUES('"+ nome +"','"+ cpf +"','0.0')";
            conn.query(query);
        }
        conn.disconnect();
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
