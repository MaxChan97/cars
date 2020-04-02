/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.StaffEntity;



public interface StaffEntitySessionBeanRemote {
     public Long createStaffEntity(StaffEntity staffEntity);
     public StaffEntity retrieveStaffEntityById(Long id);
     public void updateStaffEntity(StaffEntity staffEntity);
     public void deleteStaffEntity(Long id);
    
}
