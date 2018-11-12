package com.example.alunos.batalhanavalandroid;

import android.content.Context;

public class Global {
    private static Jogador humano;
    private static Bot bot;
    private static Global instance;
    private static ArquivoJogo arquivoJogo;

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

    public void setArquivoJogo(ArquivoJogo a){ Global.arquivoJogo = a; }

    public ArquivoJogo getArquivoJogo(){ return Global.arquivoJogo; }

    public static synchronized Global getInstance(){
        if(instance == null){
            instance = new Global();
        }
        return instance;
    }

}


