/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
   
   @Override
   public String createPatientEntity(PatientEntity patientEntity) {
       em.persist(patientEntity);
       em.flush();
       return patientEntity.getIdentityNum();
   }
   
   @Override
   public PatientEntity retrievePatientEntityById(Long id) {
       PatientEntity entity = em.find(PatientEntity.class, id);
       return entity;
   }
   @Override
   public void updatePatientEntity (PatientEntity patientEntity) {
       em.merge(patientEntity);
   }
   
   @Override
   public void deletePatientEntity (Long id) {
       PatientEntity entity = retrievePatientEntityById(id);
       em.remove(entity);
   }
}
