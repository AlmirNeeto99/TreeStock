/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 *
 * @author AlmirNeto
 */
public class AcaoPN extends Acao implements Serializable{
    
    private double dividendos;
    
    public AcaoPN(String sigla, double valor){
        super(sigla, valor);
    }
    
    public AcaoPN(String sigla, double valor, double dividendos){
        super(sigla, valor);
        this.dividendos = dividendos;
    }

    public double getDividendos() {
        return dividendos;
    }

    public void setDividendos(double dividendos) {
        this.dividendos = dividendos;
    }
    
    @Override
    public boolean equals(Object obj){
        Acao compara = (Acao)obj;
        if(getSigla().equalsIgnoreCase(compara.getSigla())){
            return true;
        }
        return false;
    }
    @Override
    public String getSigla(){
        return super.getSigla();
    }
    
}
