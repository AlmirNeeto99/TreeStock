
package br.uefs.ecomp.treeStock.view;

import br.uefs.ecomp.treeStock.Exception.*;
import br.uefs.ecomp.treeStock.facade.TreeStockFacade;
import br.uefs.ecomp.treeStock.util.*;
import br.uefs.ecomp.treeStock.model.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * Classe a qual todas as informações serão mostradas ao usuário e as informações serão
 * recebidas pelos mesmos.
 * 
 * @author AlmirNeto
 */
public class Main {
    private static TreeStockFacade controla = new TreeStockFacade();
    
    public static void menu() throws IOException, ClassNotFoundException{
        try{
            controla.carregarArquivoDeDados();
        }
        catch(FileNotFoundException a){
        }
        controla.carregarArquivoDeCotacoes();
        informacoesImportantes();
        menuUsuario();
        
    }
    
    public static void menuUsuario() throws IOException{
        clear();
        System.out.println("\t> Bem-Vindo a TreeStock!\n");
        
        System.out.println("(01)_______________Cadastro de Cliente.");
        System.out.println("(02)_______________Remoção de Cliente.");
        System.out.println("(03)_______________Manutenção de Clientes.");
        System.out.println("(04)_______________Inserir Ação.");
        System.out.println("(05)_______________Listar as N maiores carteiras dos clientes.");
        System.out.println("(06)_______________Mostrar o valor das Contas e ordená-las pelo CPF.");
        System.out.println("(07)_______________Listar ações de um Cliente e seu valor em dinheiro.");
        System.out.println("(08)_______________Excluir ações ociosas.");
        System.out.println("(09)_______________Listar Ações Cadastradas no Sistema.");
        System.out.println("(10)_______________Sair.");
        System.out.printf("> ");
        int opcao = 0;
        
        try{
            opcao = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        
        switch(opcao){
            case 1:{
                cadastroCliente();
                break;
            }
            case 2:{
                removerCliente();
                break;
            }
            case 3:{
                manutencaoCliente();
                break;
            }
            case 4:{
                cadastrarAcao();
                break;
            }
            case 5:{
                maioresCarteiras();
                break;
            }
            case 6:{
                totalContas();
                break;
            }
            case 7:{
                listarAcoesDeUmClienteESeuValor();
                break;
            }
            case 8:{
                excluirAcoesOciosas();
                break;
            }
            case 9:{
                listarAcoes();
                break;
            }
            case 10:{
                sair();
                break;
            }
        }
        
        
    }
    
    /**
     * Método que cadastra um Cliente no Sistema, que deverá especificar seu nome e senha de acesso.
     * 
     * @throws IOException
     */
    public static void cadastroCliente() throws IOException{
        clear();
        System.out.println("\t> Cadastro de Cliente:\n");
        
        System.out.printf("> Digite o nome do Cliente: ");
        String nome = Console.readString();

        System.out.printf("> Digite o CPF do Cliente :");
        String cpf = Console.readString();
        
        System.out.printf("> Digite o Endereço do Cliente: ");
        String endereco = Console.readString();
        
        Cliente cadastrado = null;
        try{
            cadastrado = controla.cadastrarCliente(nome, endereco, cpf);
        }
        catch(DadoDuplicadoException duplica){
            clear();
            System.out.println("\t> Já existe um Cliente cadastrado com esse CPF.");
            System.out.println("\t> Tente novamente com outro CPF.");
            pause();
            menuUsuario();
        }
        clear();
        System.out.println("> Cliente Cadastrado com Sucesso.");
        System.out.println("> CPF: "+cpf);
        System.out.println("> Nome: "+nome.toUpperCase());
        pause();
        
        menuUsuario();
    }
    
    /**
     * Método que remove um Cliente do Sistema.
     * 
     * @throws IOException
     */
    public static void removerCliente() throws IOException{
        clear();
        System.out.println("\t> Remoção de Cliente:\n");
        
        System.out.println("> Digite o CPF do Cliente que deseja remover do Sistema: ");
        System.out.printf("> ");
        
        String cpf = Console.readString();
        
        Cliente removido = null;
        clear();
        try{
            removido = controla.removerCliente(cpf);
        }
        catch(ClienteNaoEncontradoException a){
            System.out.println("\t> Não Existe Cliente Cadastrado com esse CPF.");
            pause();
            menuUsuario();
        }
        
        System.out.println("\t> Cliente Removido com sucesso.");
        pause();
        
        menuUsuario();
    
    }
    /**
     * Método que cadastra uma ação de uma Empresa no Sistema.
     * 
     * 
     * @throws IOException Quando o usuário deixa um espaço de digitação em Branco.
     */
    public static void cadastrarAcao() throws IOException{
        clear();
        
        System.out.println("\t> Cadastro de Ação:\n");
        
        System.out.printf("> Digite uma Sigla para Identificação das Ações: ");
        
        String nome_acao = Console.readString();

        System.out.println("> Selecione o tipo de Ação:");
        System.out.println("(01)_____ON.");
        System.out.println("(02)_____PN.");
        System.out.printf("> ");
        
        int acao = 0;
        try{
            acao = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            cadastrarAcao();
        }
        if(acao != 1 && acao != 2){
            clear();
            System.out.println("\t> Digite um Número Entre 1 e 2.");
            pause();
            cadastrarAcao();
        }
        System.out.printf("> Digite o Valor da Ação: ");
        double valor = 0.0;
        
        try{
            valor = Console.readDouble();
        }
        catch(IOException a){
            clear();
            System.out.println("\t> Não deixe o Espaço de Digitação em Branco.");
            pause();
            cadastrarAcao();
        }

        Acao verifica = null;
        if(acao == 1){
            try{
                verifica = controla.cadastrarAcao(nome_acao, valor);
            }
            catch(DadoDuplicadoException e){
                clear();
                System.out.println("\t> Já existe uma Ação com essa Sigla.");
                pause();
                menuUsuario();
            }
        }
        
        else if(acao == 2){
            System.out.printf("> Digite o valor dos Dividendos da Ação: ");
            double dividendos = 0.0;
            
            try{
                dividendos = Console.readDouble();
            }
            catch(IOException a){
                clear();
                System.out.println("\t> Não deixe o Espaço de Digitação em Branco.");
                pause();
                cadastrarAcao();
            }
            try{
                verifica = controla.cadastrarAcao(nome_acao, valor, dividendos);
            }
            catch(DadoDuplicadoException e){
                clear();
                System.out.println("\t> Já existe uma Ação com essa Sigla.");
                pause();
                menuUsuario();
            }
        }
        clear();
        System.out.println("\t> Ação Cadastrada com Sucesso.");
        pause();
        menuUsuario();
   
    }
    
    public static void manutencaoCliente() throws IOException{
        clear();
        System.out.println("\t Manutenção de Cliente:\n");
        
        System.out.printf("> Digite o CPF do Cliente que deseja atuar: ");
        String cpf = Console.readString();
        
        boolean verifica = controla.clienteCadastrado(cpf);
        clear();
        if(verifica == false){
            System.out.println("\t> O Cliente com esse CPF não está cadastrado no Sistema.");
            pause();
            menuUsuario();
        }
        System.out.println("\t Manutenção de Cliente:\n");
        System.out.println("(01)__________Alterar Quantidade de Ações.");
        System.out.println("(02)__________Remover Ação da Carteira.");
        System.out.println("(03)__________Incluir Ação.");
        System.out.printf("> ");
        
        int opcao = 0;
        try{
            opcao = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        
        switch(opcao){
            case 1:{
                alterarAcaoCliente(cpf);
                break;
            }
            case 2:{
                removerAcaoCliente(cpf);
                break;
            }
            case 3:{
                incluirAcaoCliente(cpf);
                break;
            }
            default:{
                clear();
                System.out.println("\t> Digite um número entre 1 e 3.");
                pause();
                menuUsuario();
            }
        }
        
        
        
    }
    
    public static void alterarAcaoCliente(String cpf) throws IOException{
        clear();
        
        System.out.println("\t Alteração de Cliente:\n");
        
        System.out.printf("> Digite a Sigla que deseja alterar: ");
        String sigla = Console.readString();
        
        System.out.println("> Digite quantas ações deseja adicionar ou remover: ");
        System.out.println("> Para retirar certa quantidade de ação, digite um valor negativo.");
        System.out.printf(">>>> ");
        
        int quantidade = 0;
        
        try{
            quantidade = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        clear();
        try{
            controla.setQuantidadeAcaoCliente(cpf, sigla, quantidade);
        }
        catch(ClienteNaoEncontradoException | DadoDuplicadoException e){
            //Não deve entrar aqui.
        }
        catch(AcaoInexistenteException e){
            System.out.println("\t > A Ação escolhida, não existe no Sistema.");
            pause();
            menuUsuario();
        }
        catch(AcaoNaoEncontradaException e){
            System.out.println("\t> O Cliente Não possui essa ação.");
            pause();
            menuUsuario();
        }
        catch(CarteiraVaziaException e){
            System.out.println("\t> O Cliente não possui ações.");
            pause();
            menuUsuario();
        }
        
        System.out.println("\t> Alteração Realizada com Sucesso.");
        pause();
        menuUsuario();
    }
    
    public static void incluirAcaoCliente(String cpf) throws IOException{
        clear();
        System.out.println("\t> Inclusão de Ação:\n");
        
        System.out.printf("> Digite a Ação que deseja adicionar ao Cliente: ");
        
        String sigla = Console.readString();
        
        System.out.printf("> Digite quantas Ações deseja adicionar: ");
        int quantidade = 0;
        
        try{
            quantidade = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        clear();
        try{
            controla.incluirAcaoCliente(cpf, sigla, quantidade);
        }
        catch(ClienteNaoEncontradoException | DadoDuplicadoException e){
            //Não deve entrar aqui.
        }
        catch(AcaoInexistenteException e){
            System.out.println("\t A Ação selecionada não existe.");
            pause();
            menuUsuario();
        }
        
        System.out.println("\t> Inclusão Efetuada com Sucesso.");
        pause();
        menuUsuario();
        
    }
    
    public static void removerAcaoCliente(String cpf) throws IOException{
        clear();
        
        System.out.println("\t> Remoção de Ação do Cliente:\n");
        
        System.out.printf("> Digite a Sigla da Ação que deseja remover: ");
        String sigla = Console.readString();
        try{
            controla.removerAcaoCliente(cpf, sigla);
        }
        catch(ClienteNaoEncontradoException e){
            //Não deve entrar aqui.
        }
        catch(AcaoInexistenteException e){
            System.out.println("\t> Essa Ação não está cadastrada no Sistema.");
            pause();
            menuUsuario();
        }
        catch(CarteiraVaziaException e){
            System.out.println("\t> O Cliente Não possui Ações.");
            pause();
            menuUsuario();
        }
        catch(AcaoNaoEncontradaException e){
            System.out.println("\t> O Cliente não possui essa Ação.");
            pause();
            menuUsuario();
        }
        
        System.out.println("\t> Ação Removida com Sucesso.");
        pause();
        menuUsuario();
    }
    
    public static void maioresCarteiras() throws IOException{
        System.out.println("\t Maiores Clientes:\n");
        
        System.out.printf("> Digite quantos clientes deseja visualizar: ");
        int quantidade = 0;
        
        try{
            quantidade = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        Iterator it = null;
        clear();
        try{
            it = controla.melhoresClientes(quantidade);
        }
        catch(ClienteNaoEncontradoException e){
            System.out.println("\t Não Existem Clientes suficientes para serem listados.");
            pause();
            menuUsuario();
        }
        System.out.println("\t Listando:\n");
        while(it.hasNext()){
            Conta conta = (Conta)it.next();
            System.out.println("Nome: "+conta.getCliente().getNome());
            System.out.println("CPF: "+conta.getCliente().getCpf());
            System.out.println("Valor na Carteira: "+conta.getCliente().getCarteira().getTotal_carteira());
            System.out.println("=======================================================================");
        }
        pause();
        menuUsuario();
        
    }
    /**
     * Método que lista as Ações de um Cliente e mostra o valor total que aquela ação representa em sua carteira.
     * 
     * @throws IOException
     */
    public static void listarAcoesDeUmClienteESeuValor() throws IOException{
        
        clear();
        System.out.println("\t> Listar Ações de um Cliente e Seu Valor:\n");
        
        System.out.printf("> Digite o CPF do Cliente: ");
        String cpf = Console.readString();
        clear();
        
        if(!controla.clienteCadastrado(cpf)){
            System.out.println("\t> O Cliente Não está cadastrado no Sistema.");
            pause();
            menuUsuario();
        }
        Iterator it = null;
        try{
            it = controla.listarPessoas();
        }
        catch(ClienteNaoEncontradoException e){
            clear();
            System.out.println("\t Não Existem Clientes cadastrados no Sistema.");
            pause();
            menuUsuario();
            
        }
        while(it.hasNext()){
            Cliente cliente = (Cliente)it.next();
            Carteira carteira = cliente.getCarteira();
            Pares pares = carteira.getAcoes();
            if(pares.estaVazia()){
                System.out.println("\t O Cliente não possui ações.");
                pause();
                menuUsuario();
            }
            Iterator it_par = pares.iterator();
            while(it_par.hasNext()){
                IPar par = (IPar)it_par.next();
                Acao acao = (Acao)par.getChave();
                int quantidade = (int)par.getValor();
                double total = acao.getValor() * quantidade;
                System.out.println("Ação: "+acao.getSigla().toUpperCase());
                System.out.println("Valor: "+acao.getValor());
                System.out.println("Total: "+total);
                System.out.println("==============================================");
            }
            pause();
        }
        menuUsuario();
    }
    /**
     * Método que exclui as ações que não pertecem a nenhuma carteira dos Clientes do Sistema.
     * 
     * @throws IOException
     */
    public static void excluirAcoesOciosas() throws IOException{
        clear();
        System.out.println("\t> Excluir Ações Ociosas:\n");
        System.out.println("\t Deseja excluir as Ações Ociosas?\nIsso não poderá ser desfeito.");
        
        System.out.println("(01)___Sim.");
        System.out.println("(02)___Não.");
        
        int opcao = 0;
        
        try{
            opcao = Console.readInt();
        }
        catch(IOException | NumberFormatException e){
            clear();
            System.out.println("\t Por Favor, digite um número inteiro.");
            pause();
            menuUsuario();
        }
        switch(opcao){
            case 1:{
                try{
                    controla.excluirAcoesOciosas();
                }
                catch(ClienteNaoEncontradoException e){
                    clear();
                    System.out.println("\t> Não Existem Clientes cadastrados no Sistema.");
                    pause();
                    menuUsuario();
                }
                catch(AcaoInexistenteException e){
                    clear();
                    System.out.println("\t> Não Existem Ações cadastradas no Sistema.");
                    pause();
                    menuUsuario();
                }
                break;
            }
            case 2:{
                menuUsuario();
                break;
            }
            default:{
                clear();
                System.out.println("\t Opção Inválida.");
                pause();
                menuUsuario();
            }
        }
        System.out.println("\t> Limpeza Efetuada com Sucesso.");
        pause();
        menuUsuario();
    }
    /**
     * Método que Lista as ações do Sistema.
     * 
     * @throws IOException 
     */
    public static void listarAcoes() throws IOException{
        clear();
        System.out.println("\t> Listagem de Ações:\n");
        
        Iterator listar = null;
        try{
            listar = controla.listarAcoes();
        }
        catch(AcaoInexistenteException e){
            clear();
            System.out.println("\t Não existem ações cadastradas no Sistema.");
            pause();
            menuUsuario();
        }
        System.out.println("\t > Listando Ações:\n");
        while(listar.hasNext()){
            Acao acao = (Acao)listar.next();
            System.out.println("> Sigla: "+acao.getSigla().toUpperCase());
            System.out.println("> Valor: "+acao.getValor());
            System.out.println("======================================================");
        }
        pause();
        menuUsuario();
       
    }
    /**
     * Método que lista os Clientes pelo CPF e mostra o seu Saldo e Montante.
     * 
     * @throws IOException
     */
    public static void totalContas() throws IOException{
        clear();
        System.out.println("\t> Listagem de Contas e Seu Valor:\n");
        
        Iterator it = null;
        try{
            it = controla.listarPessoas();
        }
        catch(ClienteNaoEncontradoException e){
            clear();
            System.out.println("\t> Não existem clientes cadastrados no Sistema.");
            pause();
            menuUsuario();
        }
        
        Pares pares = controla.listarContas();
        
        while(it.hasNext()){
            Cliente cliente = (Cliente)it.next();
            Conta conta = null;
            try{
                conta = (Conta)pares.get(cliente);
            }
            catch(DadoNaoEncontradoException e){
                //Não deve entrar aqui.
            }
            System.out.println("> Nome: "+cliente.getNome().toUpperCase());
            System.out.println("> CPF: "+cliente.getCpf());
            System.out.println("> Saldo: "+conta.getSaldo());
            System.out.println("> Montante: "+conta.getMontante());
            System.out.println("========================================================================");
        }
        pause();
        menuUsuario();
    }
    /**
     * Método que salva os Dados do Sistema e fecha o programa.
     * 
     * @throws IOException
     */
    public static void sair() throws IOException{
        clear();
        System.out.println("\t Salvando dados no Arquivo.");
        controla.salvarArquivoDeDados();
        clear();
        clear();
        System.out.println("\t> Obrigado por Utilizar nosso Sistema.");
        
        System.exit(0);
    }
    
    public static void informacoesImportantes() throws IOException{
        System.out.println("\t INFORMAÇÕES IMPORTANTES!!!\n");
        
        System.out.println("> Para funcionamento correto do Sistema é necessário que o Arquivo de cotações seja colocado, na mesma pasta do Projeto.");
        System.out.println("> Os Dados da Última sessão são carregados automatimente ao Abrir o Programa, para isso, é necessário que ao desejar encerrar"
                + "\n o programa, você deve selecionar a opção '10' do Menu Principal, pois é ele que é responsável por salvar o arquivo de Dados.");
        pause();
    }
    
    /**
     * Método que "limpa" o Console com o intuito de melhorar a Experiência de utilização do Usuário.
     */
    private static void clear(){
        
        System.out.println("");
        System.out.println("");
        System.out.println("--------------------------------------------------------------------------");
    }
    
    /**
     * Método que espera que o Usuário pressione "ENTER" para continuar com a Execução.
     * 
     * @throws IOException Quando o usuário não pressiona ENTER.
     */
    public static void pause() throws IOException{
        System.out.println("");
        System.out.println("\t Pressione ENTER para continuar...");
        System.in.read();
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException{
        menu();
    }
}
