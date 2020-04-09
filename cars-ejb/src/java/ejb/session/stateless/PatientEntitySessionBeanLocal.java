/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

public interface PatientEntitySessionBeanLocal {

    public String createPatientEntity(PatientEntity patientEntity);

    public List<PatientEntity> retrieveAllPatientEntities();

    public PatientEntity retrievePatientEntityByIdentityNum(String id) throws PatientNotFoundException;

    public PatientEntity patientLogin(String identityNum, String password) throws InvalidLoginException;

    public void updatePatientEntity(PatientEntity patientEntity) throws PatientNotFoundException, InvalidInputException;

    public void deletePatientEntity(String id) throws PatientNotFoundException, SQLIntegrityConstraintViolationException;
}
