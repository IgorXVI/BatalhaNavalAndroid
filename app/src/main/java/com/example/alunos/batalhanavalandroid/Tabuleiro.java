package com.example.alunos.batalhanavalandroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alunos
 */
public class Tabuleiro implements Serializable{

    private char[][] tabuleiro, aux;
    private List<Navio> navios;

    Tabuleiro() {
        this.tabuleiro = new char[7][7];
        this.aux = new char[7][7];
        this.navios = new ArrayList<Navio>();
        this.navios.add(new Navio(2));
        this.navios.add(new Navio(3));
        this.navios.add(new Navio(4));
        this.gerarTabuleiro();
    }

    public boolean overlap(int tamanho1, int tamanho2) {
        double nt1, dt1, nt2, dt2, t1, t2, x1, y1, x2, y2, x3, y3, x4, y4, aux1;
        int a,b;

        a = tamanho1 - 2;
        b = tamanho2 - 2;

        x1 = navios.get(a).getPosInicial()[0];
        y1 = navios.get(a).getPosInicial()[1];
        x2 = navios.get(a).getPosFinal()[0];
        y2 = navios.get(a).getPosFinal()[1];
        x3 = navios.get(b).getPosInicial()[0];
        y3 = navios.get(b).getPosInicial()[1];
        x4 = navios.get(b).getPosFinal()[0];
        y4 = navios.get(b).getPosFinal()[1];

        if (navios.get(a).getVertical() == navios.get(b).getVertical()) {
            if (!navios.get(a).getVertical()) {
                if (x1 > x2) {
                    aux1 = x2;
                    x2 = x1;
                    x1 = aux1;
                }
                if (x3 > x4) {
                    aux1 = x4;
                    x4 = x3;
                    x3 = aux1;
                }
                return (x3 >= x1 && x3 <= x2) || (x1 >= x3 && x1 <= x4);
            } else {
                if (y1 > y2) {
                    aux1 = y2;
                    y2 = y1;
                    y1 = aux1;
                }
                if (y3 > y4) {
                    aux1 = y4;
                    y4 = y3;
                    y3 = aux1;
                }
                return (y3 >= y1 && y3 <= y2) || (y1 >= y3 && y1 <= y4);
            }
        }
        /*Aqui eu utilizo uma fórmula para ver se existe intersecção entre dois
        segmentos de reta.
        fonte: http://www.cs.swan.ac.uk/~cssimon/line_intersection.html */

        nt1 = (y3 - y4) * (x1 - x3) + (x4 - x3) * (y1 - y3);
        dt1 = (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3);
        t1 = nt1 / dt1;
        nt2 = (y1 - y2) * (x1 - x3) + (x2 - x1) * (y1 - y3);
        dt2 = (x4 - x3) * (y1 - y2) - (x1 - x2) * (y4 - y3);
        t2 = nt2 / dt2;
        return (t1 >= 0 && t1 <= 1) && (t2 >= 0 && t2 <= 1);
    }

    public void gerarTabuleiro() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                this.tabuleiro[i][j] = '~';
                this.aux[i][j] = '~';
            }
        }
        while (overlap(3, 4)) {
            navios.get(1).gerarPosicoes();
        }
        while (overlap(2, 3) || overlap(2, 4)) {
            navios.get(0).gerarPosicoes();
        }
        gerarTabuleiroAux();
    }

    public void gerarTabuleiroAux(){
        int xi, xf, yi, yf;
        for (int k = 0; k < 3; k++) {
            xi = navios.get(k).getPosInicial()[0];
            yi = navios.get(k).getPosInicial()[1];
            xf = navios.get(k).getPosFinal()[0];
            yf = navios.get(k).getPosFinal()[1];
            if (!navios.get(k).getVertical()) {
                if (xi < xf) {
                    for (int i = xi; i <= xf; i++) {
                        this.aux[i][yi] = (char) ('a'+k);
                    }
                } else {
                    for (int i = xf; i <= xi; i++) {
                        this.aux[i][yi] = (char) ('a'+k);
                    }
                }
            } else {
                if (yi < yf) {
                    for (int i = yi; i <= yf; i++) {
                        this.aux[xi][i] = (char) ('a'+k);
                    }
                } else {
                    for (int i = yf; i <= yi; i++) {
                        this.aux[xi][i] = (char) ('a'+k);
                    }
                }
            }
        }
    }

    public boolean navioDestruido(int tamanho){
        Navio n = this.navios.get(tamanho - 2);
        int x, y;
        for(int i = 0; i < tamanho; i++){
            x = n.getPosicoes()[i][0];
            y = n.getPosicoes()[i][1];
            if(this.tabuleiro[x][y] != 'X'){
                return false;
            }
        }
        return true;
    }

    public boolean posParteNavio(int x, int y, int tamanho){
        Navio n = this.navios.get(tamanho - 2);
        int x1, y1;
        for(int i = 0; i < tamanho; i++){
            x1 = n.getPosicoes()[i][0];
            y1 = n.getPosicoes()[i][1];

            if(x == x1 && y == y1){
                return true;
            }
        }
        return false;
    }

    public boolean posParteNavioDestruido(int x, int y){
        for(int i = 2; i <= 4; i++){
            if(navioDestruido(i) && posParteNavio(x, y, i)){
                return true;
            }
        }
        return false;
    }

    public boolean todosNaviosDestruidos(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(this.aux[i][j] != '~' && this.tabuleiro[i][j] != 'X'){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean posCercada(int x, int y){
        boolean semEsquerda = posJaAtacada(x-1, y);
        boolean semDireita = posJaAtacada(x+1, y);
        boolean semBaixo = posJaAtacada(x, y-1);
        boolean semCima = posJaAtacada(x, y+1);

        return semCima && semBaixo && semDireita && semEsquerda;
    }

    public boolean posJaAtacada(int x, int y){
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return true;
        }
        char c = this.tabuleiro[x][y];
        return c != '~';
    }

    public boolean posJaErrada(int x, int y){
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return true;
        }
        char c = this.tabuleiro[x][y];
        return c == '*';
    }

    public boolean posJaAcertada(int x, int y){
        if(x > 6 || x < 0 || y > 6 || y < 0){
            return false;
        }
        char c = this.tabuleiro[x][y];
        return c == 'X';
    }

    public boolean posObstaculo(int x, int y){
        return this.posParteNavioDestruido(x, y) || this.posJaErrada(x, y);
    }

    public boolean posInutil(int x, int y){
        return this.posJaAtacada(x, y) || this.posCercada(x, y);
    }

    public boolean jaTemAcerto(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                if(posJaAcertada(i,j)){
                    return true;
                }
            }
        }
        return false;
    }

    public Navio getNavio(int tamanho){ return this.navios.get(tamanho -2 ); }

    public char[][] getTabuleiroDoJogador(){
        return this.aux;
    }

    public char[][] getTabuleiroPublico(){
        return this.tabuleiro;
    }

    public void setAcerto(int x, int y){
        this.tabuleiro[x][y] = 'X';
    }

    public void setErro(int x, int y){
        this.tabuleiro[x][y] = '*';
    }

    public void setNavio(Navio n) {
        int index  = n.getTamanho() - 2;
        this.navios.set(index, n);
    }

}
