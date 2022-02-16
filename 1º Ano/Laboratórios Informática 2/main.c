#include <stdio.h>
#include "estado.h"
#include <ctype.h>
#include <stdlib.h>

//Utilidade
/**
 * Guarda elementos numa lista de saves.
 * @param save save apontador para o inicio da stack onde os estados estão guardados.
 * @param e Estado atual.
 * @param Turno turno atual do jogo.
 */
void push(saves **save, ESTADO e, int Turno) {
    saves *novo;
    novo = malloc(sizeof(saves));
    novo->e = e;
    novo->turno = Turno;
    novo->prox = *save;
    *save = novo;
}

/**
 * Retira o último elemento da lista.
 * @param save apontador para o inicio da stack onde os estados estão guardados.
 * @param inicial estado atual do jogo.
 * @param Turno turno do jogo.
 */
void pop(saves **save, ESTADO *inicial, int *Turno) {
    saves *primeira = NULL;
    if (save == NULL) {
        printf("Não é possivel voltar para trás!");
    } else {
        primeira = (*save)->prox;
        *inicial = (*save)->e;
        *Turno = (*save)->turno;
        free(*save);
        *save = primeira;
    }
}

/**
 * Atualiza a peça.
 * @param e Estado atual.
 * @param Turno turno atual do jogo.
 */
void uppeca(ESTADO *e, int Turno) {
    if (Turno % 2) {
        e->peca = VALOR_O;
    } else { e->peca = VALOR_X; }
}

//Validar

/**
 * Verifica se existe uma jogada válida na vertical.
 * @param e Estado atual.
 * @param l Linha do tabuleiro.
 * @param c Coluna tabuleiro.
 * @return 1 se houver jogada válida para cima, 2 se houver para baixo e 3 se houver para cima e baixa.
 */
int validaVertical(ESTADO *e, int l, int c) {
    int la = l, res = 0, resC = 0, resB = 0, cont = 0;
    if ((e->grelha[(l - 1)][c] == e->peca || e->grelha[(l - 1)][c] == VAZIA)
        && (e->grelha[(l + 1)][c] == e->peca || e->grelha[(l + 1)][c] == VAZIA)) {
        res = 0;
    } else {
        while ((la - 1) >= 0) {
            if ((e->grelha[la - 1][c] != VAZIA) && (e->grelha[la - 1][c] != e->peca)) {
                cont++;
            }
            if ((e->grelha[la - 1][c] == e->peca) && cont > 0) {
                resC = 1;
                break;
            }
            if ((e->grelha[la - 1][c] == VAZIA) || (e->grelha[la - 1][c] == e->peca && cont == 0)) {
                resC = 0;
                break;
            }
            la--;
        }
        la = l;
        cont = 0;
        while (la + 1 <= 7) {
            if ((e->grelha[la + 1][c] != VAZIA) && (e->grelha[la + 1][c] != e->peca)) {
                cont++;
            }
            if ((e->grelha[la + 1][c] == e->peca) && cont > 0) {
                resB = 2;
                break;
            }
            if ((e->grelha[la + 1][c] == VAZIA) || (e->grelha[la - 1][c] == e->peca && cont == 0)) {
                resB = 0;
                break;
            }
            la++;
        }
        res = resB + resC;
    }
    return res;
}


/**
 * Verifica se existe uma jogada válida na horizontal.
 * @param e Estado atual.
 * @param l Linha do tabuleiro.
 * @param c Coluna tabuleiro.
 * @return 1 se houver jogada válida para esquerda, 2 se houver para direita e 3 se houver para esquerda e direita.
 */
int validaHorizontal(ESTADO *e, int l, int c) {
    int ca = c, res = 0, resD = 0, resE = 0, cont = 0;
    if ((e->grelha[l][(c - 1)] == e->peca || e->grelha[l][(c - 1)] == VAZIA)
        && (e->grelha[l][(c + 1)] == e->peca || e->grelha[l][(c + 1)] == VAZIA)) {
        res = 0;
    } else {
        while ((ca - 1) >= 0) {
            if (e->grelha[l][ca - 1] != e->peca && (e->grelha[l][ca - 1] != VAZIA)) {
                cont++;
            }
            if ((e->grelha[l][ca - 1] == e->peca) && cont > 0) {
                resE = 1;
                break;
            }
            if ((e->grelha[l][ca - 1] == e->peca && cont == 0) || (e->grelha[l][ca - 1] == VAZIA)) {
                resE = 0;
                break;
            }
            ca--;
        }
        ca = c;
        cont = 0;
        while ((ca + 1) <= 7) {
            if (e->grelha[l][ca + 1] != e->peca && (e->grelha[l][ca + 1] != VAZIA)) {
                cont++;
            }
            if (e->grelha[l][ca + 1] == e->peca && cont > 0) {
                resD = 2;
                break;
            }
            if ((e->grelha[l][ca + 1] == e->peca && cont == 0) || (e->grelha[l][ca + 1] == VAZIA)) {
                resD = 0;
                break;
            }
            ca++;
        }
        res = resD + resE;
    }
    return res;
}


/**
 * Verifica se existe uma jogada válida nas diagonais.
 * @param e Estado atual.
 * @param l Linha do tabuleiro.
 * @param c Coluna tabuleiro.
 * @return Cada diagonal tem umm determinado valor, de modo que o return dá a soma dos volores em que existem jogadas válidas na diagonal.
 */
int validaDiagonal(ESTADO *e, int l, int c) {
    int la = l, ca = c, res = 0, resCD = 0, resCE = 0, resBD = 0, resBE = 0, cont = 0;
    if ((e->grelha[(l - 1)][(c - 1)] == e->peca || e->grelha[(l - 1)][(c - 1)] == VAZIA)
        && (e->grelha[(l + 1)][(c + 1)] == e->peca || e->grelha[(l + 1)][(c + 1)] == VAZIA)
        && (e->grelha[(l - 1)][(c + 1)] == e->peca || e->grelha[(l - 1)][(c + 1)] == VAZIA)
        && (e->grelha[(l + 1)][(c - 1)] == e->peca || e->grelha[(l + 1)][(c - 1)] == VAZIA)) {
        res = 0;
    } else {
        while ((la - 1 >= 0) && (ca - 1 >= 0)) {
            if (e->grelha[la - 1][ca - 1] != e->peca && (e->grelha[la - 1][ca - 1] != VAZIA)) {
                cont++;
            }
            if (e->grelha[la - 1][ca - 1] == e->peca && cont > 0) {
                resCE = 1;
                break;
            }
            if ((e->grelha[la - 1][ca - 1] == e->peca && cont == 0) || (e->grelha[la - 1][ca - 1] == VAZIA)) {
                resCE = 0;
                break;
            }
            la--, ca--;
        }
        la = l;
        ca = c;
        cont = 0;
        while ((la + 1 <= 7) && (ca + 1 <= 7)) {
            if (e->grelha[la + 1][ca + 1] != e->peca && (e->grelha[la + 1][ca + 1] != VAZIA)) {
                cont++;
            }
            if (e->grelha[la + 1][ca + 1] == e->peca && cont > 0) {
                resBD = 30;
                break;
            }
            if ((e->grelha[la + 1][ca + 1] == e->peca && cont == 0) || (e->grelha[la + 1][ca + 1] == VAZIA)) {
                resBD = 0;
                break;
            }
            la++, ca++;
        }
        la = l;
        ca = c;
        cont = 0;
        while ((la - 1 >= 0) && (ca + 1 <= 7)) {
            if (e->grelha[la - 1][ca + 1] != e->peca && (e->grelha[la - 1][ca + 1] != VAZIA)) {
                cont++;
            }
            if (e->grelha[la - 1][ca + 1] == e->peca && cont > 0) {
                resCD = 50;
                break;
            }
            if ((e->grelha[la - 1][ca + 1] == e->peca && cont == 0) || (e->grelha[la - 1][ca + 1] == VAZIA)) {
                resCD = 0;
                break;
            }
            la--, ca++;
        }
        la = l;
        ca = c;
        cont = 0;
        while ((la + 1 <= 7) && (ca - 1 >= 0)) {
            if (e->grelha[la + 1][ca - 1] != e->peca && (e->grelha[la + 1][ca - 1] != VAZIA)) {
                cont++;
            }
            if (e->grelha[la + 1][ca - 1] == e->peca && cont > 0) {
                resBE = 10;
                break;
            }
            if ((e->grelha[la + 1][ca - 1] == e->peca && cont == 0) || (e->grelha[la + 1][ca - 1] == VAZIA)) {
                resBE = 0;
                break;
            }
            la++, ca--;
        }
        res = resCD + resBD + resBE + resCE;
    }
    return res;
}


/**
 * Verifica se uma jogada numa determinada linha l e coluna c é válida.
 * @param e Estado atual.
 * @param l Linha do tabuleiro.
 * @param c Coluna do tabuleiro.
 * @return 0 se não houver jogada válida ou outro número se for válida.
 */
int jogadaValida(ESTADO *e, int l, int c) {
    int resV = 0, resH = 0, resD = 0, Valido = 0;
    if (e->grelha[l][c] == VAZIA) {
        resV = validaVertical(e, l, c);
        resH = validaHorizontal(e, l, c);
        resD = validaDiagonal(e, l, c);
        Valido = resD + resH + resV;
    } else { Valido = 0; }
    return Valido;
}


/**
 * Verifica se é possivel continuar a jogar.
 * @param e Estado atual.
 * @param Turno turno atual do jogo.
 * @return 1 se puder continuar a jogar, 0 caso contrário.
 */
int Continuar(ESTADO e, int Turno) {
    int valida = 0;
    uppeca(&e, Turno);
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            if (jogadaValida(&e, i, j)) {
                valida = 1;
            }
        }
    }
    return valida;
}


/**
 * Muda o turno consoante a peça selecionada.
 * @param Turno turno atual do jogo.
 * @param letra Peça do tabuleiro.
 */
void NovoJogo(int *Turno, char letra) {
    if (letra == 'X') {
        *Turno = 0;
    }
    if (letra == 'O') {
        *Turno = 1;
    }
}


// Tabuleiro

/**
 * Cria o tabuleiro inicial.
 * @param e Estado atual.
 * @param M Modo do jogo.
 * @param Turno turno do jogo.
 */
void tabuleiroI(ESTADO *e, int M, int Turno) {
    int l = 0, c = 0;
    while (l < 8) {
        while (c < 8) {
            e->grelha[l][c] = VAZIA;
            c++;
        }
        c = 0;
        l++;
    }
    // estado inicial do tabuleiro. Inicio do jogo!
    e->grelha[3][4] = VALOR_X;
    e->grelha[4][3] = VALOR_X;
    e->grelha[3][3] = VALOR_O;
    e->grelha[4][4] = VALOR_O;
    printf("\n");
    printa(*e, M, Turno);
    printf("\n");
}


/**
 * Coloca pontos no tabuleiro nos locais onde é possivel jogar.
 * @param e Estado atual.
 * @param Modo modo atual.
 * @param Turno turno atual do jogo.
 */
void Possibilidade(ESTADO e, int Modo, int Turno) {     //coloca pontos nas jogadas válidas.
    char c = '6';
    int cont = 1;

    if (Modo == 1) {
        printf("M");
    } else { printf("A"); }
    if (Turno % 2) {
        printf(" O");
    } else { printf(" X"); }
    printf("\n");
    printf("  1 2 3 4 5 6 7 8\n");
    uppeca(&e, Turno);
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
            if (jogadaValida(&e, i, j)) {
                c = '.';
            }
            printf("%c ", c);

        }
        printf("\n");
    }
}


// Jogar
/**
 * Joga na posição dada.
 * @param e Estado atual.
 * @param l Linha do tabuleiro.
 * @param c Coluna do tabuleiro
 */
void Joga(ESTADO *e, int l, int c) {
    int la = l, ca = c, v, h, d;
    e->grelha[l][c] = e->peca;
    v = validaVertical(e, l, c);
    h = validaHorizontal(e, l, c);
    d = validaDiagonal(e, l, c);
    switch (v) {
        case (3): {
            while (((e->grelha[la - 1][c] != e->peca) && (e->grelha[la - 1][c] != VAZIA)) && la > 0) {
                e->grelha[la - 1][c] = e->peca;
                la--;
            }
            la = l;
            while ((e->grelha[la + 1][c] != e->peca && (e->grelha[la + 1][c] != VAZIA)) && la < 7) {
                e->grelha[la + 1][c] = e->peca;
                la++;
            }
            la = l;
            break;
        }
        case (1): {
            while (((e->grelha[la - 1][c] != e->peca) && (e->grelha[la - 1][c] != VAZIA)) && la > 0) {
                e->grelha[la - 1][c] = e->peca;
                la--;
            }
            la = l;
            break;
        }
        case (2): {
            while (((e->grelha[la + 1][c] != e->peca) && (e->grelha[la + 1][c] != VAZIA)) && la < 7) {
                e->grelha[la + 1][c] = e->peca;
                la++;
            }
            la = l;
            break;
        }
        default: {
        }
    }
    switch (h) {
        case (3): {
            while ((e->grelha[l][ca - 1] != e->peca && (e->grelha[l][c - 1] != VAZIA)) && ca > 0) {
                e->grelha[l][ca - 1] = e->peca;
                ca--;
            }
            ca = c;
            while ((e->grelha[l][ca + 1] != e->peca && (e->grelha[l][c + 1] != VAZIA)) && ca < 7) {
                e->grelha[l][ca + 1] = e->peca;
                ca++;
            }
            ca = c;
            break;
        }
        case (1): {
            while (((e->grelha[l][ca - 1] != e->peca) && (e->grelha[l][ca - 1] != VAZIA)) && ca > 0) {
                e->grelha[l][ca - 1] = e->peca;
                ca--;
            }
            ca = c;
            break;
        }
        case (2): {
            while (((e->grelha[l][ca + 1] != e->peca) && (e->grelha[l][ca + 1] != VAZIA)) && ca < 7) {
                e->grelha[l][ca + 1] = e->peca;
                ca++;
            }
            ca = c;
            break;
        }
        default: {
        }
    }
    switch (d) {
        case (91): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            break;
        }
        case (1): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            break;
        }
        case (10): {
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            break;
        }
        case (30): {
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            break;
        }
        case (50): {
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (11): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            break;
        }
        case (31): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            break;
        }
        case (51): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (40): {
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            break;
        }
        case (60): {
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (80): {
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (41): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            break;
        }
        case (61): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (81): {
            while ((e->grelha[la - 1][ca - 1] != VAZIA && e->grelha[la - 1][ca - 1] != e->peca) && la > 0 && ca > 0) {
                e->grelha[la - 1][ca - 1] = e->peca;
                la--, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        case (90): {
            while ((e->grelha[la + 1][ca - 1] != VAZIA && e->grelha[la + 1][ca - 1] != e->peca) && la < 7 && ca > 0) {
                e->grelha[la + 1][ca - 1] = e->peca;
                la++, ca--;
            }
            la = l;
            ca = c;
            while ((e->grelha[la + 1][ca + 1] != VAZIA && e->grelha[la + 1][ca + 1] != e->peca) && la < 7 && ca < 7) {
                e->grelha[la + 1][ca + 1] = e->peca;
                la++, ca++;
            }
            la = l;
            ca = c;
            while ((e->grelha[la - 1][ca + 1] != VAZIA && e->grelha[la - 1][ca + 1] != e->peca) && la > 0 && ca < 7) {
                e->grelha[la - 1][ca + 1] = e->peca;
                la--, ca++;
            }
            break;
        }
        default: {
        }
    }
}

/**
 * Calcula a jogada que produz maior diferença de pontos num turno especifico.
 * @param e Estado atual.
 * @param pbot Peça que vai jogar.
 * @return Diferença de pontuação maxima entre as duas peças.
 */
int jogamax(ESTADO *e, char pbot) {
    ESTADO temp = *e;
    if (pbot == 'X') {
        temp.peca = VALOR_X;
    } else {
        temp.peca = VALOR_O;
    }
    int scoremax = -69, scoreP, scoreI, scoreT, l = 0, c = 0;
    int i, j;
    for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
            if (jogadaValida(&temp, i, j)) {
                Joga(&temp, i, j);
                if (pbot == 'X') {
                    score(temp, &scoreI, &scoreP);
                }
                if (pbot == 'O') {
                    score(temp, &scoreP, &scoreI);
                }
                scoreT = scoreP - scoreI;
                if (scoremax < scoreT) {
                    l = i;
                    c = j;
                    scoremax = scoreT;
                }
                temp = *e;
            }
        }
    }
    Joga(e, l, c);
    return scoremax;
}

/**
 * Faz o bot jogar.
 * @param e Estado atual.
 * @param lvl nivel do bot.
 * @param pbot peça do bot.
 */
void jogadaBot(ESTADO *e, int lvl, char pbot) {
    ESTADO temp = *e;
    char antiP;
    int i, j, la, ca, l = 0, c = 0, scorePbot = 0, scorevs = 0, scoreMax = -149, scorefuturo = 0, contRonda = 0;
    if (pbot == 'X') antiP = 'O';
    else antiP = 'X';
    for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
            if (jogadaValida(&temp, i, j)) {
                la = i;
                ca = j;
                Joga(&temp, la, ca);
                while (contRonda <= ((3 * lvl) + 1)) {           //joga e altera o pbot para a peca oposta ,
                    if (contRonda % 2) {
                        contRonda++;
                        scorefuturo = jogamax(&temp, pbot);
                    }//usar as posições para aumentar ou diminur o score
                    else {
                        contRonda++;
                        scorefuturo = jogamax(&temp, antiP);
                    }
                }
                if (pbot == 'O') {
                    score(temp, &scorePbot, &scorevs);
                }
                if (pbot == 'X') {
                    score(temp, &scorevs, &scorePbot);
                }
                if ((i == 0 && j == 0) || (i == 0 && j == 7) || (i == 7 && j == 0) || (i == 7 && j == 7)) {
                    scorePbot += ((99 / 3) * lvl);
                } else if ((i == 0 && j == 1) || (i == 0 && j == 6) || (i == 1 && j == 0) || (i == 1 && j == 7) ||
                           (i == 6 && j == 0) || (i == 7 && j == 1) || (i == 7 && j == 6) || (i == 6 && j == 7)) {
                    scorePbot += ((-8 / 3) * lvl);
                } else if ((i == 1 && j == 1) || (i == 1 && j == 6) || (i == 6 && j == 1) || (i == 6 && j == 6)) {
                    scorePbot += ((-24 / 3) * lvl);
                } else if ((i == 0 && j == 2) || (i == 0 && j == 5) || (i == 2 && j == 0) || (i == 2 && j == 7) ||
                           (i == 5 && j == 0) || (i == 7 && j == 2) || (i == 7 && j == 5) || (i == 5 && j == 7)) {
                    scorePbot += ((8 / 3) * lvl);
                } else if ((i == 1 && j == 2) || (i == 2 && j == 1) || (i == 5 && j == 1) || (i == 6 && j == 2) ||
                           (i == 6 && j == 5) || (i == 5 && j == 6) || (i == 2 && j == 6) || (i == 1 && j == 5)) {
                    scorePbot += ((-4 / 3) * lvl);
                } else if ((i == 2 && j == 2) || (i == 2 && j == 5) || (i == 5 && j == 2) || (i == 5 && j == 5)) {
                    scorePbot += ((7 / 3) * lvl);
                } else if ((i == 0 && j == 3) || (i == 0 && j == 4) || (i == 3 && j == 0) || (i == 4 && j == 0) ||
                           (i == 3 && j == 7) || (i == 4 && j == 7) || (i == 7 && j == 3) || (i == 7 && j == 4)) {
                    scorePbot += ((6 / 3) * lvl);
                } else if ((i == 1 && j == 4) || (i == 4 && j == 1) || (i == 6 && j == 3) || (i == 3 && j == 6) ||
                           (i == 4 && j == 6) || (i == 6 && j == 4)) {
                    scorePbot += ((-3 / 3) * lvl);
                } else if ((i == 2 && j == 3) || (i == 2 && j == 4) || (i == 3 && j == 2) || (i == 4 && j == 2) ||
                           (i == 5 && j == 3) ||
                           (i == 5 && j == 4) || (i == 3 && j == 5) || (i == 4 && j == 5)) {
                    scorePbot += ((4 / 3) * lvl);
                }
                if ((scorePbot-scorefuturo) > scoreMax) {
                    l = i;
                    c = j;
                    scoreMax = scorePbot;
                }
                temp = *e;
            }
        }
    }
    Joga(e, l, c);
}


/**
 * Joga na primeira posição válida que encontrar.
 * @param e Estado atual.
 */
void PrimeiraJogada(ESTADO *e, char peca) {
    ESTADO temp = *e;
    if (peca == 'X') {
        temp.peca = VALOR_X;
    } else {
        temp.peca = VALOR_O;
    }
    char p;
    int scoremax = -69, scoreP, scoreI, scoreT, l = 0, c = 0, cont = 1;
    int i, j;
    for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
            if (jogadaValida(&temp, i, j)) {
                Joga(&temp, i, j);
                if (peca == 'X') {
                    score(temp, &scoreI, &scoreP);
                }
                if (peca == 'O') {
                    score(temp, &scoreP, &scoreI);
                }
                scoreT = scoreP - scoreI;
                if (scoremax < scoreT) {
                    l = i;
                    c = j;
                    scoremax = scoreT;
                }
                temp = *e;
            }
        }
    }
    printf("  1 2 3 4 5 6 7 8\n");
    for (int g = 0; g < 8; g++) {
        printf("%i ", cont);
        cont++;
        for (int h = 0; h < 8; h++) {
            switch (e->grelha[g][h]) {
                case VALOR_O: {
                    p = 'O';
                    break;
                }
                case VALOR_X: {
                    p = 'X';
                    break;
                }
                case VAZIA: {
                    p = '-';
                    break;
                }
            }
            if (g == l && h == c) {
                p = '?';
            }
            printf("%c ", p);

        }
        printf("\n");
    }
    printf("%d %d\n", l, c);
}


//Ficheiros
/**
 * Guarda um ficheiro com o Estado atual do jogo.
 * @param e Estado atual.
 * @param nome Nome do ficheiro.
 */
void escreve(ESTADO e, char nome[]) {
    FILE *hardsave = fopen(nome, "w");
    char c = ' ', p = ' ';
    if (e.peca == VALOR_O) { p = 'O'; }
    else { p = 'X'; }
    if (e.modo > 0) {
        fprintf(hardsave, "A %c %c \n", p, e.modo);
    } else {
        fprintf(hardsave, "M %c \n", p);
    }
    for (int i = 0; i < 8; i++) {
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
            fprintf(hardsave, "%c ", c);
        }
        fprintf(hardsave, "\n");
    }
    fclose(hardsave);

}

/**
 * Lê um ficheiro e continua o jogo a partir dele.
 * @param e Estado atual.
 * @param nome Nome do ficheiro.
 */
void ler(ESTADO *e, char nome[],char *pbot,int *Turno) {
    char t = ' ', p = ' ', c = ' ';
    char str[68];
    char nivel;
    FILE *hardsave = fopen(nome, "r");
    if (hardsave == NULL) {
        printf("Não tem nada guardado!");

    } else {
        fgets(str, 50, hardsave);
        sscanf(str, "%c", &t);
        if (t == 'A'){
            sscanf(str,"%c %c %c", &t,&p,&nivel);
            e->modo = nivel;
        }
        else if (t == 'M')
        {
            sscanf(str,"%c %c",&t,&p);
            e->modo = 0;
        }

        e->peca = (p == 'X' ? VALOR_X : VALOR_O);
        *pbot = (p == 'X' ? 'O' : 'X');
        if (e->peca == VALOR_X) {
            *Turno = 0;
        } else {
            *Turno = 1;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                fscanf(hardsave, "%c ", &c);
                switch (c) {
                    case 'X': {
                        e->grelha[i][j] = VALOR_X;
                        break;
                    }
                    case 'O': {
                        e->grelha[i][j] = VALOR_O;
                        break;
                    }
                    case '-': {
                        e->grelha[i][j] = VAZIA;
                        break;
                    }
                }
            }
        }
    }
    fclose(hardsave);
}

int main() {
    ESTADO e = {0};
    saves *saves = {0};
    char linha[50];
    char com, In, pbot = ' ', peca = ' ', lvl = '0';
    int c, l, contO = 0, contX = 0, M = 1, Turno = -1, contu = 0;
    do {
        if (Turno < 0) {
            printf("Insira um comando!\n");
            printf((" 👎(N) <peça> para novo jogo.\n (J) <l> <c> para jogar na linha l e coluna c.\n (L) <ficheiro> para ler ficheiro.\n"
                    " (E) <ficheiro> para guardar estado jogo.\n (S) para jogadas válidas.\n (H) para sugestão de jogada.\n (U) para desfazer.\n"
                    " (A) <peça> <nível> novo jogo contra bot.\n (Q) para sair.\n"));
        }
        if ((M != 1 || e.modo != 0) && ((pbot == 'X' && (Turno % 2) == 0) || (pbot == 'O' && (Turno % 2))) && Continuar(e, Turno)) {
            uppeca(&e, Turno);
            jogadaBot(&e, lvl, pbot);
            printa(e, M, Turno);
            Turno++;
            if (Continuar(e, (Turno)) == 0) {
                Turno++;
                printf("O turno foi passado!\n");
                if (Continuar(e, (Turno)) == 0) {
                    printf("Jogo acabou.\n");
                    score(e, &contO, &contX);
                    if (contO > contX) printf("O jogador (O) ganhou!!!\n\n");
                    if (contO < contX) printf("O jogador (X) ganhou!!!\n\n");
                    if (contO == contX) printf("O jogo ficou empatado!!!\n\n");

                    Turno = -1;
                }
            }
        } else {
            if (Turno % 2 == 0) printf("Proximo Comando?\nÉ a vez do jogador X!\n");
            if (Turno % 2 != 0 && Turno > 0) printf("Proximo Comando?\nÉ a vez do jogador O!\n");
            fgets(linha, 50, stdin);
            switch (toupper(linha[0])) {
                case 'N' : {
                    e.modo = 0;
                    Turno = 0;
                    sscanf(linha, "%c %c", &com, &In);
                    if (toupper(In) != ('X') && toupper(In) != 'O') {
                        printf("Peça inválida \n");
                        break;
                    }
                    if (In == 'X') {
                        e.peca = VALOR_X;
                    } else {
                        e.peca = VALOR_O;
                    }
                    push(&saves, e, Turno);
                    contu++;
                    NovoJogo(&Turno, In);
                    e.modo = 0;
                    tabuleiroI(&e, M, Turno);
                    uppeca(&e, Turno);
                    break;
                }

                case 'T' : {
                    printf("%d \n", Turno);
                    break;
                }

                case 'J': {
                    sscanf(linha, "%c %d %d", &com, &l, &c);
                    uppeca(&e, Turno);
                    if (e.grelha[(l - 1)][c - 1] != VAZIA || (l < 1 || l > 8) || (c < 1 || c > 8) ||
                        jogadaValida(&e, (l - 1), (c - 1)) == 0) {
                        printf("Jogada não é valida\n");
                        printa(e, M, Turno);
                    } else {
                        push(&saves, e, Turno);
                        contu++;
                        Joga(&e, (l - 1), (c - 1));
                        Turno++;
                        printa(e, M, Turno);
                    }
                    if (Continuar(e, (Turno)) == 0) {
                        Turno++;
                        printf("O turno foi passado!\n");
                        if (Continuar(e, (Turno)) == 0) {
                            printf("Jogo acabou.\n");
                            score(e, &contO, &contX);
                            if (contO > contX) printf("O jogador (O) ganhou!!!\n\n");
                            if (contO < contX) printf("O jogador (X) ganhou!!!\n\n");
                            if (contO == contX) printf("O jogo ficou empatado!!!\n\n");

                            Turno = -1;
                        }
                    }
                    break;
                }

                case 'S': {
                    Possibilidade(e, M, Turno);
                    break;
                }

                case 'H': {
                    uppeca(&e, Turno);
                    if (e.peca == VALOR_X) peca = 'X';
                    else peca = 'O';
                    PrimeiraJogada(&e, peca);
                    break;
                }

                case 'U': {
                    if (contu > 1) {
                        contu--;
                        pop(&saves, &e, &Turno);
                    } else {
                        printf("Não pode voltar mais para trás.\n");
                    }
                    uppeca(&e, Turno);
                    printa(e, M, Turno);
                    break;
                }

                case 'A': {
                    sscanf(linha, "%c %c %c", &com, &pbot, &lvl);
                    Turno = 0;
                    M = 2;
                    tabuleiroI(&e, M, Turno);
                    e.peca = VALOR_X;
                    switch (lvl) {
                        case ('1') : {
                            e.modo = '1';
                        }
                        case ('2') : {
                            e.modo = '2';
                        }
                        case ('3') : {
                            e.modo = '3';
                        }
                    }
                    if (pbot == 'O' && (Turno % 2)) {
                        jogadaBot(&e, lvl, pbot);
                        Turno++;
                        printa(e, M, Turno);
                    } else if (pbot == 'X' && (Turno % 2) == 0) {
                        jogadaBot(&e, lvl, pbot);
                        Turno++;
                        printa(e, M, Turno);
                    }
                    break;
                }

                case 'E': {
                    char nome[50];
                    sscanf(linha, "%c %s", &com, nome);
                    uppeca(&e, Turno);
                    escreve(e, nome);
                    break;
                }

                case 'L': {
                    char nome[69];
                    sscanf(linha, "%c %s", &com, nome);
                    ler(&e, nome,&pbot,&Turno);
                    push(&saves, e, Turno);
                    contu++
                    printa(e, M, Turno);
                    break;
                }

                case 'Q': {
                    break;
                }
                default: {
                    printf("comando invalido\n");
                    break;
                }
            }
        }
    } while (toupper(linha[0] != 'Q') && Turno < 69);
    return 0;
}

