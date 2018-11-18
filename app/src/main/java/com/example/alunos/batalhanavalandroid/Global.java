package com.example.alunos.batalhanavalandroid;

import android.content.Intent;

import java.io.Serializable;

public class Global implements Serializable {
    private static Jogador humano;
    private static Bot bot;
    private static Intent ultimaActivityIntent;
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

    public void setUltimaActivityIntent(Intent i){
        Global.ultimaActivityIntent = i;
    }

    public Intent getUltimaActivityIntent(){
        return Global.ultimaActivityIntent;
    }

    public static synchronized Global getInstance(){
        if(instance == null){
            instance = new Global();
        }
        return instance;
    }

}


