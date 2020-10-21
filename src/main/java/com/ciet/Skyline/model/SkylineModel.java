package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        double saldo = 0;
        
        try{
            String qSelec = "SELECT saldo_real FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(qSelec);
            while(rs.next()){
                saldo = rs.getDouble("saldo_real");
            }
        }catch(SQLException e){ e.printStackTrace(); }
        
        return saldo;
    }
    
    public double getSaldoBtc(String cpf){
        double saldo = 0;
        
        try{
            String qSelec = "SELECT saldo_btc FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(qSelec);
            while(rs.next()){
                saldo = rs.getDouble("saldo_btc");
            }
        }catch(SQLException e){ e.printStackTrace(); }
        
        return saldo;
    }
    
    public double valorTotalInvestido(String cpf){
        double valor = 0;
        
        try{
            String query = "SELECT SUM(valor_real) as valor_total FROM \"public\".\"Transacao\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(query);
            while(rs.next()){
                valor = rs.getDouble("valor_total");
            }
        }catch(SQLException e){ e.printStackTrace(); }
        
        return valor;
    }
    
    public double lucroObtido(String cpf){
        double lucro = 0;
        double saldoBtc = 0;
        BTC btcPrice = RestHandler.getBTCDia();
        
        try{
            String qSelec = "SELECT saldo_btc FROM \"public\".\"Clientes\" WHERE cpf ='"+ cpf +"';";
            ResultSet rs = this.conn.query(qSelec);
            while(rs.next()){
                saldoBtc = rs.getDouble("saldo_btc");
            }
            
            
        }catch(SQLException e){ e.printStackTrace(); }
        
        return (btcPrice.data.amount * saldoBtc) - valorTotalInvestido(cpf);
    }
    
    public String historico(String cpf){
        Transacao transacao = new Transacao();
        ObjectMapper obj = new ObjectMapper();
        
        try{
            String query = "SELECT * FROM \"public\".\"Transacao\" WHERE cpf = '"+cpf+"' ORDER BY id DESC LIMIT 5;";
            ResultSet rs = this.conn.query(query);
            while(rs.next()){
                transacao.id = rs.getInt("id");
                transacao.idCliente = rs.getInt("id_cliente");
                transacao.valorReal = rs.getDouble("valor_real");
                transacao.valorBtc = rs.getDouble("valor_btc");
            }
        }catch(SQLException e){ e.printStackTrace();}
        return obj.writeValueAsString(transacao);
    }
}
