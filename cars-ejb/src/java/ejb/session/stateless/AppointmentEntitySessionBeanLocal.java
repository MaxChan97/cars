/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidInputException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Max
 */
public interface AppointmentEntitySessionBeanLocal {

    public Long createAppointmentEntity(String patientIdentityNum, Long doctorId, AppointmentEntity appointmentEntity) throws PatientNotFoundException, DoctorNotFoundException, InvalidInputException;

    public List<AppointmentEntity> retrieveAllAppointmentEntities();

    public AppointmentEntity retrieveAppointmentEntityById(Long appointmentId) throws AppointmentNotFoundException;
    
    public AppointmentEntity retrieveAppointmentEntityByIdWebService(Long appointmentId) throws AppointmentNotFoundException;

    public void updateAppointmentEntity(AppointmentEntity newAppointment) throws AppointmentNotFoundException;

    public void deleteAppointmentEntity(Long id) throws AppointmentNotFoundException, SQLIntegrityConstraintViolationException;

}
