package com.eai.wizard.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eai.dto.Constants;
import com.eai.wizard.model.LevelTwo;

public class UtilPage {
    
    public void waitInRetries(int time){
        try {
            Thread.sleep(time);
        } catch (Exception ex) {
            Logger.getLogger(UtilPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String url(String abc){
        StringBuilder urlString = new StringBuilder();
        
        urlString
                .append(Constants.BASEURL.val())
                .append("gp/search/other/ref=sr_in_a_-2?rh=i%3Afashion-mens-watches%2Cn%3A7141123011%2Cn%3A7147441011%2Cn%3A6358539011%2Cn%3A6358540011%2Ck%3Awatch&bbn=6358540011&keywords=watch&pickerToList=lbr_brands_browse-bin&indexField=")
                .append(abc)
                .append("&ie=UTF8&qid=1521131922");
        
        return  urlString.toString();
    }
    
    public List<String>urlPage(LevelTwo levelTwo){
        int number = levelTwo.getAmount();
        
        List<String> urlPage= new ArrayList<>();
        
        if(number > 48){
            int num = (int) Math.ceil((float)number/48);
            for(int i=1; i <= num; i++){
                urlPage.add(levelTwo.getUrl() + "&page=" + (i));
            }
        }else{
             urlPage.add(levelTwo.getUrl());
        }
        
        return urlPage;
    }
}