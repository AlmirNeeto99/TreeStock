package br.uefs.ecomp.treeStock.Exception;


public class AcaoInexistenteException extends Exception {
    public AcaoInexistenteException() {
        super();
    }

    public AcaoInexistenteException(String msg) {
        super(msg);
    }

    public AcaoInexistenteException(Throwable exception) {
        super(exception);
    }
}
