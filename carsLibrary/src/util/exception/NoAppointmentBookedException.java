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
public class NoAppointmentBookedException extends Exception {

    /**
     * Creates a new instance of <code>NoAppointmentBookedException</code>
     * without detail message.
     */
    public NoAppointmentBookedException() {
    }

    /**
     * Constructs an instance of <code>NoAppointmentBookedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoAppointmentBookedException(String msg) {
        super(msg);
    }
}
