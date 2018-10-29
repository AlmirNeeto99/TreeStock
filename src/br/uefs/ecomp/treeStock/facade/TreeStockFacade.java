package br.uefs.ecomp.treeStock.facade;

import br.uefs.ecomp.treeStock.controller.*;
import br.uefs.ecomp.treeStock.model.*;
import br.uefs.ecomp.treeStock.util.*;
import br.uefs.ecomp.treeStock.Exception.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Iterator;

public class TreeStockFacade {

    private TreeStockController controller;

    public TreeStockFacade() {
        controller = new TreeStockController();
    }
    /**
     * Método que cadastra um Cliente no Sistema e o coloca na Árvore de Dados.
     * 
     * @param nome Nome do Cliente a ser cadastrado.
     * @param endereco Endereço do Cliente a ser cadastrado.
     * @param cpf CPF do Cliente a ser cadastrado.
     * 
     * @throws DadoDuplicadoException Quando já existe um Usuário com o CPF passado como parâmetro.
     * 
     * @return Um Objeto do tipo CLIENTE caso este seja cadastrado no Sistema.
     */
    public Cliente cadastrarCliente(String nome, String endereco, String cpf) throws DadoDuplicadoException {
        return controller.cadastrarCliente(nome, endereco, cpf);
    } 
    /**
     * Método responsável por Cadastrar uma ação no Sistema.
     
     * @param sigla Sigla da Ação que será cadastrada.
     * @param valor Valor de uma ação.
     * @throws DadoDuplicadoException Quando já houver uma ação existente no Sistema com a Sigla passada.
     * 
     * @return NULL caso a ação seja cadastrada.
     */
    public Acao cadastrarAcao(String sigla, double valor) throws DadoDuplicadoException{
        return controller.cadastrarAcao(sigla, valor);
    }
    /**
     * Método responsável por Cadastrar uma ação no Sistema.
     
     * @param sigla Sigla da Ação que será cadastrada.
     * @param valor Valor de uma ação.
     * @param dividendos Valor de um dividendo que o Cliente receberá.
     * @throws DadoDuplicadoException Quando já houver uma ação existente no Sistema com a Sigla passada.
     * 
     * @return NULL caso a ação seja cadastrada.
     */
    public Acao cadastrarAcao(String sigla, double valor, double dividendos) throws DadoDuplicadoException{
        return controller.cadastrarAcao(sigla, valor, dividendos);
    }
    
    /**
     * Método que retorna um iterator para listagens de Ações no Sistema.
     * 
     * @return Um Iterador que a lista de Ações.
     * 
     * @throws AcaoInexistenteException Quando não houver ações cadastradas no Sistema.
     */
    public Iterator listarAcoes() throws AcaoInexistenteException{
        return controller.listarAcoes();
    }
    /**
     * Método que remove um determinado Cliente do Sistema.
     * 
     * @param cpf CPF do Cliente que será removido do Sistema.
     * @return O Cliente, caso este exista e seja removido.
     * 
     * @throws ClienteNaoEncontradoException Caso o cliente passado como parâmetro não exista.
     */
    public Cliente removerCliente(String cpf) throws ClienteNaoEncontradoException {
        return controller.removerCliente(cpf);
    }
    /**
     * Método que verifica se determinado Cliente está cadastrado no Sistema.
     * 
     * @param cpf CPF do Cliente que será buscado no Sistema.
     * 
     * @return TRUE caso o cliente exista, FALSE caso contrário.
     */
    public boolean clienteCadastrado(String cpf) {
        return controller.clienteCadastrado(cpf);
    }
    /**
     * Método que remove uma Ação do Sistema.
     * 
     * @param siglaAcao Sigla da Ação a ser removida do Sistema.
     * 
     * @return A Ação, caso esta seja removida.
     * 
     * @throws AcaoInexistenteException Caso a Ação passada como parâmetro não exista.
     */
    public Acao removerAcao(String siglaAcao) throws AcaoInexistenteException {
        return controller.removerAcao(siglaAcao);
    }
    /**
     * Método que verifica se determinada Ação está cadastrada no Sistema.
     * 
     * @param siglaAcao Sigla da Ação a ser buscada no Sistema.
     * 
     * @return TRUE caso a ação exista no Sistema, FALSE caso contrário.
     */
    public boolean acaoCadastrada(String siglaAcao) {
        return controller.acaoCadastrada(siglaAcao);
    }
    /**
     * Método que altera o valor de determinada ação e altera este em todas as Carteiras de todos os 
     * Clientes que a possue.
     * 
     * @param siglaAcao, Sigla da Ação que será alterada.
     * 
     * @param novoValor Novo Valor para a ação passada como parâmetro.
     * 
     * @throws AcaoInexistenteException Caso a Ação passada como parâmetro não exista.
     */
    public void setValorAcao(String siglaAcao, double novoValor) throws AcaoInexistenteException {
        controller.setValorAcao(siglaAcao, novoValor);
    }
    /**
     * Retorna o valor de uma determinada ação.
     * 
     * @param siglaAcao Sigla da ação a ser buscada.
     * 
     * @throws AcaoInexistenteException Quando a Ação não existir no Sistema.
     * 
     * @return O Valor da Ação
     */
    public double getValorAcao(String siglaAcao) throws AcaoInexistenteException {
        return controller.getValorAcao(siglaAcao);
    }

    /**
     * Retorna os clientes ordenados pelo nome.
     *
     * @return os clientes ordenados pelo nome.
     * 
     * @throws ClienteNaoEncontradoException Quando não houver clientes cadastrados no sistema.
     */
    public Iterator listarPessoas() throws ClienteNaoEncontradoException {
        return controller.listarPessoas();
    }
    /**
     * Método que adquire determinada Ação e a coloca na carteira do Cliente requisitado.
     * 
     * @param cpf CPF do Cliente que receberá a ação.
     * @param siglaAcao Sigla da Ação a qual será colocada na carteira do Cliente, caso esta exista.
     * @param quantidade Quantidade de Ações que serão adicionadas na Carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Caso o Cliente que possue o CPF passado como parâmetro 
     * não exista.
     * 
     * @throws DadoDuplicadoException Não será lançada, mas o NB exije.
     * 
     * @throws AcaoInexistenteException Caso a ação passada como parâmetro na esteja cadastrada no Sistema.
     */
    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException{
        controller.incluirAcaoCliente(cpf, siglaAcao, quantidade);
    }
    /**
     * Método que remove uma ação da Carteira do Cliente.
     * 
     * @param cpf CPF do Cliente o qual a ação passada como parâmetro será removida.
     * @param siglaAcao Sigla da Ação a qual será removida da Carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Caso o Cliente passado como parâemtro não exista.
     * @throws AcaoNaoEncontradaException Caso a ação passada como parâmetro não esteja na carteira do Cliente.
     * @throws AcaoInexistenteException Caso a ação passada como parâmetro não esteja cadastrada no Sistema.
     * @throws CarteiraVaziaException Caso a carteira do Cliente esteja vazia.
     */
    public void removerAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoInexistenteException, CarteiraVaziaException, AcaoNaoEncontradaException {
        controller.removerAcaoCliente(cpf, siglaAcao);
    }
    /**
     * Método que retorna a Quantidade de Determinada ação de Determinado Cliente.
     * 
     * @param cpf CPF do Cliente que será verificado.
     * @param siglaAcao Sigla da Ação a qual será verificada a quantidade.
     * 
     * @return A Quantidade de determinada Ação de determinado Cliente.
     * 
     * @throws ClienteNaoEncontradoException Caso o cliente passado como parâmetro não esteja
     * cadastrado no Sistema.
     * 
     * @throws CarteiraVaziaException Caso a carteira do Cliente não possua nenhuma ação.
     * 
     * @throws AcaoInexistenteException Caso a ação passada como parâmetro não esteja cadastrada no Sisttema.
     * 
     * @throws AcaoNaoEncontradaException Caso a ação passada como parâmetro não pertença a carteira no Cliente.
     */
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoInexistenteException, CarteiraVaziaException, AcaoNaoEncontradaException {
        return  controller.getQuantidadeAcaoCliente(cpf, siglaAcao);
    }
    /**
     * Método que altera a quantidade de Ações de um determinado Cliente.
     * 
     * @param cpf CPF do Cliente o qual a quantidade será alterada.
     * @param siglaAcao Sigla da Ação a qual a quantidade será alterada.
     * @param quantidade Quantidade de Ações que será adicionadas a carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Caso o cliente passado como parâmetro não esteja
     * cadastrado no Sistema.
     * 
     * @throws CarteiraVaziaException Caso a carteira do Cliente não possua nenhuma ação.
     * 
     * @throws AcaoInexistenteException Caso a ação passada como parâmetro não esteja cadastrada no Sisttema.
     * 
     * @throws AcaoNaoEncontradaException Caso a ação passada como parâmetro não pertença a carteira no Cliente.
     * 
     * @throws DadoDuplicadoException Não será lançada, mas o NB exije.
     */
    public void setQuantidadeAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, AcaoInexistenteException, DadoDuplicadoException, AcaoNaoEncontradaException, CarteiraVaziaException {
        controller.setQuantidadeAcaoCliente(cpf, siglaAcao, quantidade);
    }
    /**
     * Método que busca e Retorna a carteira do Cliente.
     * 
     * @param cpf CPF do Cliente o qual sua carteira será buscada e retornada.
     * 
     * @return A Carteira do Cliente, caso exista.
     * @throws DadoNaoEncontradoException Caso o Cliente com o CPF passado como parâmetro não exista.
     * @throws CarteiraVaziaException Quando a carteira do Cliente estiver vazia.
     */
    public Pares getCarteiraCliente(String cpf) throws DadoNaoEncontradoException, CarteiraVaziaException{
        return controller.getCarteiraCliente(cpf);
    }
    /**
     * Método que retorna o Valor total da Carteira de um Cliente.
     * 
     * @param cpf CPF do Cliente o qual será buscado , calculado e retornado o valor de sua carteira.
     * @return O Valor total da Carteira do Cliente.
     * 
     * @throws ClienteNaoEncontradoException Caso o Cliente passado como parâmetro não exista.
     */
    public double getValorCarteiraCliente(String cpf) throws ClienteNaoEncontradoException {
        return controller.getValorCarteiraCliente(cpf);
    }

    /**
     * Retorna os k melhores clientes em ordem descrescente de valor da
     * carteira.
     *
     * @param k os k melhores clientes
     * @return os k melhores clientes em ordem descrescente de valor da
     * carteira.
     * 
     * @throws ClienteNaoEncontradoException Caso K seja maior que a quantidade de clientes no Sistema.
     */
    public Iterator melhoresClientes(int k) throws ClienteNaoEncontradoException{
        return controller.melhoresClientes(k);
    }
    /**
     * Método que carrega o Arquivo de Cotações e Atualiza o Sistema.
     * 
     * @throws FileNotFoundException Quando o Arquivo não existir no Computador do Usuário.
     */
    public void carregarArquivoDeCotacoes() throws FileNotFoundException{
        controller.carregarArquivoDeCotacoes();
    }
    /**
     * Método que Carrega os Dados da Última Sessão do Sistema.
     * 
     * @throws FileNotFoundException Quando o arquivo não Existir no Computador.
     * @throws IOException
     * @throws ClassNotFoundException
     * 
     */
    public void carregarArquivoDeDados() throws IOException, FileNotFoundException, ClassNotFoundException{
        controller.carregarArquivoDeDados();
    }
    /**
     * Método que exclui as ações que não estão em nenhuma carteira.
     * 
     * @throws AcaoInexistenteException Quando não houver ações cadastradas no sistema.
     * @throws ClienteNaoEncontradoException Quando não houver clientes cadastrados no Sistema.
     */
    public void excluirAcoesOciosas() throws AcaoInexistenteException, ClienteNaoEncontradoException{
        controller.excluirAcoesOciosas();
    }
    /**
     * Método que Retorna o conjunto de Clientes e Contas.
     * 
     * @return O conjunto de Clientes e Contas.
     */
    public Pares listarContas(){
        return controller.listarContas();
    }
    public void salvarArquivoDeDados() throws IOException{
        controller.salvarArquivoDeDados();
    }
}