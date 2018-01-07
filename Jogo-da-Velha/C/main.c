/*
 * Eu ate comecei a fazer o bot invencivel
 * mas C é muito pobre e ando meio sem tempo
 * então deixei assim semi-inteligente talvez em 2017
 * eu faço ele invencivel.
 * 
 */

/* 
 * File:   main.c
 * Author: Pedro
 *
 * Created on 22 de Junho de 2016, 09:27
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
/*
 * 
 */
//Variavei globais
char board[3][3] = {{' ', ' ', ' '}, 
                    {' ', ' ', ' '}, 
                    {' ', ' ', ' '}};
char* vitoria[8][3] = {{&board[0][0], &board[0][1], &board[0][2]},
                      {&board[1][0], &board[1][1], &board[1][2]},
                      {&board[2][0], &board[2][1], &board[2][2]},
                      {&board[0][0], &board[1][0], &board[2][0]},
                      {&board[0][1], &board[1][1], &board[2][1]},
                      {&board[0][2], &board[1][2], &board[2][2]},
                      {&board[0][0], &board[1][1], &board[2][2]},
                      {&board[0][2], &board[1][1], &board[2][0]}};
int platWithBot = 1;
int rowNumber = 3;
int colNumber = 3;
int currentPlayer = 1;
int game_state = 2;
int rodada = 0;
int pontosPlayer1 = 0;
int pontosPlayer2 = 0;
char symbol[2] = {'O', 'X'};
int historicoMovimento_player1[7][2] = {{}, {}, {}, {}, {}, {}, {}};
int tamHist1 = 0;
int tamHist2 = 0;
int historicoMovimento_player2[7][2] = {{}, {}, {}, {}, {}, {}, {}};
char* excluidos_vazio[3] = {0, 0, 0};

//Funções
void header();
void clearConsole();
void displayBoard();
void clearBoard();
void gameLoop();
void jogarLugarDisponivelAleatorio(char* excluidos[]);
int contains(char* elementos[], char* vc);
int containsMatriz(char* vc, int which);
void winMessge(int i);
int insert(int row, int col, char caractere);
int winner();
void vezDoBot();
int possivelVitoria();
void quemTaVazio(int argument);
int isFull();

int main(int argc, char** argv) {
    int i;
	for(i = 0; i < 22; i++)
    {
        printf("**");
	}
    printf("\n*                                          *");
    printf("\n*     ACESSE: pedrooaugusto.github.io      *");
    printf("\n*                                          *\n");
    int li;
    for(li = 0; li < 22; li++)
        printf("**");
    int opc;
    printf("\n\nModo de Jogo:\n1-Single Player\n2-Multiplayer\n");
    scanf("%i", &opc);
    if(opc == 1)
        platWithBot = 1;
    else
        platWithBot = 0;
    clearConsole();
    gameLoop();
    return 0;
}

void gameLoop()
{
    int row, col;
    while(game_state != 4)
    {
        header();
        printf("\n\n\n");
        displayBoard();
        if(currentPlayer == 1)
        {            
            printf("\n     Player %c:\n", symbol[currentPlayer]);
            printf("\nRow Nº: ");
            scanf("%d", &row);
            printf("Col Nº: ");
            scanf("%d", &col);
            if(insert(row, col, 'X'))
            {
                currentPlayer = 0;
                historicoMovimento_player2[tamHist2][0] = row;
                historicoMovimento_player2[tamHist2][1] = col;
                tamHist2+=1;   
            }
        }
        else
        {
            if(platWithBot == 1)
            {
                vezDoBot();
                currentPlayer = 1;
            }
            else 
            {
                printf("\n     Player %c:\n", symbol[currentPlayer]);
                printf("\nRow Nº: ");
                scanf("%d", &row);
                printf("Col Nº: ");
                scanf("%d", &col);
                if(insert(row, col, 'O') == 1)
                {
                    currentPlayer = 1;
                }
            }
        }
        clearConsole();
        game_state = winner();   
        switch(game_state)
        {
            case 0://JOGADOR 0 VENCEU
                pontosPlayer1++;
                winMessge(0);
            break;
            case 1://JOGADOR 1 VENCEU
                pontosPlayer2++;
                winMessge(1);
            break;
            case 3://EMPATE
                winMessge(3);
            break;
        }
    }
}

void vezDoBot()
{
    if(possivelVitoria() == 0)
    {
        jogarLugarDisponivelAleatorio(excluidos_vazio);
    }
}

int isFull()
{
	int i;
	int j;
    for(i = 0; i < 3; i++)
        for(j = 0; j < 3; j++)
            if(board[i][j] == ' ')
                return 0;
    return 1;
}
void jogarLugarDisponivelAleatorio(char* excluidos[])
{
    char* possiveis[8];
    int posi[8][2];
    int tam = 0;
	int i;
	int j;
    for(i = 0; i < 3; i++)
    {
        for(j = 0; j < 3; j++)
        {
            if(board[i][j] == ' ' && contains(excluidos, &board[i][j]) == -1)
            {
                possiveis[tam] = &board[i][j];
                posi[tam][0] = i;
                posi[tam][1] = j;
                tam++;
            }
        }
    }
    srand(time(0));
    int r = rand();
    int who = r % tam;
    *possiveis[who] = 'O';
    historicoMovimento_player1[tamHist1][0] = posi[who][0];
    historicoMovimento_player1[tamHist1][1] = posi[who][1];
    tamHist1+=1;
}

int contains(char* elementos[], char* vc)
{
	int i;
    for(i = 0; i < 3; i++)
        if(elementos[i] == vc)
            return i;
    return -1;
}

int stringEquals(char string[], char oq)
{
	int i;
    for(i = 0; i < 3; i++)//{' ', 'O', ' '} -> 'O'--> 1
        if(string[i] != ' ' && string[i] != oq)
            return 0;
    return 1;
}

int containsMatriz(char* vc, int which)
{
    int a[2];
	int i;
	int j;
    for(i = 0; i < 3; i++)
    {
        for(j = 0; j < 3; j++)
        {
            if(&board[i][j] == vc)
            {
                a[0] = i;
                a[1] = j;
                break;
            }
        }
    }
    return which == 1 ? a[1] : a[0];
}

int possivelVitoria()
{
    int k = -1;
	int i;
	int j;
    for(i = 0; i < 8; i++)
    {
        int resul[3] = {0, 0, 0};
        for(j = 0; j < 3; j++)
        {
            if(*vitoria[i][j] == 'X')
                resul[0]++;
            else if(*vitoria[i][j] == 'O')
                resul[1]++;
            else
                resul[2]++;
        }
        if((resul[1] == 2) && (resul[2] == 1))
        {
            quemTaVazio(i);
            return 1;
        }
        else if((resul[0] == 2) && (resul[2] == 1))
        {
            k = i;
        }
    }
    if(k != -1)
    {
        quemTaVazio(k);
        return 1;
    }
    return 0;
}

void quemTaVazio(int argument) 
{
	int i;
    for(i = 0; i < 3; i++) 
        if(*vitoria[argument][i] == ' ')
        {
            *vitoria[argument][i] = 'O';
            historicoMovimento_player1[tamHist1][0] = 
                    containsMatriz(vitoria[argument][i], 0);
            historicoMovimento_player1[tamHist1][1] = 
                    containsMatriz(vitoria[argument][i], 1);
            tamHist1+=1;
        }
}

int winner()
{
    //0  = Player 1 win
    //1  = Player 2 win
    //2  = no winner
    //3  = empate
    int i;
    if(isFull() == 1)
        return 3;
    else
        for(i = 0; i < 8; i++)
            if((*vitoria[i][0] == 'X' && *vitoria[i][1] == 'X' && *vitoria[i][2] == 'X')
                    ||
                (*vitoria[i][0] == 'O' && *vitoria[i][1] == 'O' && *vitoria[i][2] == 'O'))
                return !currentPlayer;
    return 2;
}

int insert(int row, int col, char caractere)
{
    if(board[row][col] == ' ')
    {
        board[row][col] = caractere;
        return 1;
    }
    else
    {
        printf("\n___The space is already in use___\n");
        return 0;
    }
}

void displayBoard()
{
	int i;
	int j;
    printf("    0    1    2\n\n");
    for(i = 0; i < rowNumber; i++)
    {
        for(j = 0; j < colNumber; j++)
        {
            if(board[i][j] == ' ')
            {
                if(j == 0)
                    printf("%i   *  ", i);
                else
                    printf("  *  ");
            }
            else
            {
                if(j == 0)
                    printf("%i   %c  ", i, board[i][j]);
                else
                    printf("  %c  ", board[i][j]);
            }
        }
        printf("\n\n");
    }
}

void clearConsole()
{
    printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
}

void clearBoard()
{
	int i;
	int j;
    for(i = 0; i < 3; i++)
        for(j = 0; j < 3; j++)
            board[i][j] = ' ';
    rodada++;
}

void header()
{
    printf("    Jogo da velha\n    By: Pedro Augusto\n\n");
    printf("    Pontos (O): %i\n    Rodada:     %i\n    Pontos (X): %i", 
            pontosPlayer1, rodada, pontosPlayer2);
}

void winMessge(int i)
{
    clearConsole();
    displayBoard();
    int opc;
    if(i == 3)
        printf("\n\nNinguém venceu!\n");
    else
        printf("\nJogador ' %c ' venceu!!\n", symbol[i]);
    printf("Pressione 0 para sair, e 1 para tentar novamente: ");
    scanf("%i", &opc);
    if(opc == 1)
    {
        clearBoard();
        clearConsole();
        gameLoop();
    }
    else
    {
        game_state = 4;
    }
}

