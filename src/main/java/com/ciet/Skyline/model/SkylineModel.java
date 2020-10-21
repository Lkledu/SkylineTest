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

    public boolean creditarSaldo(String cpf, double saldo){
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
            return true;
        }catch(SQLException e){ e.printStackTrace();}
        this.conn.disconnect();
        return false;
    }

    public boolean compraBTC(String cpf, double valor){
        conn.connect();
        try{
            BTC btcPrice = RestHandler.getBTCDia();
            
            String qSelec = "SELECT id, saldo_real, saldo_btc FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet result = this.conn.query(qSelec);
            
            while(result.next()){ 
                int idCliente = result.getInt("id");
                double saldoRealAtual = result.getDouble("saldo_real");
                double saldoBtcAtual = result.getDouble("saldo_btc");
                double precoBtcTotal = btcPrice.data.amount * valor; //calcula a quantia em reais necessário para compra a partir da cotação e valor desejado
                
                if(saldoRealAtual >= precoBtcTotal){
                    double novoSaldoReal = saldoRealAtual - precoBtcTotal;
                    double novoSaldoBtc = saldoBtcAtual + valor;
                    
                    String queryu = "UPDATE \"public\".\"Clientes\" SET saldo_real="+ novoSaldoReal +", saldo_btc="+ novoSaldoBtc+" WHERE cpf='"+cpf+"';";
                    String queryi = "INSERT INTO \"public\".\"Transacao\"(id_Cliente, valor_real, valor_btc) VALUES("+idCliente+","+precoBtcTotal+", "+valor+");";
                    this.conn.update(queryu);
                    this.conn.update(queryi);
                    return true;    
                }
            }
        }catch(SQLException e){ e.printStackTrace(); }
        conn.disconnect();
        return false;
    }
    
    public double getsaldoReal(String cpf){
        try{
            double saldo = 0;
            String qSelec = "SELECT saldo_real FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(qSelec);
            while(rs.next()){
                saldo = rs.getDouble("saldo_real");
            }
            return saldo;
        }catch(SQLException e){ e.printStackTrace(); }
        
        return -1;
    }
    
    public double getSaldoBtc(String cpf){
        try{
            double saldo = 0;
            String qSelec = "SELECT saldo_btc FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(qSelec);
            while(rs.next()){
                saldo = rs.getDouble("saldo_btc");
            }
            return saldo;
        }catch(SQLException e){ e.printStackTrace(); }
        
        return -1;
    }
    
    public double valorTotalInvestido(String cpf){
        
        
        return -1;
    }
}
