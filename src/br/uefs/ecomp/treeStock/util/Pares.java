/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.Exception.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author AlmirNeto
 */
public class Pares implements IPares, Serializable{
    
    private LinkedList<Par> pares;
    
    public Pares(){
        pares = new LinkedList();
    }

    @Override
    public void put(Object chave, Object valor) throws DadoDuplicadoException{
        if(estaVazia()){
            pares.add(new Par(chave, valor));
            return;
        }
        else{
            try{
                Object verifica = get(chave);
                throw new DadoDuplicadoException();
            }
            catch(DadoNaoEncontradoException a){
                pares.add(new Par(chave, valor));
            }
            
        }
    }

    @Override
    public Object get(Object chave) throws DadoNaoEncontradoException {
        if(estaVazia()){
            throw new DadoNaoEncontradoException();
        }
        else{
            Iterator busca = pares.iterator();
            while(busca.hasNext()){
                Par testa = (Par)busca.next();
                if(testa.getChave().equals(chave)){
                    return testa.getValor();
                }
            }
        }
        throw new DadoNaoEncontradoException();
    }

    @Override
    public void remove(Object chave) throws DadoNaoEncontradoException {
        if(estaVazia()){
            throw new DadoNaoEncontradoException();
        }
        else{
            Iterator remove = pares.iterator();
            while(remove.hasNext()){
                Par removido = (Par)remove.next();
                if(removido.getChave().equals(chave)){
                    pares.removeFirstOccurrence(removido);
                    return;
                }
            }
            throw new DadoNaoEncontradoException();       
        }
    }

    @Override
    public boolean estaVazia() {
        return pares.isEmpty();
    }

    @Override
    public Iterator iterator() {
        return pares.iterator();
    }
    
    public int size(){
        return pares.size();
    }
    
    
    private class Par implements IPar, Serializable{
        
        private Object chave;
        private Object valor;
        
        public Par(Object chave, Object valor){
            this.chave = chave;
            this.valor = valor;
        }
        
        public Par(Object chave){
            this.chave = chave;
        }

        @Override
        public Object getChave() {
            return chave;
        }

        @Override
        public Object getValor() {
            return valor;
        }
    }
    
    
}
