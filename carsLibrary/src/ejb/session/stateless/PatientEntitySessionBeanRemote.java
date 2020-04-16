/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.PatientEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AppointmentNotFoundException;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Lenovo
 */
@Remote
public interface PatientEntitySessionBeanRemote {

    public String createPatientEntity(PatientEntity patientEntity) throws InvalidInputException;

    public List<PatientEntity> retrieveAllPatientEntities();

    public PatientEntity retrievePatientEntityByIdentityNum(String id) throws PatientNotFoundException;
    
    public PatientEntity retrievePatientEntityByIdentityNumWebService(String id) throws PatientNotFoundException;

    public PatientEntity patientLogin(String identityNum, String password) throws InvalidLoginException;
    
    public PatientEntity patientLoginWebService(String identityNum, String password) throws InvalidLoginException;

    public void updatePatientEntity(PatientEntity patientEntity) throws PatientNotFoundException, InvalidInputException;
    
    public List<AppointmentEntity> viewAppointmentmentByPatientId(String patientId) throws PatientNotFoundException;
    
    public void cancelAppointment(Long appointmentIdToDelete,String patientId) throws PatientNotFoundException;

    public void deletePatientEntity(String id) throws PatientNotFoundException, SQLIntegrityConstraintViolationException, AppointmentNotFoundException;
}
