package br.uefs.ecomp.treeStock.Exception;

public class ClienteNaoEncontradoException extends Exception {

    public ClienteNaoEncontradoException() {
        super();
    }

    public ClienteNaoEncontradoException(String msg) {
        super(msg);
    }

    public ClienteNaoEncontradoException(Throwable exception) {
        super(exception);
    }
}
