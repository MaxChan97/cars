/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.ConsultationEntity;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AppointmentNotFoundException;
import util.exception.ConsultationNotFoundException;

/**
 *
 * @author Lenovo
 */
@Stateless
@Local(ConsultationEntitySessionBeanLocal.class)
@Remote(ConsultationEntitySessionBeanRemote.class)

public class ConsultationEntitySessionBean implements ConsultationEntitySessionBeanRemote, ConsultationEntitySessionBeanLocal {

    @PersistenceContext(unitName = "cars-ejbPU")
    private EntityManager em;
    
    @EJB
    AppointmentEntitySessionBeanLocal appointmentEntitySessionBean;

    public ConsultationEntitySessionBean() {
    }

    public Long createConsultationEntity(Long appointmentId, Integer duration) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity = appointmentEntitySessionBean.retrieveAppointmentEntityById(appointmentId);
        
        Timestamp currentTimestamp = new Timestamp(2020-1900, 4-1, 15, 16, 15, 0, 0);
        List<ConsultationEntity> consultationEntities = retrieveAllConsultationEntities();
        boolean newDay = true;
        for (ConsultationEntity ce : consultationEntities) {
            if (ce.getAppointment().getAppointmentTimestamp().getDate() == currentTimestamp.getDate()
                    && ce.getAppointment().getAppointmentTimestamp().getMonth() == currentTimestamp.getMonth()
                    && ce.getAppointment().getAppointmentTimestamp().getYear() == currentTimestamp.getYear()) {
                newDay = false;
            }
        }
        if (newDay == true) {
            ConsultationEntity.resetQueueNumberGenerator();
        }
        
        ConsultationEntity consultationEntity = new ConsultationEntity(duration);
        consultationEntity.setAppointment(appointmentEntity);
        appointmentEntity.setConsultation(consultationEntity);

        em.persist(consultationEntity);
        em.flush();

        return consultationEntity.getConsultationId();
    }

    public List<ConsultationEntity> retrieveAllConsultationEntities() {
        Query query = em.createQuery("SELECT c FROM ConsultationEntity c");

        return query.getResultList();
    }

    public ConsultationEntity retrieveConsultationEntityById(Long consultationId) throws ConsultationNotFoundException {
        ConsultationEntity consultation = em.find(ConsultationEntity.class, consultationId);
        
        if (consultation != null) {
            return consultation;
        } else {
            throw new ConsultationNotFoundException("Consultation ID " + consultationId + " does not exist!");
        }
    }

    public void updateConsultationEntity(ConsultationEntity consultation) throws ConsultationNotFoundException {
        ConsultationEntity ce = retrieveConsultationEntityById(consultation.getConsultationId());
        
        ce.setAppointment(consultation.getAppointment());
        ce.setConsultationId(consultation.getConsultationId());
        ce.setDuration(consultation.getDuration());
        ce.setQueueNumber(consultation.getDuration());
    }

    public void deleteConsultationEntity(Long consultationId) throws ConsultationNotFoundException {
        ConsultationEntity consultationEntity = retrieveConsultationEntityById(consultationId);
        em.remove(consultationEntity);
    }

}
