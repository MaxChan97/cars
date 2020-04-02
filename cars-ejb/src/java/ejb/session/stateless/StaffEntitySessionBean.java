/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@Local(StaffEntitySessionBeanLocal.class)
@Remote(StaffEntitySessionBeanRemote.class)
public class StaffEntitySessionBean implements StaffEntitySessionBeanRemote, StaffEntitySessionBeanLocal {
    
    @PersistenceContext (unitName = "cars-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createStaffEntity(StaffEntity staffEntity) {
        em.persist(staffEntity);
        em.flush();
        return staffEntity.getStaffId();
    }
    
    @Override
    public StaffEntity retrieveStaffEntityById(Long id) {
        StaffEntity entity = em.find(StaffEntity.class, id);
        return entity;
    }
    
    @Override
    public void updateStaffEntity(StaffEntity staffEntity) {
        em.merge(staffEntity);
    }
    
    @Override
    public void deleteStaffEntity(Long id) {
        StaffEntity entity = retrieveStaffEntityById(id);
        em.remove(entity);
    }
}
