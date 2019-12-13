package com.biblioP7.exception;

public class FunctionalException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public FunctionalException(String pMessage) {
        super(pMessage);
    }

    public FunctionalException(Throwable pCause) {
        super(pCause);
    }

    public FunctionalException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }

}
