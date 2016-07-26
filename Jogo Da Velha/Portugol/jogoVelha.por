programa
{
	inclua biblioteca Util --> u
	
	cadeia matriz[3][3]
	funcao inicio()
	{
		povoarMatriz(matriz)
		gameLoop()
	}
	funcao gameLoop()
	{
		inteiro x, y
		cadeia jogador = "X"
		faca 
		{
			mostrarMatriz(matriz)
			escreva("\n(***Informe -1 na linha para sair***)")
			escreva("\nJogador: "+jogador)
			escreva("\nInforme a linha: ")
			leia(x)
			escreva("\nInforme a coluna: ")
			leia(y)
			se(x != -1)
			{
				incluir(x, y, jogador)
				se(verificarVitoria())
				{
					x = -1
					escreva("\n\n**********VENCEDOR**********\n")
					mostrarMatriz(matriz)
					escreva("**********VENCEDOR**********\n")
					escreva("O jogador "+jogador+" venceu o game!")
				}
				senao
				{
					limpatela()
					se(jogador == "X")
						jogador = "O"
					senao
						jogador = "X"
				}
					
			}
		}enquanto(x != -1)
	}
	funcao limpatela()
	{
		escreva("\n\n\n\n\n\n\n\n\n\n\n\n")
	}
	funcao incluir(inteiro x, inteiro y, cadeia oque)
	{
		se(matriz[x][y] == " ")
			matriz[x][y] = oque
	}
	funcao povoarMatriz(cadeia &m[][])
	{
		inteiro numColuna = u.numero_colunas(m),
			numLinha = u.numero_linhas(m)
		para(inteiro i = 0; i < numLinha; i++)
			para(inteiro j = 0; j < numColuna; j++)
				m[i][j] = " "
	}
	funcao mostrarMatriz(cadeia &m[][])
	{
		inteiro numColuna = u.numero_colunas(m),
			numLinha = u.numero_linhas(m)
		para(inteiro l = 0; l < 3; l++)
			escreva("   "+l+"-  ")
		escreva("\n")
		para(inteiro i = 0; i < numLinha; i++)
		{
			para(inteiro j = 0; j < numColuna; j++)
			{
				se(j != 0)
					escreva("| "+m[i][j]+" |")
				senao
					escreva(i+"- | "+m[i][j]+" |")
			}
			escreva("\n")
		}
	}
	funcao logico verificarVitoria()
	{
		se(verificarReta('H') ou verificarReta('V') ou verificarDiagonal())
			retorne verdadeiro
		senao 
			retorne falso
	}
	funcao logico verificarReta(caracter direcao)
	{
		logico vitoria = falso
		para(inteiro i = 0; i < 3; i++)
		{
			cadeia texto = ""
			para(inteiro j = 0; j < 3; j++)
				se(direcao == 'H')
					texto +=matriz[i][j]
				senao
					texto +=matriz[j][i]
			se(texto == "OOO" ou texto == "XXX")
				vitoria = verdadeiro
		}
		retorne vitoria
	}
	funcao logico verificarDiagonal()
	{
		logico vitoria = falso
		para(inteiro i = 0; i < 2; i++)
		{
			cadeia texto = ""
			para(inteiro j = 0; j < 3; j++)
				se(i == 0)
					texto +=matriz[j][j]
				senao
					texto +=matriz[2 - j][2 - j]
			se(texto == "OOO" ou texto == "XXX")
				vitoria = verdadeiro 
		}
		retorne vitoria
	}
}
/* $$$ Portugol Studio $$$ 
 * 
 * Esta seção do arquivo guarda informações do Portugol Studio.
 * Você pode apagá-la se estiver utilizando outro editor.
 * 
 * @POSICAO-CURSOR = 1368; 
 * @PONTOS-DE-PARADA = ;
 * @SIMBOLOS-INSPECIONADOS = ;
 * @FILTRO-ARVORE-TIPOS-DE-DADO = inteiro, real, logico, cadeia, caracter, vazio;
 * @FILTRO-ARVORE-TIPOS-DE-SIMBOLO = variavel, vetor, matriz, funcao;
 */