/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author Max
 */
public class ConsultationNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ConsultationNotFoundException</code>
     * without detail message.
     */
    public ConsultationNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ConsultationNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConsultationNotFoundException(String msg) {
        super(msg);
    }
}
