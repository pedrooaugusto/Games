/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Embaralhar;

import Card.CardEstatico;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Embaralhar
{
    final List<Integer>numeroImagens = new ArrayList<>();
    final List<CardEstatico>cardEs = new ArrayList<>();
    public Embaralhar(int necessario)
    {
        criarCardEs(necessario);
    }
    
    public List getCardes()
    {
        return cardEs;
    }
    private List numeroDeImagensNecessario(int necessario)
    {
        List<String> total = new ManipularPastas().lerTodasImagensNaPasta();
        List<String> pretendido = new ArrayList<>();
        Collections.shuffle(total);
        for(int i = 0; i < necessario; i ++)
        {
            pretendido.add(total.get(i));
        }
        return pretendido;
    }
    private void criarCardEs(int necessario)
    {
        List<String> n = numeroDeImagensNecessario(necessario);
        CardEstatico c;
        for(int i = 0; i < n.size(); i++)
        {
            c = new CardEstatico("Imagens da Lily/"+n.get(i), "Imagens da Lily/padrao.jpg", i);
            cardEs.add(c);
        }
        int tamanhoAtual = cardEs.size();
        for(int i = 0; i < tamanhoAtual; i++)
        {
            cardEs.add(cardEs.get(i));
        }
        Collections.shuffle(cardEs);
    }
}
