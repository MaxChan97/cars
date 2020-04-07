/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import util.exception.InvalidLoginException;
import util.exception.StaffNotFoundException;

public interface StaffEntitySessionBeanRemote {

    public Long createStaffEntity(StaffEntity staffEntity);

    public List<StaffEntity> retrieveAllStaffEntities();

    public StaffEntity retrieveStaffEntityById(Long id) throws StaffNotFoundException;

    public StaffEntity retrieveStaffEntityByUsername(String userName) throws StaffNotFoundException;

    public StaffEntity staffLogin(String username, String password) throws InvalidLoginException;

    public void updateStaffEntity(StaffEntity staffEntity) throws StaffNotFoundException;

    public void deleteStaffEntity(Long id) throws StaffNotFoundException, SQLIntegrityConstraintViolationException;

}
