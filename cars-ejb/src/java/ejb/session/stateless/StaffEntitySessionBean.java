/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;


@Stateless
@Local(StaffEntitySessionBeanLocal.class)
@Remote(StaffEntitySessionBeanRemote.class)
public class StaffEntitySessionBean implements StaffEntitySessionBeanRemote, StaffEntitySessionBeanLocal {
    
    @PersistenceContext (unitName = "cars-ejbPU")
    private EntityManager em;

    public StaffEntitySessionBean() {
    }
    
    @Override
    public Long createStaffEntity(StaffEntity staffEntity) {
        em.persist(staffEntity);
        em.flush();
        
        return staffEntity.getStaffId();
    }
    
    @Override
    public List<StaffEntity> retrieveAllStaffEntities() {
        Query query = em.createQuery("SELECT s FROM StaffEntity s");
        
        return query.getResultList();
    }
    
    @Override
    public StaffEntity retrieveStaffEntityById(Long id) throws StaffNotFoundException {
        StaffEntity entity = em.find(StaffEntity.class, id);
        
        if (entity != null) {
            return entity;
        } else {
            throw new StaffNotFoundException("Staff ID " + id + " does not exist!");
        }
    }
    
    @Override
    public StaffEntity retrieveStaffEntityByUsername(String userName) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM StaffEntity s WHERE s.userName = :inUserName");
        query.setParameter("inUserName", userName);
        
        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff username " + userName + " does not exist");
        }
    }
    
    @Override
    public StaffEntity staffLogin(String username, String password) throws InvalidLoginException {
        try {
            StaffEntity staffEntity = retrieveStaffEntityByUsername(username);
            
            if(staffEntity.getPassword().equals(password)) {           
                return staffEntity;
            }
            else {
                throw new InvalidLoginException("Username does not exist or invalid password!");
            }
        }
        catch(StaffNotFoundException ex) {
            throw new InvalidLoginException("Username does not exist or invalid password!");
        }
    }
    
    @Override
    public void updateStaffEntity(StaffEntity staffEntity) throws StaffNotFoundException {
        StaffEntity se = retrieveStaffEntityById(staffEntity.getStaffId());
        
        se.setUserName(staffEntity.getUserName());
        se.setPassword(staffEntity.getPassword());
        se.setFirstName(staffEntity.getFirstName());
        se.setLastName(staffEntity.getLastName());
    }
    
    @Override
    public void deleteStaffEntity(Long id) throws StaffNotFoundException, SQLIntegrityConstraintViolationException {
        StaffEntity entity = retrieveStaffEntityById(id);
        em.remove(entity);
    }
}
