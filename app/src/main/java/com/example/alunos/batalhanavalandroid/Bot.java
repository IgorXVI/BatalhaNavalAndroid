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
        int x, y;
        this.cerebro.ataque();
        x = this.cerebro.getPosUltimo()[0];
        y = this.cerebro.getPosUltimo()[1];
        super.realizarJogada(x, y, this.humano);
        this.cerebro.checkAcerto();
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
