
package br.uefs.ecomp.treeStock.model;

import java.io.Serializable;

/**
 * Classe responsável por armazenar os Dados de um determinado Cliente, tais como:
 * Nome, CPF, sua carteira e sua conta no Sistema.
 * 
 * Para possibilitar a unicidade das contas dos clientes, cada Cliente deverá cadastrar
 * uma senha para que apenas o dono da sua conta possa alterá-la e movimentá-la como
 * quiser.
 * 
 * 
 * Para tal, será necessário que o Construtor dessa Classe, receba tais parâmetros:
 * 
 * Cliente novo_cliente = new Cliente(nome, cpf, senha, conta, carteira);
 * 
 * Sendo os dois últimos atributos criados pela controller, pois haverá certas verificações
 * de existência e criação de números distintos.
 *
 * @author AlmirNeto
 */
public class Cliente implements Comparable, Serializable{
    
    private String nome = null; //Armazena o nome do Cliente.
    private String cpf; //Armazena o CPF do Cliente.
    private String endereco; //Armazena o endereço do Cliente.
    private Carteira carteira; //Armazena a carteira do Cliente.
    
    /**
     * Contrutor da Classe Cliente
     * 
     * @param nome
     * @param cpf
     * @param endereco
     * 
     */
    public Cliente(String nome, String cpf, String endereco){
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.carteira = new Carteira();
    }

    public Cliente(String cpf){
        this.cpf = cpf;
    }
    /**
        Método que retorna a Carteira do Cliente.
     * 
     * @return a Carteira do Cliente.*/
    public Carteira getCarteira() {
        return carteira;
    }
    /**
     * Método que retorna o Endereço do Cliente.
     * 
     * @return o Endereço do Cliente.
     */
    public String getEndereco() {
        return endereco;
    }
    

    /**
     * Método que retorna o nome do cliente.
     * 
     * @return O nome do cliente
    */
    public String getNome() {
        return nome;
    }
    /**
     Método que retorna o CPF do Cliente.
     * @return CPF do cliente.
    */
    public String getCpf() {
        return cpf;
    }

    /**
     * Método que compara dois Clientes pelo CPF.
     * 
     * @param obj Objeto o qual será comparado com o Objeto atual.
     * 
     * @return Retorna -1 caso o CPF do Cliente Atual seja menor que o recebido como parâmetro,
     * retorna 1 caso o CPF do Cliente Atual seja maior que o, e retorna 0 caso os CPF's sejam iguais.
     */
    @Override
    public int compareTo(Object obj){
        if(obj instanceof Cliente){
            Cliente novo = (Cliente )obj;
            if(cpf.compareTo(novo.getCpf()) < 0){
                return -1;
            }
            else if(cpf.compareTo(novo.getCpf()) > 0){
                return 1;
            }
            else{
                return 0;
            }     
        }
        // Lança tal exceção caso o Objeto em questão não seja um Cliente.
        throw new RuntimeException();
    }
}
