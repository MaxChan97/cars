/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;


import entity.ConsultationEntity;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


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
   
    public Long createConsultationEntity(ConsultationEntity consultationEntity) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        List<ConsultationEntity> consultationEntities = retrieveAllConsultationEntities();
        boolean newDay = true;
        for (ConsultationEntity ce : consultationEntities) {
            if (ce.getConsultationTimestamp().getDate() == currentTimestamp.getDate()
                    && ce.getConsultationTimestamp().getMonth() == currentTimestamp.getMonth()
                    && ce.getConsultationTimestamp().getYear() == currentTimestamp.getYear()) {
                newDay = false;
            }
        }
        if (newDay == true) {
            ConsultationEntity.resetQueueNumberGenerator();
        }
       
        em.persist(consultationEntity);
        em.flush();

        return consultationEntity.getConsultationId();
    }
   
   public List<ConsultationEntity> retrieveAllConsultationEntities() {
       Query query = em.createQuery("SELECT c FROM ConsultationEntity c");
        
       return query.getResultList();
   }
    
    public ConsultationEntity retrieveConsultationEntityById(Long consultationId) {
        ConsultationEntity consultation = em.find(ConsultationEntity.class, consultationId);
        return consultation;
    }
    
    public void updateConsultationEntity(ConsultationEntity consultation){
        //merge unmanaged state from client side to database
        em.merge(consultation);
    }
    
    //delete by id, passing in an entity from the client will be unmanaged

    public void deleteConsultation(Long id) {
        ConsultationEntity appointment = retrieveConsultationEntityById(id);
        em.remove(appointment);
    }
    



    public void deleteConsultationEntity(Long consultationId) {
        ConsultationEntity consultationEntity = retrieveConsultationEntityById(consultationId);
        em.remove(consultationEntity);
    }

}
