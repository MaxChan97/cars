/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.ConsultationEntity;
import javax.ejb.Stateless;


/**
 *
 * @author Lenovo
 */
@Stateless
@Local(ConsultationEntitySessionBeanRemote.class)
@Remote(ConsultationEntitySessionBeanLocal.class)
public class ConsultationEntitySessionBean implements ConsultationEntitySessionBeanRemote, ConsultationEntitySessionBeanLocal {
    
    
   @PersistenceContext(unitName = "cars-ejbPU")
   private EntityManager em;
   
   public Long createConsultationEntity(Consultation consultationEntity){
        em.persist(consultationEntity);
        em.flush();
        return consultationEntity.getAppointmentId();
    }
    
    public ConsultationEntity retrieveConsultationEntityById(Long consultationId){
        ConsultationEntity consultation = em.find(ConsultationEntity.class, consultationId);
        return consultation;
    }
    
    public void updateConsultationEntity(ConsultationEntity consultation){
        //merge unmanaged state from client side to database
        
        em.merge(consultation);
    }
    
    //delete by id, passing in an entity from the client will be unmanaged
    public void deleteAppointmentEntity(Long id){
        AppointmentEntity appointment = retrieveAppointmentEntityById(id);
        em.remove(appointment);
    }
    
    
    // One to One relationship, mandatory hence we need to have one 
    public Long createConsultationEntity(ConsultationEntity consultationEntity, Long recordId){
        em.persist(consultationEntity);
        AppointmentEntity appointment = em.find(AppointmentEntity.class,recordId);
        consultationEntity.setAppointmentEntity(appointment);
        em.flush();
        return consultationEntity.getId();
        
    }

}
