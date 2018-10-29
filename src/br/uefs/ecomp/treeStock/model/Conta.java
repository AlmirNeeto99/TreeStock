
package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Classe responsável por armazenar as ações de determinado cliente
 * como o seu dinheiro.
 * 
 * @author AlmirNeto
 */
public class Conta implements Serializable, Comparable{

    private Cliente cliente; //Atributo que armazena a referência para o cliente, detentor da conta.
    private double saldo; //Atributo que armazena o saldo do cliente.
    private double montante; //Atributo que armazena o montante do cliente.
    
    public Conta(Cliente cliente){
        this.cliente = cliente;
    }
    /**
     * Método que retorna o detentor da Conta.
     * @return O Dono da conta.
     */
    public Cliente getCliente(){
        return cliente;
    }
    
    /**
     * Método que calcula e retorna o saldo do cliente.
     * 
     * Calcula o saldo, baseando no dividendo das ações do Cliente.
     * 
     * @return O saldo do Cliente.
     */
    public double getSaldo(){
        saldo = 0;
        saldo = cliente.getCarteira().getTotal_dividendos();
        return saldo;
    }
    
    /**
     * Método que calcula e retorna o montante do cliente.
     * 
     * Calcula o montante, baseando na quantidade e no valor das Ações do Cliente.
     * 
     * @return O montante do Cliente.
     */
    public double getMontante(){
        montante = 0;
        montante = cliente.getCarteira().getTotal_carteira() + getSaldo();
        return montante;
    }
    /**
     * 
     * Método que compara 02 contas.
     * 
     * @return 1 caso o objeto atual seja menor que o objeto passado como parâmetro, -1 caso contrário e 0 caso sejam iguais.
     * 
     * @param obj O Objeto a ser comparado.
     */
    @Override
    public int compareTo(Object obj) {
    if(obj instanceof Conta){
            Conta novo = (Conta )obj;
            if(montante > novo.getMontante()){
                return -1;
            }
            else if(montante < novo.getMontante()){
                return 1;
            }
            else{
                return 0;
            }     
        }
        // Lança tal exceção caso o Objeto em questão não seja um Cliente.
        throw new RuntimeException();} 
}
