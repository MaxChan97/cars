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
public class PatientNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>PatientNotFoundException</code> without
     * detail message.
     */
    public PatientNotFoundException() {
    }

    /**
     * Constructs an instance of <code>PatientNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PatientNotFoundException(String msg) {
        super(msg);
    }
}
