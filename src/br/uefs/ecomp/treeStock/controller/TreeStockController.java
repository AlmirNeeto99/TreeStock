
package br.uefs.ecomp.treeStock.controller;

import br.uefs.ecomp.treeStock.Exception.*;
import br.uefs.ecomp.treeStock.model.*;
import br.uefs.ecomp.treeStock.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *  Classe responsável pela Execução das Informações passadas para ela.
 * 
 * 
 * @author AlmirNeto
 */
public class TreeStockController {
    
    private ArrayList<String> dados;
    private Arvore clientes;
    private LinkedList<Acao> acoes;
    private Pares contas;
    
    /**
     * Construtor da Controller, responsável pelo Controle do Sistema.
     * 
     * Alteração, Inserção e Remoção das Informações para ela passada.
     * 
     */
    public TreeStockController(){
        clientes = new Arvore();
        acoes = new LinkedList();
        dados = new ArrayList();
        contas = new Pares();
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
    public Cliente cadastrarCliente(String nome, String endereco, String cpf) throws DadoDuplicadoException{
        
        Cliente busca = new Cliente(cpf);
        
        try{
            clientes.buscar(busca);
        }
        catch(DadoNaoEncontradoException a){
            Cliente novo = new Cliente(nome, cpf, endereco);

            Conta nova_conta = new Conta(novo);
            contas.put(novo, nova_conta);
            clientes.inserir(novo);
            return novo;
        }
        
        throw new DadoDuplicadoException();      
    }
    /**
     * Método que remove um determinado Cliente do Sistema.
     * 
     * @param cpf CPF do Cliente que será removido do Sistema.
     * @return O Cliente, caso este exista e seja removido.
     * 
     * @throws ClienteNaoEncontradoException Caso o cliente passado como parâmetro não exista.
     */
    public Cliente removerCliente(String cpf) throws ClienteNaoEncontradoException{
        Cliente busca = new Cliente(cpf);
        Cliente remover = null;
        
        try{
            remover = (Cliente) clientes.buscar(busca);   
        }
        catch(DadoNaoEncontradoException a){
            throw new ClienteNaoEncontradoException();
        }
        try{
            clientes.remover(remover);
        }
        catch(DadoNaoEncontradoException a){
            throw new ClienteNaoEncontradoException();
        }
        try{
            contas.remove(remover);
        }
        catch(DadoNaoEncontradoException e){
            //Não deve Lançar
        }
        return remover;
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
        Acao compara = new Acao(sigla, valor);
        if(acoes.contains(compara)){
            throw new DadoDuplicadoException();
        }
        acoes.add(compara);
        return compara;
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
        AcaoPN compara = new AcaoPN(sigla, valor, dividendos);
        if(acoes.contains(compara)){
            throw new DadoDuplicadoException();
        }
        acoes.add(compara);
        return compara;
    }
    /**
     * Método que retorna um iterator para listagens de Ações no Sistema.
     * 
     * @return Um Iterador que a lista de Ações.
     * 
     * @throws AcaoInexistenteException Quando não houver ações cadastradas no Sistema.
     */
    public Iterator listarAcoes() throws AcaoInexistenteException{
        if(acoes.isEmpty()){
            throw new AcaoInexistenteException();
        }
        return acoes.iterator();
    }
    /**
     * Método que atualiza os dados do Sistema a partir do arquivo de Texto.
     * 
     * @param position Posição atual do arquivo que será atualizado.
     */
    public void atualizarDados(int position){
        if(position > dados.size() - 1){
            atualizarCarteiras();
            return;
        }
        String[] dado = new String[4];
        dado[0] = dados.get(position);
        dado[1] = dados.get(position+1);
        dado[2] = dados.get(position+2);
        dado[3] = dados.get(position+3);
        double valor = Double.parseDouble(dado[2]);
        valor /= 100;
        double dividendos = Double.parseDouble(dado[3]);
        dividendos /= 100;
        if(dado[1].equalsIgnoreCase("PN")){
            AcaoPN busca = new AcaoPN(dado[0], valor, dividendos);
            int posicao = 0;
            if(acoes.contains(busca)){
                Iterator alterar = acoes.iterator();
                while(alterar.hasNext()){
                    Acao altera = (Acao)alterar.next();
                    if(altera.getSigla().equalsIgnoreCase(dado[0])){
                        if(altera instanceof AcaoPN){
                            break;
                        }
                    }
                    posicao++;
                }
            AcaoPN alterado = (AcaoPN)acoes.get(posicao);
            alterado.setDividendos(dividendos);
            alterado.setValor(valor);
            }
            else{
                try{
                    cadastrarAcao(dado[0], valor, dividendos);
                }
                catch(DadoDuplicadoException a){
                }
            }
        }
        else {
            Acao busca = new Acao(dado[0], valor);
            int posicao = 0;
            if(acoes.contains(busca)){
                Iterator alterar = acoes.iterator();
                while(alterar.hasNext()){
                    Acao altera = (Acao)alterar.next();
                    if(altera.getSigla().equalsIgnoreCase(dado[0])){
                        if(altera instanceof Acao){
                            break;
                        }
                    }
                    posicao++;
                }
            Acao alterado = (Acao)acoes.get(posicao);
            alterado.setValor(valor);
            }
            else{
                try{
                    cadastrarAcao(dado[0], valor);
                }
                catch(DadoDuplicadoException a){
                }
            }
        }
        
        atualizarDados(position += 4);
    }
    /**
     * Método que carrega o Arquivo de Cotações e Atualiza o Sistema.
     * 
     * @throws FileNotFoundException Quando o Arquivo não existir no Computador do Usuário.
     */
    public void carregarArquivoDeCotacoes() throws FileNotFoundException{
        File file = new File("Cotações.txt");

        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            String texto = sc.next();
            dados.add(texto);
        }
        atualizarDados(3);
    }
    /**
     * Método que salva os Dados do Sistema em um arquivo de Texto, para um possível retorno.
     * 
     * @throws FileNotFoundException Quando o Arquivo Não Existir.
     * @throws IOException
     */
    public void salvarArquivoDeDados() throws FileNotFoundException, IOException{
        
        File file = new File("Dados.data");
        ObjectOutputStream saida;
        saida = new ObjectOutputStream(new FileOutputStream(file));
        
        saida.writeObject(clientes);
        saida.writeObject(contas);
        saida.writeObject(acoes);
        saida.close();
    
    }
    /**
     * Método que Carrega os Dados da Última Sessão do Sistema.
     * 
     * @throws FileNotFoundException Quando o arquivo não Existir no Computador.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void carregarArquivoDeDados() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        File file = new File("Dados.data");
        
        ObjectInputStream entrada;
        entrada = new ObjectInputStream(new FileInputStream(file));
        
        clientes = (Arvore) entrada.readObject();
        contas = (Pares) entrada.readObject();
        acoes = (LinkedList) entrada.readObject();
        entrada.close();
        file.delete();
    }
    /**
     * Método que busca uma ação no Sistema por Sigla.
     * 
     * @param sigla Sigla que será busca na lista.
     * 
     * @throws DadoNaoEncontradoException Quando não existir uma ação com tal sigla.
     * 
     * @return A Ação caso ela exista.
     */
    public Acao buscaAcaoPorSigla(String sigla) throws DadoNaoEncontradoException{
        Acao acao = new Acao(sigla);
        if(!acoes.contains(acao)){
            throw new DadoNaoEncontradoException();
        }
        Iterator busca = acoes.iterator();
        while(busca.hasNext()){
            Acao buscada = (Acao)busca.next();
            if(buscada.getSigla().equalsIgnoreCase(sigla)){
                return buscada;
            }
        }
        return null;
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
        Cliente pessoa = (Cliente)clientes.buscar(new Cliente(cpf));
        
        Carteira carteira = pessoa.getCarteira();
        
        int tamanho = carteira.getAcoes().size();
        if(tamanho == 0){
            throw new CarteiraVaziaException();
        }
        return carteira.getAcoes();
    }
    /**
     * Método que verifica se determinado Cliente está cadastrado no Sistema.
     * 
     * @param cpf CPF do Cliente que será buscado no Sistema.
     * 
     * @return TRUE caso o cliente exista, FALSE caso contrário.
     */
    public boolean clienteCadastrado(String cpf) {
        Cliente buscado = new Cliente(cpf);
        Cliente encontrado = null;
        try{
            encontrado = (Cliente)clientes.buscar(buscado);
        }
        catch(DadoNaoEncontradoException a){
            return false;
        }
        
        return true;
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
        Acao removido = new Acao(siglaAcao);
        
        if(acoes.contains(removido)){
            acoes.remove(removido);
            return removido;
        }
        throw new AcaoInexistenteException();   
    }
    /**
     * Método que verifica se determinada Ação está cadastrada no Sistema.
     * 
     * @param siglaAcao Sigla da Ação a ser buscada no Sistema.
     * 
     * @return TRUE caso a ação exista no Sistema, FALSE caso contrário.
     */
    public boolean acaoCadastrada(String siglaAcao) {
        Iterator buscar = acoes.iterator();
        while(buscar.hasNext()){
            Acao verifica = (Acao)buscar.next();
            if(verifica.getSigla().equalsIgnoreCase(siglaAcao)){
                return true;
            }
        }
        return false;
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
        Acao buscar = new Acao(siglaAcao);
        if(acoes.contains(buscar)){
            Iterator alterar = acoes.iterator();
            while(alterar.hasNext()){
                Acao acao = (Acao)alterar.next();
                if(acao.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao.setValor(novoValor);
                }
            }
        }
        else{
            throw new AcaoInexistenteException();
        }
        Iterator it = clientes.iterator();
        while(it.hasNext()){
            Cliente cliente = (Cliente)it.next();
            Iterator ac = cliente.getCarteira().getAcoes().iterator();
            while(ac.hasNext()){
                IPar acs = (IPar) ac.next();
                Acao acao = (Acao)acs.getChave();
                if(acao.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao.setValor(novoValor);
                }
            }
        }
    }
    /**
     * Método que verifica o preço de determinada Ação no Sistema.
     * 
     * @param siglaAcao Sigla da Ação que será verificada.
     * @return O valor da Ação.
     * 
     * @throws AcaoInexistenteException Caso a ação em questão não exista no Sistema.
     */
    public double getValorAcao(String siglaAcao) throws AcaoInexistenteException {
       Acao busca = new Acao(siglaAcao);
       if(acoes.contains(busca)){
           Iterator buscar = acoes.iterator();
           while(buscar.hasNext()){
               Acao verifica = (Acao)buscar.next();
               if(busca.getSigla().equalsIgnoreCase(siglaAcao)){
                   return verifica.getValor();
               }
           }
       }
       throw new AcaoInexistenteException();
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
    public void incluirAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, DadoDuplicadoException, AcaoInexistenteException {
        Cliente recebe = new Cliente(cpf);
        Cliente busca = null;
        
        try{
            busca = (Cliente)clientes.buscar(recebe);
        }
        catch(DadoNaoEncontradoException a){
           throw new ClienteNaoEncontradoException();
        }
       
        Carteira carteira = busca.getCarteira();
       
        Pares acoes_cliente = carteira.getAcoes();
       
        Acao acao = new Acao(siglaAcao);
        if(acoes.contains(acao)){
            Iterator buscar = acoes.iterator();
            while(buscar.hasNext()){
                Acao encontrada = (Acao)buscar.next();
                if(encontrada.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao = encontrada;
                }
            }
        }
        else{
            throw new AcaoInexistenteException();
        }
            
        int qnt_acoes;
        try{
            qnt_acoes = (int)acoes_cliente.get(acao);
        }
        catch(DadoNaoEncontradoException a){
            acoes_cliente.put(acao, quantidade);
            return;
        }
        
        qnt_acoes += quantidade;
        try{
            acoes_cliente.remove(acao);
        }
        catch(DadoNaoEncontradoException a){
            //Nunca entrará aqui, ou não deveria entrar. xD
        }
        
        acoes_cliente.put(acao, qnt_acoes);
        
        Iterator it = contas.iterator();
        while(it.hasNext()){
            Conta conta = (Conta)it.next();
            if(conta.getCliente().getCpf().equals(cpf)){
                double avulso = conta.getMontante();
                double saldo = conta.getSaldo();
            }
        }

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
    public void removerAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, AcaoInexistenteException, AcaoNaoEncontradaException, CarteiraVaziaException{
        Cliente remover = new Cliente(cpf);
        Cliente cliente = null;
        
        try{
            cliente = (Cliente)clientes.buscar(remover);
        }
        catch(DadoNaoEncontradoException a){
            throw new ClienteNaoEncontradoException();
        }
        
        Carteira carteira = cliente.getCarteira();
        
        if(carteira.getAcoes().estaVazia()){
            throw new CarteiraVaziaException();
        }
        
        Pares acoes_cliente = carteira.getAcoes();
        
        Acao acao = new Acao(siglaAcao);
        if(acoes.contains(acao)){
            Iterator buscar = acoes.iterator();
            while(buscar.hasNext()){
                Acao encontrada = (Acao)buscar.next();
                if(encontrada.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao = encontrada;
                }
            }
        }
        else{
            throw new AcaoInexistenteException();
        }
        int quantidade;
        try{
            quantidade = (int)acoes_cliente.get(acao);
        }
        catch(DadoNaoEncontradoException a){
            throw new AcaoNaoEncontradaException();
        }
        
        try{
            acoes_cliente.remove(acao);
        }
        catch(DadoNaoEncontradoException a){
            //Nunca entrará aqui, ou não deveria entrar. xD
        }
        
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
    public int getQuantidadeAcaoCliente(String cpf, String siglaAcao) throws ClienteNaoEncontradoException, CarteiraVaziaException, AcaoInexistenteException, AcaoNaoEncontradaException {
        
        Cliente busca = new Cliente(cpf);
        Cliente qnt = null;
        try{
            qnt = (Cliente)clientes.buscar(busca);
        }
        catch(DadoNaoEncontradoException a){
            throw new ClienteNaoEncontradoException();
        }
        Carteira carteira = qnt.getCarteira();
        if(carteira.getAcoes().estaVazia()){
            throw new CarteiraVaziaException();
        }
        
        Pares acoes_cliente = carteira.getAcoes();
        Acao acao = new Acao(siglaAcao);
        if(acoes.contains(acao)){
            Iterator buscar = acoes.iterator();
            while(buscar.hasNext()){
                Acao encontrada = (Acao)buscar.next();
                if(encontrada.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao = encontrada;
                }
            }
        }
        else{
            throw new AcaoInexistenteException();
        }
        int buscada;
        try{
            buscada = (int)acoes_cliente.get(acao);
        }
        catch(DadoNaoEncontradoException a){
            throw new AcaoNaoEncontradaException();
        }
        return buscada;
  
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
    public void setQuantidadeAcaoCliente(String cpf, String siglaAcao, int quantidade) throws ClienteNaoEncontradoException, DadoDuplicadoException, AcaoInexistenteException, AcaoNaoEncontradaException, CarteiraVaziaException{
        Cliente recebe = new Cliente(cpf);
        Cliente busca = null;
        
        try{
            busca = (Cliente)clientes.buscar(recebe);
        }
        catch(DadoNaoEncontradoException a){
           throw new ClienteNaoEncontradoException();
        }
       
        Carteira carteira = busca.getCarteira();
       
        Pares acoes_cliente = carteira.getAcoes();
       
        Acao acao = new Acao(siglaAcao);
        if(acoes.contains(acao)){
            Iterator buscar = acoes.iterator();
            while(buscar.hasNext()){
                Acao encontrada = (Acao)buscar.next();
                if(encontrada.getSigla().equalsIgnoreCase(siglaAcao)){
                    acao = encontrada;
                }
            }
        }
        else{
            throw new AcaoInexistenteException();
        }
            
        int qnt_acoes;
        try{
            qnt_acoes = (int)acoes_cliente.get(acao);
        }
        catch(DadoNaoEncontradoException a){
            throw new AcaoNaoEncontradaException();
        }
        
        qnt_acoes += quantidade;
        try{
            acoes_cliente.remove(acao);
        }
        catch(DadoNaoEncontradoException a){
            //Nunca entrará aqui, ou não deveria entrar. xD
        }
        
        acoes_cliente.put(acao, qnt_acoes);
    }
    /**
     * Método que retorna um Iterador para os K Clientes com maiores contas no Sistema.
     * 
     * @param k Quantidade de Clientes que serão retornados pelo Iterador.
     * 
     * @return Um Iterador para a quantidade K de clientes.
     * 
     * @throws ClienteNaoEncontradoException Caso o número de Clientes passado como parâmetro
     * seja maior que a quantidade de clientes
     */
    public Iterator melhoresClientes(int k) throws ClienteNaoEncontradoException {
        if(k <= clientes.size()){
            PriorityQueue top_K = new PriorityQueue();

            Iterator it_contas = contas.iterator();
            while(it_contas.hasNext()){
                IPar par = (IPar) it_contas.next();
                Conta conta = (Conta)par.getValor();
                conta.getSaldo();
                conta.getMontante();
                top_K.add(conta);
            }
            LinkedList itera = new LinkedList();
            for(int i = 0; i < k; i++){
                Conta a = (Conta)top_K.peek();
                itera.add(top_K.poll());
            }
            return itera.iterator();
        }
        throw new ClienteNaoEncontradoException();
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
        Cliente buscado = new Cliente(cpf);
        Cliente cliente = null;
        
        try{
            cliente = (Cliente)clientes.buscar(buscado);
        }
        catch(DadoNaoEncontradoException e){
            throw new ClienteNaoEncontradoException();
        }
        
        Carteira carteira = cliente.getCarteira();
        
        if(carteira.getAcoes().estaVazia()){
            return  0.0;
        }
        double total = 0.0;
        Iterator it = carteira.getAcoes().iterator();
        while(it.hasNext()){
            IPar par = (IPar)it.next();
            Acao acao = (Acao)par.getChave();
            total += (acao.getValor() * (int)par.getValor());
        }
        return total;
    }
    /**
     * Método que retorna um iterador o qual lista os Clientes Ordenadas pelo CPF.
     * 
     * @return um Iterator para os Clientes.
     * 
     * @throws ClienteNaoEncontradoException Quando Não houver clientes cadastrados no Sistema.
     */
    public Iterator listarPessoas() throws ClienteNaoEncontradoException {
        if(clientes.isEmpty()){
            throw new ClienteNaoEncontradoException();
        }
        return clientes.iterator();
    }
    /**
     * Método que exclui as ações que não estão em nenhuma carteira.
     * 
     * @throws AcaoInexistenteException Quando não houver ações cadastradas no sistema.
     * @throws ClienteNaoEncontradoException Quando não houver clientes cadastrados no Sistema.
     */
    public void excluirAcoesOciosas() throws AcaoInexistenteException, ClienteNaoEncontradoException{
        if(acoes.isEmpty()){
            throw new AcaoInexistenteException();
        }
        PriorityQueue position = new PriorityQueue();
        
        if(clientes.isEmpty()){
            throw new ClienteNaoEncontradoException();
        }
        Iterator it = clientes.iterator();
        while(it.hasNext()){
            Cliente cliente = (Cliente)it.next();
            Carteira carteira = cliente.getCarteira();
            Pares pares = carteira.getAcoes();
            Iterator actions = pares.iterator();
            while(actions.hasNext()){
                IPar par = (IPar)actions.next();
                Acao acao = (Acao)par.getChave();
                if(!position.contains(acao)){
                    position.add(acao);
                }
            }
        }
        LinkedList<Acao>nova = new LinkedList();
        Iterator mover = position.iterator();
        while(mover.hasNext()){
            Acao acao = (Acao)position.poll();
            nova.add(acao);
        }
        acoes = nova; 
    }
    /**
     * Método que Retorna o conjunto de Clientes e Contas.
     * 
     * @return O conjunto de Clientes e Contas.
     */
    public Pares listarContas(){
        if(contas.estaVazia()){
            return null;
        }
        return contas;
    }
    /**
     * Método que atualiza as carteiras do Clientes do sistema, após o carregamento do arquivo de dados.
     */
    public void atualizarCarteiras(){
        if(clientes.isEmpty()){
            return;
        }
        
        Iterator it = clientes.iterator();
        while(it.hasNext()){
            Cliente cliente = (Cliente)it.next();
            Carteira carteira = cliente.getCarteira();
            Pares pares = carteira.getAcoes();
            Iterator pares_it = pares.iterator();
            while(pares_it.hasNext()){
                IPar par = (IPar) pares_it.next();
                Acao acao = (Acao)par.getChave();
                if(acoes.contains(acao)){
                    Iterator iterar = acoes.iterator();
                    while(iterar.hasNext()){
                        Acao mudar = (Acao)iterar.next();
                        if(mudar.getSigla().equalsIgnoreCase(acao.getSigla())){
                            acao.setValor(mudar.getValor());
                        }
                    }
                }
            }
        }
    }
}
