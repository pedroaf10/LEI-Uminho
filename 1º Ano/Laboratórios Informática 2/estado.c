//
// Created by pja on 28/02/2019.
//
#include <stdio.h>
#include "estado.h"

/**
 * Dá a pontuação de cada jogador.
 * @param e Estado atual.
 * @param contO Contador de peças O.
 * @param contX Contador de peças X.
 */
void score (ESTADO e, int *contO, int *contX){
    int contB = 0, contP = 0;
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            switch (e.grelha[i][j]) {
                case VALOR_O:{
                    contB++;
                    break;
                }
                case VALOR_X: {
                    contP++;
                    break;
                }
                case VAZIA:{
                }
            }
        }
    }
    *contO = contB;
    *contX = contP;
}

// exemplo de uma função para imprimir o estado (Tabuleiro)
void printa(ESTADO e, int Modo, int Turno)
{
    char c = ' ';
    int cont = 1, contO = 0, contX = 0 ;

    if (Modo == 1) {
        printf("M");
    }
    else {printf("A");}
    if (Turno % 2){
        printf(" O");
    }
    else {printf (" X");}

    printf("\n");
    printf("  1 2 3 4 5 6 7 8\n");
    for (int i = 0; i < 8; i++) {
        printf("%i ", cont);
        cont++;
        for (int j = 0; j < 8; j++) {
            switch (e.grelha[i][j]) {
                case VALOR_O: {
                    c = 'O';
                    break;
                }
                case VALOR_X: {
                    c = 'X';
                    break;
                }
                case VAZIA: {
                    c = '-';
                    break;
                }
            }
            printf("%c ", c);

        }
        printf("\n");
    }
    score(e, &contO, &contX);
    printf("Resultado: O:%d X:%d\n", contO, contX);

}

