package com.example.alunos.batalhanavalandroid;

import java.io.Serializable;

/**
 *
 * @author Alunos
 */
public class Jogador implements Serializable{

    private String nome;
    private Tabuleiro tabuleiro;
    private boolean temBomba;

    Jogador(String nome) {
        this.nome = nome;
        this.temBomba = true;
        this.tabuleiro = new Tabuleiro();
    }

    public void realizarJogada(int x, int y, Jogador adversario, boolean bomba) {
        if (bomba) {
            usarBomba(x, y, adversario);
        } else {
            atirar(x, y, adversario);
        }
    }

    private void usarBomba(int x, int y, Jogador adversario) {
        this.temBomba = false;
        int xc, yc, xb, yb, xe, ye, xd, yd;
        xc = x - 1;
        yc = y;
        xb = x + 1;
        yb = y;
        xe = x;
        ye = y - 1;
        xd = x;
        yd = y + 1;
        atirar(x, y, adversario);
        if (xc > -1) {
            atirar(xc, yc, adversario);
        }
        if (xb < 7) {
            atirar(xb, yb, adversario);
        }
        if (ye > -1) {
            atirar(xe, ye, adversario);
        }
        if (yd < 7) {
            atirar(xd, yd, adversario);
        }
    }

    private void atirar(int x, int y, Jogador adversario) {
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

    public boolean getTemBomba() {
        return this.temBomba;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setTabuleiro(Tabuleiro t){
        this.tabuleiro = t;
    }
}
