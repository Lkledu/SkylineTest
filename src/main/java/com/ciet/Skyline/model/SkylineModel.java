package com.ciet.Skyline.model;

import com.ciet.Skyline.lib.ConexaoDB;

public class SkylineModel {

    private ConexaoDB conn = new ConexaoDB("localhost", "5432", "SkylineDB", "skyline_user", "12345");
    
    public void cadastroCliente(String nome, String cpf){
        conn.connect();



        conn.disconnect();
    }

    public void transferenciaSaldo(){
        conn.connect();



        conn.disconnect();
    }

    public void compraBTC(){
        conn.connect();



        conn.disconnect();
    }
}
