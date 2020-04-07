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
public class DoctorNotAvailableForWalkInException extends Exception {

    /**
     * Creates a new instance of
     * <code>DoctorNotAvailableForWalkInException</code> without detail message.
     */
    public DoctorNotAvailableForWalkInException() {
    }

    /**
     * Constructs an instance of
     * <code>DoctorNotAvailableForWalkInException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public DoctorNotAvailableForWalkInException(String msg) {
        super(msg);
    }
}
