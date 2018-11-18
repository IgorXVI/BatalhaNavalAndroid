package com.example.alunos.batalhanavalandroid;

import java.io.Serializable;

public class Global implements Serializable {
    private static Jogador humano;
    private static Bot bot;
    private static String ultimaActivity;
    private static Global instance;

    Global(){ }

    public void setHumano(Jogador j) {
        Global.humano = j;
    }

    public Jogador getHumano(){
        return Global.humano;
    }

    public void setBot(Bot b){
        Global.bot = b;
    }

    public Bot getBot(){
        return Global.bot;
    }

    public void setUltimaActivity(String a){
        Global.ultimaActivity = a;
    }

    public String getUltimaActivity(){
        return Global.ultimaActivity;
    }

    public static synchronized Global getInstance(){
        if(instance == null){
            instance = new Global();
        }
        return instance;
    }

}


