package br.uefs.ecomp.treeStock.util;

import br.uefs.ecomp.treeStock.Exception.*;

/**
 * Armazena um conjunto de pares (chave, valor). Para cada chave, existe apenas
 * um valor correspondente.
 * 
 * @author Robos
 */
public interface IPares extends Iterable{

    /**
     * Armazena um mapeamento (chave, valor). P
     * @param chave a chave do mapeamento
     * @param valor o valor associado a chave
     * @throws DadoDuplicadoException caso já exista uma chave na estrutura.
     */
    public void put(Object chave, Object valor) throws DadoDuplicadoException;
    
    /**
     * Retorna o valor correspondente a chave dda
     * @param chave a chave
     * @return o valor associado a chave
     * @throws DadoNaoEncontradoException caso a chave n�o esteja presente na 
     * estrutura de dados.
     */
    public Object get(Object chave) throws DadoNaoEncontradoException;
    
    
    /**
     * Remove um mapeamento da estrutura.
     * @param chave a chave
     * @throws DadoNaoEncontradoException caso a chave n�o esteja presente na 
     * estrutura de dados.
     */
    public void remove(Object chave) throws DadoNaoEncontradoException;
    
    
    /**
     * Retorna true caso tenha um ou mais pares armazenados, false em caso contr�rio.
     * @return true, caso n�o tenha nenhum par armazenado na estrutura. 
     * False, caso contr�rio.
     */
    public boolean estaVazia();    
    
    
}
