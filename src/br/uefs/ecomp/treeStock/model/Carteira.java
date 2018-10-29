/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.treeStock.model;

import br.uefs.ecomp.treeStock.util.IPar;
import br.uefs.ecomp.treeStock.util.Pares;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author AlmirNeto
 */
public class Carteira implements Serializable{
    
    private double total_carteira;
    private Pares acoes;
    
    public Carteira(){
        acoes = new Pares();
    }

    public double getTotal_carteira() {
        this.total_carteira = 0.0;
        Iterator percorre = acoes.iterator();
        while(percorre.hasNext()){
            IPar par = (IPar)percorre.next();
            Acao acao = (Acao)par.getChave();
            total_carteira += (acao.getValor() * (int)par.getValor());
        }
        return this.total_carteira;
    }  
    public double getTotal_dividendos(){
        double dividendos = 0.0;
        Iterator percorre = acoes.iterator();
        while(percorre.hasNext()){
            IPar par = (IPar)percorre.next();
            Acao acao = (Acao)par.getChave();
            if(acao instanceof AcaoPN){
                AcaoPN acaopn = (AcaoPN)acao;
                dividendos += acaopn.getDividendos() * (int)par.getValor();
            }
        }
        return dividendos; 
    }

    public Pares getAcoes() {
        return acoes;
    }
    
    
}
