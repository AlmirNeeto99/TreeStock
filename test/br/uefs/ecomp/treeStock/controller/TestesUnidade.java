/**
 * As documentações de lançamento de exceção que estão em branco, significam que nunca serão lançadas, porém o NB exige sua declaração.
 * 
 * @author Almir Neto
 * 
 */
package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.model.*;
import br.uefs.ecomp.treeStock.Exception.*;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import org.junit.Before;

public class TestesUnidade {
    TreeStockController controller;
    @Before
    public void setUp(){
        controller = new TreeStockController();
    }
    /**
     * Método que testa um cadastro de um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test
    public void testCadastrarCliente() throws DadoDuplicadoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");

        
    }
    /**
     * Método que teste o Cadastro de Clientes com o mesmo CPF, gerando um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test(expected =  DadoDuplicadoException.class)
    public void testCadastrarClienteMesmoCPFComFalha() throws DadoDuplicadoException{

        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");

       
        Cliente testa1 = controller.cadastrarCliente("Lucano", "Endereço", "1234");
    }
    /**
     * Método que testa o Cadastro de um nova ação ordinária no Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test
    public void testCadastrarAcaoOrdinaria() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS1", 20.0);

    }
    /**
     * Método que testa o Cadastro de um nova ação preferencial no Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test
    public void testCadastrarAcaoPreferencial() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0, 1.0);
    }
    /**
     * Método que cadastra duas ações ordinárias com a mesma sigla, resultando em um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoOrdinariaRepetida() throws DadoDuplicadoException{

        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);

        Acao acao2 = controller.cadastrarAcao("UEFS2", 50.0);
    }
    /**
     * Método que cadastra duas ações preferenciais com a mesma sigla, resultando em um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoPreferencialRepetida() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0, 3.0);

        Acao acao2 = controller.cadastrarAcao("UEFS2", 50.0, 3.0);
    }
    /**
     * Método que cadastra duas ações distintas com a mesma sigla, resultando em um erro.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test(expected = DadoDuplicadoException.class)
    public void testCadastrarAcaoONePNcomMesmaSigla() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);

        Acao acao2 = controller.cadastrarAcao("UEFS2", 50.0, 3.0);
    }
    /**
     * Método que testa a remoção de um Cliente do sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * 
     * @throws ClienteNaoEncontradoException Quando o cliente que está sendo removido, não existir.
     */
    @Test
    public void testRemoverCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Cliente removido = controller.removerCliente("1234"); 
    }
    /**
     * Método que testa a remoção de um Cliente inexistente do sistema.
     * 
     * 
     * @throws ClienteNaoEncontradoException Quando o cliente que está sendo removido, não existir.
     */
    @Test(expected = ClienteNaoEncontradoException.class)
    public void testRemoverClienteInexistente() throws  ClienteNaoEncontradoException{
        Cliente testa = controller.removerCliente("123456789");
    }
    /**
     * Método que busca uma Ação no Sistema pela sua sigla.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * 
     * @throws DadoNaoEncontradoException Quando não existir a ação com tal sigla.
     */
    @Test
    public void testBuscarAcaoPorSigla() throws DadoDuplicadoException, DadoNaoEncontradoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        Acao buscada = controller.buscaAcaoPorSigla("UEFS2");
    }
    /**
     * Método que busca uma Ação inexistente no Sistema pela sua sigla.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * 
     * @throws DadoNaoEncontradoException Quando não existir a ação com tal sigla.
     */
    @Test(expected = DadoNaoEncontradoException.class)
    public void testBuscarAcaoInexistente() throws DadoDuplicadoException, DadoNaoEncontradoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        Acao buscada = controller.buscaAcaoPorSigla("UEFS5");
    }
    /**
     * Método que verifica se determinado cliente está cadastrado no sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test
    public void testBuscarClienteCadastrado() throws DadoDuplicadoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        assertEquals(true, controller.clienteCadastrado("1234"));
    }
    /**
     * Método que verifica se determinado cliente inexistente está cadastrado no sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test
    public void testBuscarClienteInexistente() throws DadoDuplicadoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        assertEquals(false, controller.clienteCadastrado("1231456"));
    }
    /**
     * Método que tenta remover uma ação do Sistema com sucesso.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test
    public void testRemoverAcao() throws DadoDuplicadoException, AcaoInexistenteException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        Acao removido = controller.removerAcao("UEFS2");
        
        assertEquals(acao, removido);
    }
    /**
     * Método que tenta remover uma ação inexistente do Sistema com sucesso.
     * 
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testRemoverAcaoInexistente() throws AcaoInexistenteException{
        Acao removido = controller.removerAcao("UEFS2");
    }
    /**
     * Método que verifica se uma ação está cadastrada no Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test
    public void testVerificarAcaoCadastrada() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        assertEquals(true, controller.acaoCadastrada("UEFS2"));
    }
    /**
     * Método que verifica se uma ação inexistente está cadastrada no Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     */
    @Test
    public void testVerificarAcaoNaoCadastrada() throws DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        assertEquals(false, controller.acaoCadastrada("UEFS9"));
    }
    /**
     * Método que tenta alterar o valor de uma ação do Sistema com sucesso.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test
    public void testAlterarValorDaAcao() throws DadoDuplicadoException, AcaoInexistenteException{
        Acao acao = controller.cadastrarAcao("UEFS2", 20.0);
        
        controller.setValorAcao("UEFS2", 50.0);
        
        assertEquals(50.0, acao.getValor(), 0.1);
    }
    /**
     * Método que tenta alterar o valor de uma ação inexistente do Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testAlterarValorDeAcaoInexistente() throws AcaoInexistenteException, DadoDuplicadoException{
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.setValorAcao("UEFS2", 50.0);
        
        assertEquals(50.0, acao.getValor(), 0.1);
    }
    /**
     * Método que tenta pegar o valor de uma ação do Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test
    public void testPegarValorDaAcao() throws DadoDuplicadoException, AcaoInexistenteException{
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        assertEquals(20.0, controller.getValorAcao("UEFS5"), 0.1);
    }
    /**
     * Método que tenta pegar o valor de uma ação inexistente do Sistema.
     * 
     * @throws DadoDuplicadoException Quando já houver uma ação com a mesma sigla no Sistema.
     * 
     * @throws AcaoInexistenteException Quando não existir tal ação no Sistema.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testPegarValorDeAcaoInexistente() throws DadoDuplicadoException, AcaoInexistenteException{
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        assertEquals(20.0, controller.getValorAcao("UEFS3"), 0.1);
    }
    /**
     * Método que inclui uma ação em um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws DadoNaoEncontradoException Quando não houver o Cliente com o CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     */
    @Test
    public void testIncluirAcaoEmCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException, DadoNaoEncontradoException, AcaoInexistenteException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
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
    public void testIncluirAcaoEmClienteInexistente() throws ClienteNaoEncontradoException, DadoDuplicadoException, DadoNaoEncontradoException, AcaoInexistenteException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("CPF QUALQUER", "UEFS5", 150);
    }
    
    /**
     * Método que inclui uma ação inexistente em um Cliente.
     * 
     * @throws DadoDuplicadoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws DadoNaoEncontradoException Quando não houver o Cliente com o CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testIncluirAcaoInexistenteEmCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoInexistenteException, DadoNaoEncontradoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "ACAOQUALQUER", 200);
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
    public void testRemoverAcaoCliente() throws ClienteNaoEncontradoException, DadoDuplicadoException, AcaoInexistenteException, AcaoNaoEncontradaException, CarteiraVaziaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        controller.removerAcaoCliente("1234", "UEFS5");
        
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
    @Test(expected = CarteiraVaziaException.class)
    public void testRemoverAcaoClienteSemAcoes() throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoInexistenteException, AcaoNaoEncontradaException, CarteiraVaziaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        
        controller.removerAcaoCliente("1234", "QUALQUER SIGLA");
    }
    /**
     * Método que busca a quantidade de determinada ação de um cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     * @throws DadoDuplicadoException
     */
    @Test
    public void testPegarQuantidadeDeAcoesDeUmCliente() throws ClienteNaoEncontradoException, CarteiraVaziaException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        assertEquals(100, controller.getQuantidadeAcaoCliente("1234", "UEFS5"));
    }
    /**
     * Método que busca a quantidade de determinada ação de um cliente inexistente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     * @throws DadoDuplicadoException
     */
    @Test(expected = ClienteNaoEncontradoException.class)
    public void testPegarQuantidadeDeAcoesDeUmClienteInexistente()throws ClienteNaoEncontradoException, CarteiraVaziaException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        assertEquals(100, controller.getQuantidadeAcaoCliente("QUALQUER CPF", "UEFS5"));
    }
    /**
     * Método que busca a quantidade de determinada ação inexistente de um cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     * @throws DadoDuplicadoException
     */
    @Test(expected = AcaoInexistenteException.class)
    public void testPegarQuantidadeDeAcoesInexistentes() throws ClienteNaoEncontradoException, CarteiraVaziaException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        assertEquals(100, controller.getQuantidadeAcaoCliente("1234", "QUALQUER SIGLA"));
    }
    /**
     * Método que busca a quantidade de determinada ação que não pertence a um cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     * @throws DadoDuplicadoException
     */
    @Test(expected = AcaoNaoEncontradaException.class)
    public void testPegarQuantidadeDeAcoesQueNaoPossuemAUmCliente() throws ClienteNaoEncontradoException, CarteiraVaziaException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        Acao acao2 = controller.cadastrarAcao("UEFS1", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        assertEquals(100, controller.getQuantidadeAcaoCliente("1234", "UEFS1"));
    }
    /**
     * Método que altera a quantidade de determinada ação de um cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws CarteiraVaziaException Quando a carteira do cliente estiver vazia.
     * @throws AcaoNaoEncontradaException Quando o Cliente não possuir tal ação.
     * @throws DadoDuplicadoException
     */
    @Test
    public void testAlterarQuantidadeDeAcoesDeUmCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoInexistenteException, AcaoNaoEncontradaException, CarteiraVaziaException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        controller.setQuantidadeAcaoCliente("1234", "UEFS5", 50);
        
        assertEquals(150, controller.getQuantidadeAcaoCliente("1234", "UEFS5"));
    }
    /**
     * Método que pega o valor da Carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws AcaoInexistenteException Quando não houver a ação com tal sigla no Sistema.
     * @throws DadoDuplicadoException
     */
    @Test
    public void pegarValorDaCarteiraDoCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoInexistenteException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
        
        controller.incluirAcaoCliente("1234", "UEFS5", 100);
        
        assertEquals(2000.0, controller.getValorCarteiraCliente("1234"), 0.1);

    }
    /**
     * Método que pega o valor da Carteira Vazia do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     * @throws DadoDuplicadoException
     */
    @Test()
    public void pegarValorDeUmaCarteiraVaziaDeUmCliente() throws DadoDuplicadoException, ClienteNaoEncontradoException{
        Cliente testa = controller.cadastrarCliente("Almir", "Endereço", "1234");
        
        Acao acao = controller.cadastrarAcao("UEFS5", 20.0);
 
        assertEquals(0, controller.getValorCarteiraCliente("1234"), 0.1);
    }
    /**
     * Método que pega o valor da Carteira de um Cliente Inexistente.
     * 
     * @throws ClienteNaoEncontradoException Quando já houver um Cliente com o mesmo CPF no Sistema.
     */
    @Test(expected = ClienteNaoEncontradoException.class)
    public void pegarValorDeUmaCarteiraDeUmClienteInexistente() throws ClienteNaoEncontradoException{
        assertEquals(0, controller.getValorCarteiraCliente("1234"), 0.1);
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
    public void testMelhoresClientes() throws DadoDuplicadoException, ClienteNaoEncontradoException, AcaoInexistenteException, DadoNaoEncontradoException{
        Cliente testa1 = controller.cadastrarCliente("Almir", "Endereço", "100");
        Cliente testa2 = controller.cadastrarCliente("Luciano", "Endereço", "200");
        Cliente testa3 = controller.cadastrarCliente("Jamine", "Endereço", "300");
        
        Acao acao1 = controller.cadastrarAcao("UEFS1", 25.0);
        Acao acao2 = controller.cadastrarAcao("UEFS2", 50.0);
        Acao acao3 = controller.cadastrarAcao("UEFS3", 10.0);
        
        controller.incluirAcaoCliente("100", "UEFS2", 50);
        controller.incluirAcaoCliente("300", "UEFS1", 39);
        controller.incluirAcaoCliente("200", "UEFS3", 48);
        
        Iterator testa = controller.melhoresClientes(3);
        
        assertTrue(testa.hasNext());
        Conta conta = (Conta)testa.next();
        assertEquals(testa1, conta.getCliente());
        
        conta = (Conta)testa.next();
        assertEquals(testa3, conta.getCliente());
        
        conta = (Conta)testa.next();
        assertEquals(testa2, conta.getCliente());
    
    }
}
