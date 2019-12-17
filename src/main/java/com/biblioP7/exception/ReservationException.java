package com.biblioP7.exception;

public class ReservationException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    public ReservationException(String pMessage) {
        super(pMessage);
    }

    public ReservationException(Throwable pCause) {
        super(pCause);
    }

    public ReservationException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }

}
