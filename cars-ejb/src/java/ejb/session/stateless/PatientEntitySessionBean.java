/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.PatientEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Lenovo
 */
@Stateless
@Local(PatientEntitySessionBeanLocal.class)
@Remote(PatientEntitySessionBeanRemote.class)
public class PatientEntitySessionBean implements PatientEntitySessionBeanRemote, PatientEntitySessionBeanLocal {

   @PersistenceContext(unitName = "cars-ejbPU")
    private EntityManager em;

    public PatientEntitySessionBean() {
    }
   
    @Override
    public String createPatientEntity(PatientEntity patientEntity) {
        em.persist(patientEntity);
        em.flush();
       
        return patientEntity.getIdentityNum();
    }
    
    @Override
    public List<PatientEntity> retrieveAllPatientEntities() {
        Query query = em.createQuery("SELECT p FROM PatientEntity p");
        
        return query.getResultList();
    }
   
    @Override
    public PatientEntity retrievePatientEntityByIdentityNum(String id) throws PatientNotFoundException {
        PatientEntity entity = em.find(PatientEntity.class, id);
        
        if (entity != null) {
            return entity;
        } else {
            throw new PatientNotFoundException("Patient ID " + id + " does not exist!");
        }
    }
    
    public PatientEntity patientLogin(String identityNum, String password) throws InvalidLoginException {
        try {
            PatientEntity patientEntity = retrievePatientEntityByIdentityNum(identityNum);
            
            if(patientEntity.getPassword().equals(password)) {           
                return patientEntity;
            }
            else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        }
        catch(PatientNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public void updatePatientEntity(PatientEntity patientEntity) throws PatientNotFoundException, InvalidInputException {
        PatientEntity pe = retrievePatientEntityByIdentityNum(patientEntity.getIdentityNum());
        
        pe.setAddress(patientEntity.getAddress());
        pe.setAge(patientEntity.getAge());
        pe.setAppointments(patientEntity.getAppointments());
        pe.setFirstName(patientEntity.getFirstName());
        pe.setLastName(patientEntity.getLastName());
        pe.setGender(patientEntity.getGender());
        pe.setIdentityNum(patientEntity.getIdentityNum());
        pe.setPassword(patientEntity.getPassword());
        pe.setPhoneNumber(patientEntity.getPhoneNumber());
    }
   
    @Override
    public void deletePatientEntity(String id) throws PatientNotFoundException, SQLIntegrityConstraintViolationException {
        PatientEntity entity = retrievePatientEntityByIdentityNum(id);
        
        List<AppointmentEntity> appointmentEntities = entity.getAppointments();
        for (AppointmentEntity ae : appointmentEntities) {
            ae.setPatient(null);
        }
        
        em.remove(entity);
    }
    
    public List<AppointmentEntity> viewAppointmentmentByPatientId(String patientId) throws PatientNotFoundException{
        PatientEntity toView =  retrievePatientEntityByIdentityNum(patientId);
        return toView.getAppointments();   
    }
    
    public void addAppointment(Long doctorId, Date appointmentDate)throws DoctorNotFoundException{
        
        
        
        
        
    }
    
    public void cancelAppointment(Long appointmentIdToDelete,String patientId) throws PatientNotFoundException{
       
        PatientEntity patient = retrievePatientEntityByIdentityNum(patientId);
        List<AppointmentEntity> appointments = patient.getAppointments();
        for(AppointmentEntity appointment : appointments){
            if(appointment.getAppointmentId()== appointmentIdToDelete){
                appointments.remove(appointment);
                
            }
        }
        
    }
}
