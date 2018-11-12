package com.example.alunos.batalhanavalandroid;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Bot extends Jogador implements Serializable {
    private AI cerebro;
    private Jogador humano;

    public Bot(Jogador humano) {
        super.setNome("Bot");
        this.cerebro = new AI(humano.getTabuleiro());
        this.humano = humano;
    }

    public void realizarJogada() {
        int[] pos = this.cerebro.ataque();
        super.realizarJogada(pos[0], pos[1], this.humano);
    }

    public Jogador getHumano(){
        return this.humano;
    }

    public void setHumano(Jogador novoHumano){
        this.humano = novoHumano;
    }

    public AI getCerebro(){
        return this.cerebro;
    }

    public void setCerebro(AI novoCerebro){
        this.cerebro = novoCerebro;
    }

}
