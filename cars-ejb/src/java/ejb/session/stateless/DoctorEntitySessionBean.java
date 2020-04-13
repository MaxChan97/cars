/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.DoctorEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.DoctorNotFoundException;

/**
 *
 * @author Lenovo
 */
@Stateless
@Local(DoctorEntitySessionBeanLocal.class)
@Remote(DoctorEntitySessionBeanRemote.class)
public class DoctorEntitySessionBean implements DoctorEntitySessionBeanRemote, DoctorEntitySessionBeanLocal {

    @PersistenceContext(unitName = "cars-ejbPU")
    private EntityManager em;

    public DoctorEntitySessionBean() {
    }

    @Override
    public long createDoctorEntity(DoctorEntity doctorEntity) {
        em.persist(doctorEntity);
        em.flush();

        return doctorEntity.getDoctorId();
    }
    
    @Override
    public List<DoctorEntity> retrieveAllDoctorEntities() {
        Query query = em.createQuery("SELECT d FROM DoctorEntity d");
        
        return query.getResultList();
    }

    @Override
    public DoctorEntity retrieveDoctorEntityById(Long id) throws DoctorNotFoundException {
        DoctorEntity entity = em.find(DoctorEntity.class, id);
        
        if (entity != null) {
            return entity;
        } else {
            throw new DoctorNotFoundException("Doctor ID " + id + " does not exist!");
        }
    }

    @Override
    public void updateDoctorEntity(DoctorEntity doctorEntity) throws DoctorNotFoundException {
        DoctorEntity de = retrieveDoctorEntityById(doctorEntity.getDoctorId());
        
        de.setFirstName(doctorEntity.getFirstName());
        de.setLastName(doctorEntity.getLastName());
        de.setAppointments(doctorEntity.getAppointments());
        de.setLeaves(doctorEntity.getLeaves());
        de.setNotAvail(doctorEntity.getNotAvail());
        de.setQualification(doctorEntity.getQualification());
        de.setRegistration(doctorEntity.getRegistration());
        de.setDatesAppliedForLeaves(doctorEntity.getDatesAppliedForLeaves());
    }

    @Override
    public void deleteDoctorEntity(Long id) throws DoctorNotFoundException, SQLIntegrityConstraintViolationException {
        DoctorEntity toDelete = retrieveDoctorEntityById(id);
        
        List<AppointmentEntity> appointmentEntities = toDelete.getAppointments();
        for (AppointmentEntity ae : appointmentEntities) {
            ae.setDoctor(null);
            em.remove(ae);
        }
        em.remove(toDelete);
    }
}
