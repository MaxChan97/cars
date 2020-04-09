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
public class NoAvailableDoctorsException extends Exception {

    /**
     * Creates a new instance of <code>NoAvailableDoctorsException</code>
     * without detail message.
     */
    public NoAvailableDoctorsException() {
    }

    /**
     * Constructs an instance of <code>NoAvailableDoctorsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAvailableDoctorsException(String msg) {
        super(msg);
    }
}
