/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;




public interface DoctorEntitySessionBeanLocal {
    public long createDoctorEntity(DoctorEntity doctorEntity);
    public DoctorEntity retrieveDoctorEntityById(Long id);
    public void updateDoctorEntity(DoctorEntity doctor);
    public void deleteDoctorEntity(Long id);
    
}
