package com.example.alunos.batalhanavalandroid;

import java.io.Serializable;

/**
 *
 * @author Alunos
 */
public class Jogador implements Serializable{

    private String nome;
    private Tabuleiro tabuleiro;

    Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro();
    }

    public void realizarJogada(int x, int y, Jogador adversario) {
        if (adversario.getTabuleiro().getTabuleiroDoJogador()[x][y] == '~') {
            adversario.getTabuleiro().setErro(x, y);
        } else {
            adversario.getTabuleiro().setAcerto(x, y);
        }
    }

    public Tabuleiro getTabuleiro() {
        return this.tabuleiro;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setTabuleiro(Tabuleiro t){
        this.tabuleiro = t;
    }
}
