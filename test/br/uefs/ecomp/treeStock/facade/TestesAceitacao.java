/**
 * As documentações de lançamento de exceção que estão em branco, significam que nunca serão lançadas, porém o NB exige sua declaração.
 * 
 * @author Almir Neto
 * 
 */
package br.uefs.ecomp.treeStock.facade;

import br.uefs.ecomp.treeStock.model.*;
import br.uefs.ecomp.treeStock.Exception.*;
import br.uefs.ecomp.treeStock.util.*;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import org.junit.Before;

public class TestesAceitacao {

    private static final double EPSILON = 0.001;
    private TreeStockFacade facade;
    private Cliente maria, joao, pedro, marcos, suzana, tarcizio, priscila;
    private Acao petr, elet, bbas, embr;

    @Before
    public void setUp() throws Exception {
        facade = new TreeStockFacade();

        tarcizio = facade.cadastrarCliente("Tarcizio Nery", "Rua Pedro Suzart, 3456", "1");
        maria = facade.cadastrarCliente("Maria dos Santos", "Rua Drummond, 23, Centro", "2");
        joao = facade.cadastrarCliente("Jo�o dos Santos", "Rua Pessoa, 12, Centro", "3");
        pedro = facade.cadastrarCliente("Pedro da Silva", "Rua Andrade, 45, Cidade Nova", "4");
        marcos = facade.cadastrarCliente("Marcos Oliveira", "Rua Quintana, 45, Santa Monica", "5");
        suzana = facade.cadastrarCliente("Suzana Abreu Lima", "Rua da ladeira, 23", "6");
        priscila = facade.cadastrarCliente("Priscila Costa e Silva", "Rua E, 27", "7");

        petr = facade.cadastrarAcao("PETR4", 21.10);
        elet = facade.cadastrarAcao("ELET3", 9.80);
        bbas = facade.cadastrarAcao("BBAS3", 29.50);
        embr = facade.cadastrarAcao("EMBR3", 22.60);
    }
    /**
     * Método que testa um cadastro de um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test
    public void testCadastrarCliente() throws DadoDuplicadoException {
        assertFalse(facade.clienteCadastrado("10"));

        Cliente cliente = facade.cadastrarCliente("Fulano de tal", "Rua da hora, 23, Centro", "10");
        assertEquals("Fulano de tal", cliente.getNome());
        assertEquals("Rua da hora, 23, Centro", cliente.getEndereco());

        assertTrue(facade.clienteCadastrado("10"));
    }
    /**
     * Método que teste o Cadastro de Clientes com o mesmo CPF, gerando um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarClienteRepetido() throws DadoDuplicadoException {
        //deve lan�ar exce��o
        facade.cadastrarCliente("Marcos Oliveira", "Independente do endere�o", "5");
    }
    /**
     * Método que testa a remoção de um Cliente do sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * 
     * @throws ClienteNaoEncontradoException Quando o cliente que está sendo removido, não existir.
     */
    @Test
    public void testRemoverCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException {
        assertTrue(facade.clienteCadastrado("1"));

        Cliente cliente = facade.removerCliente("1");
        assertEquals(cliente, tarcizio);

        assertFalse(facade.clienteCadastrado("1"));
    }
    /**
     * Método que testa a remoção de um Cliente inexistente do sistema.
     * 
     * 
     * @throws ClienteNaoEncontradoException Quando o cliente que está sendo removido, não existir.
     */
    @Test(expected = ClienteNaoEncontradoException.class)
    public void testRemoverClienteInexistente() throws ClienteNaoEncontradoException {
        //deve lan�ar exce��o
        facade.removerCliente("1000");
    }
    /**
     * Método que testa o Cadastro de um nova ação no Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test
    public void testCadastrarAcao() throws DadoDuplicadoException {
        assertFalse(facade.acaoCadastrada("FAKE4"));

        Acao acao = facade.cadastrarAcao("FAKE4", 1.00);

        assertEquals("FAKE4", acao.getSigla());
        assertEquals(1.00, acao.getValor(), EPSILON);

        assertTrue(facade.acaoCadastrada("FAKE4"));
    }
    /**
     * Método que cadastra duas ações com a mesma sigla, resultando em um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoRepetida() throws DadoDuplicadoException {
        //deve lan�ar exce��o
        facade.cadastrarAcao("PETR4", 123);
    }
    /**
     * Método que tenta remover uma ação do Sistema com sucesso.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test
    public void testRemoverAcao() throws DadoDuplicadoException, AcaoInexistenteException {
        assertTrue(facade.acaoCadastrada("EMBR3"));

        Acao acao = facade.removerAcao("EMBR3");
        assertEquals(new Acao("EMBR3"), acao);

        assertFalse(facade.acaoCadastrada("EMBR3"));
    }
    /**
     * Método que tenta remover uma ação inexistente do Sistema com sucesso.
     * 
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testRemoverAcaoInexistente() throws AcaoInexistenteException {
        //deve lan�ar exce��o
        facade.removerAcao("UEFS3");
    }
    /**
     * Método que tenta pegar o valor de uma ação do Sistema.
     * 
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    public void testValorAcao() throws AcaoInexistenteException {
        assertEquals(21.10, facade.getValorAcao("PETR4"), EPSILON);

        facade.setValorAcao("PETR4", 40.80);

        assertEquals(40.80, facade.getValorAcao("PETR4"), EPSILON);
    }
    /**
     * Método que testa a integridade do Iterador de Pessoas.
     * 
     * @throws DadoDuplicadoException
     */
    @Test
    public void testListarClientes() throws DadoDuplicadoException {
        Iterator clientes = null;
        try{
        
            clientes = facade.listarPessoas();
        }
        catch(ClienteNaoEncontradoException e){
        
        }
        assertTrue(clientes.hasNext());
        assertEquals(tarcizio, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(maria, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(joao, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(pedro, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(marcos, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(suzana, clientes.next());

        assertTrue(clientes.hasNext());
        assertEquals(priscila, clientes.next());

        assertFalse(clientes.hasNext());
    }
    /**
     * Método que inclui uma ação em um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws DadoNaoEncontradoException Quando não houver o Cliente com o CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     */
    @Test
    public void testIncluirAcaoCliente() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException, CarteiraVaziaException, DadoNaoEncontradoException, AcaoNaoEncontradaException {
        
        facade.incluirAcaoCliente("3", "PETR4", 1000);
        assertEquals(1000, facade.getQuantidadeAcaoCliente("3", "PETR4"));
        
    }
    /**
     * Método que inclui uma ação em um Cliente inexistente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws DadoNaoEncontradoException Quando não houver o Cliente com o CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     */
    @Test(expected = ClienteNaoEncontradoException.class)
    public void testIncluirAcaoClienteNaoCadastradaNaBolsa() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException, DadoNaoEncontradoException {
        facade.incluirAcaoCliente("Cliente n�o Cadastrado", "PERT2", 1000);
    }
    /**
     * Método que inclui uma ação inexistente em um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testSetQuantidadeAcaoNaoCadastradaNaBolsa() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException, CarteiraVaziaException {
        
        facade.setQuantidadeAcaoCliente("3", "MICO2", 1000);        
    }
    /**
     * Método que remove uma determinada ação de um cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     */
    @Test
    public void testExcluirAcaoCliente() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException, CarteiraVaziaException, AcaoNaoEncontradaException, AcaoNaoEncontradaException {
        facade.incluirAcaoCliente("3", "PETR4", 1);
        facade.setQuantidadeAcaoCliente("3", "PETR4", 500);

        assertEquals(501, facade.getQuantidadeAcaoCliente("3", "PETR4"));

        facade.removerAcaoCliente("3", "PETR4");

        try {
            facade.getQuantidadeAcaoCliente("3", "PETR4");
            assertTrue(false);
        } catch (CarteiraVaziaException e) {
            assertTrue(true);
        }
    }
    /**
     * Método que pega o valor da Carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws DadoDuplicadoException
     * @throws DadoNaoEncontradoException
     * @throws AcoesInsuficientesException Caso o cliente não possua ações.
     * @throws CarteiraVaziaException Quando a carteira do cliente está vazia.
     * @throws AcaoNaoEncontradaException Quando o cliente não possue tal ação.
     */
    @Test
    public void testGetCarteiraCliente() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoNaoEncontradoException, AcoesInsuficientesException, CarteiraVaziaException, DadoDuplicadoException, AcaoNaoEncontradaException{
        IPares carteira = null;
        try{
            carteira = facade.getCarteiraCliente("6");
        }
        catch(CarteiraVaziaException a){
            assertTrue(true);
        }

        double valor = facade.getValorCarteiraCliente("6");
        assertEquals(0.0, valor, EPSILON);

        facade.incluirAcaoCliente("6", "PETR4", 500);
        try{
            carteira = facade.getCarteiraCliente("6");
        }
        catch(CarteiraVaziaException a){
            assertFalse(false);
        }
        assertEquals(500, carteira.get(petr));
        assertEquals(500, facade.getQuantidadeAcaoCliente("6", "PETR4"));

        valor = facade.getValorCarteiraCliente("6");
        assertEquals(petr.getValor() * 500, valor, EPSILON);

        facade.incluirAcaoCliente("6", "EMBR3", 1000);
        facade.incluirAcaoCliente("6", "BBAS3", 100);
        try{
            carteira = facade.getCarteiraCliente("6");
        }
        catch(CarteiraVaziaException a){
            assertTrue(true);
        }
        assertEquals(500, carteira.get(petr));
        assertEquals(1000, carteira.get(embr));
        assertEquals(100, carteira.get(bbas));

        valor = facade.getValorCarteiraCliente("6");
        assertEquals(petr.getValor() * 500 + embr.getValor() * 1000 + bbas.getValor() * 100, valor, EPSILON);

        facade.removerAcaoCliente("6", "PETR4");
        facade.removerAcaoCliente("6", "EMBR3");
        facade.removerAcaoCliente("6", "BBAS3");

        try{
            carteira = facade.getCarteiraCliente("6");
        }
        catch(CarteiraVaziaException a){
            assertTrue(true);
        }
    }
    /**
     * Método que verifica a confiabilidade do Iterador de Melhores Clientes.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws DadoDuplicadoException
     * @throws DadoNaoEncontradoException
     */
    @Test
    public void testMelhoresClientes() throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoNaoEncontradoException, DadoDuplicadoException {
        Iterator melhores = facade.melhoresClientes(0);
        assertFalse(melhores.hasNext());

        facade.incluirAcaoCliente(maria.getCpf(), petr.getSigla(), 501);
        facade.incluirAcaoCliente(maria.getCpf(), elet.getSigla(), 1150);
        facade.incluirAcaoCliente(joao.getCpf(), petr.getSigla(), 1500);
        facade.incluirAcaoCliente(joao.getCpf(), elet.getSigla(), 100); 

        facade.incluirAcaoCliente(pedro.getCpf(), petr.getSigla(), 2500);
        facade.incluirAcaoCliente(pedro.getCpf(), elet.getSigla(), 50);

        facade.incluirAcaoCliente(marcos.getCpf(), petr.getSigla(), 400);
        facade.incluirAcaoCliente(suzana.getCpf(), petr.getSigla(), 200);
        facade.incluirAcaoCliente(tarcizio.getCpf(), petr.getSigla(), 300);
        facade.incluirAcaoCliente(priscila.getCpf(), petr.getSigla(), 500);

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        Conta conta = (Conta)melhores.next();
        assertEquals(pedro.getCpf(), conta.getCliente().getCpf());
        assertFalse(melhores.hasNext());
        
        facade.setValorAcao(elet.getSigla(), 40.00);
        

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        conta = (Conta)melhores.next();
        assertEquals(maria.getCpf(), conta.getCliente().getCpf());
        
        
        assertFalse(melhores.hasNext());

        facade.incluirAcaoCliente(suzana.getCpf(), bbas.getSigla(), 2000);

        melhores = facade.melhoresClientes(1);
        assertTrue(melhores.hasNext());
        conta = (Conta)melhores.next();
        assertEquals(suzana.getCpf(), conta.getCliente().getCpf());
        assertFalse(melhores.hasNext());
    }
}
