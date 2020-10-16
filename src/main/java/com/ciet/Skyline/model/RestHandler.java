package com.ciet.Skyline.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URL;

public class RestHandler {
    public static BTC getBTCDia(){
       try{
            ObjectMapper obj = new ObjectMapper();
            BTC btcPrice = obj.readValue(new URL("https://api.coinbase.com/v2/prices/spot?currency=BRL"), BTC.class);
            return btcPrice;
       }catch(Exception e){ e.printStackTrace(); }
       return null;
    }
}
