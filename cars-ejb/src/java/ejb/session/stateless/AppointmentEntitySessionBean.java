/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.ConsultationEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Max
 */
@Stateless
@Local(AppointmentEntitySessionBeanLocal.class)
@Remote(AppointmentEntitySessionBeanRemote.class)
public class AppointmentEntitySessionBean implements AppointmentEntitySessionBeanRemote, AppointmentEntitySessionBeanLocal {

    @PersistenceContext(unitName = "cars-ejbPU")
    private EntityManager em;
    
    public Long createAppointmentEntity(AppointmentEntity appointmentEntity) {
        em.persist(appointmentEntity);
        em.flush();
        return appointmentEntity.getAppointmentId();
    }
    
    public AppointmentEntity retrieveAppointmentEntityById(Long appointmentId) {
        AppointmentEntity appointment = em.find(AppointmentEntity.class, appointmentId);
        return appointment;
    }
    
    public void updateAppointmentEntity(AppointmentEntity newAppointment) {
        //merge unmanaged state from client side to database
        em.merge(newAppointment);
    }
    
    //delete by id, passing in an entity from the client will be unmanaged
    public void deleteAppointmentEntity(Long id) {
        AppointmentEntity appointment = retrieveAppointmentEntityById(id);
        em.remove(appointment);
    }
}
