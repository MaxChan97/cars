/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
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
@Local(DoctorEntitySessionBeanLocal.class)
@Remote(DoctorEntitySessionBeanRemote.class)
public class DoctorEntitySessionBean implements DoctorEntitySessionBeanRemote, DoctorEntitySessionBeanLocal {

  @PersistenceContext(unitName = "cars-ejbPU")
  private EntityManager em;
  
  
  @Override
  public long createDoctorEntity(DoctorEntity doctorEntity)
  {
      em.persist(doctorEntity);
      em.flush();
      return doctorEntity.getDoctorId();
  }
  
  @Override
  public DoctorEntity retrieveDoctorEntityById(Long id){
      DoctorEntity entity = em.find(DoctorEntity.class, id);
      return entity;
  }
  
  @Override
  public void updateDoctorEntity(DoctorEntity doctor){
      em.merge(doctor);
  }
  
  @Override
  public void deleteDoctorEntity(Long id)
  {
      DoctorEntity toDelete = retrieveDoctorEntityById(id);
      em.remove(toDelete);
      
  }
  
  
  
  
    
   
}
