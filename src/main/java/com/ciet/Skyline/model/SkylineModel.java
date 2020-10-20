package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SkylineModel {

    private final ConexaoDB conn = new ConexaoDB();
    
    private boolean cpfExist(String cpf){
        try {
            this.conn.connect();
            String query = "SELECT COUNT(*) FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(query);
            result.next();
            if(result.getInt(1)>0){ 
                this.conn.disconnect();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.conn.disconnect();
        return false;
    }
    
    public boolean cadastroCliente(String nome, String cpf){
        boolean response = false;
        
        if(!this.cpfExist(cpf)){
            this.conn.connect();
            String query = "INSERT INTO \"public\".\"Clientes\"(nome, cpf, saldo_real, saldo_btc) VALUES('"+ nome +"','"+ cpf +"','0.0', 0.0);";
            this.conn.update(query);
            response = true;
            this.conn.disconnect();
        }
        return response;
    }

    public void creditarSaldo(String cpf, double saldo){
        this.conn.connect();
        try{
            double saldoAtual;
            String qSelec = "SELECT saldo_real FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(qSelec);
            while(result.next()){ 
                saldoAtual = result.getDouble("saldo_real");
                saldo += saldoAtual;
            }
            String query = "UPDATE \"public\".\"Clientes\" SET saldo_real="+ saldo+" WHERE cpf='"+cpf+"';";
            this.conn.update(query);
        }catch(SQLException e){ e.printStackTrace(); }
        finally{this.conn.disconnect();}
    }

    public boolean compraBTC(String cpf, double valor){
        conn.connect();
        try{
            BTC btcPrice = RestHandler.getBTCDia();
            double saldoAtual;
            
            String qSelec = "SELECT saldo_real FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(qSelec);
            
            while(result.next()){ 
                saldoAtual = result.getDouble("saldo_real");
                double quantiaBtc = btcPrice.data.amount * valor; //calcula a quantia em btc a partir da cotação e valor desejado
                if(saldoAtual >= quantiaBtc){
                    double novoSaldo = saldoAtual - quantiaBtc;
                    String query = "UPDATE \"public\".\"Clientes\" SET saldo_real="+ novoSaldo +", saldo_btc="+ valor+" WHERE cpf='"+cpf+"';";
                    this.conn.update(query);
                    return true;    
                }
                conn.disconnect();
                return false;
            }
        }catch(SQLException e){ e.printStackTrace(); }
        conn.disconnect();
        return false;
    }
}
