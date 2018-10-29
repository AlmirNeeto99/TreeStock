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
public class Acao implements Serializable{
    
    private double valor;
    private String sigla;
    
    public Acao(String sigla, double valor){
        this.sigla = sigla;
        this.valor = valor;
    }
    
    public Acao(String sigla){
        this.sigla = sigla;
    }

    public double getValor() {
        return valor;
    }
    

    public String getSigla() {
        return sigla;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    @Override
    public boolean equals(Object obj){
        Acao compara = (Acao)obj;
        if(sigla.equalsIgnoreCase(compara.getSigla())){
            return true;
        }
        return false;
    }
    
}
