package com.ciet.Skyline.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BTC {
    /*
    public String base;
    public String currency;
    public double amount;
    */
    public static String getBTCDia(){
       //https://api.coinbase.com/v2/prices/spot?currency=BRL
       String output,json=""; 
       try{
            URL url = new URL("https://api.coinbase.com/v2/prices/spot?currency=BRL");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                System.out.print("HTTP error code : " + connection.getResponseCode());
                
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((output = br.readLine()) != null) { json+= output; }

                connection.disconnect();
            }
       }catch(Exception e){
           e.printStackTrace();
       }
       return json;
    }
}