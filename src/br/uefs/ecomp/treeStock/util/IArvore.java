package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.Exception.DadoNaoEncontradoException;
import br.uefs.ecomp.treeStock.Exception.DadoDuplicadoException;

public interface IArvore extends Iterable{
    
    public Object buscar(Comparable item) throws DadoNaoEncontradoException;

    public void inserir(Comparable item) throws DadoDuplicadoException;

    public void remover(Comparable item) throws DadoNaoEncontradoException;

}