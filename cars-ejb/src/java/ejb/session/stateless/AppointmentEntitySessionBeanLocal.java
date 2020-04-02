/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;


/**
 *
 * @author Max
 */
public interface AppointmentEntitySessionBeanLocal {
     public Long createAppointmentEntity(AppointmentEntity appointmentEntity);
     public AppointmentEntity retrieveAppointmentEntityById(Long appointmentId);
     public void updateAppointmentEntity(AppointmentEntity newAppointment);
     public void deleteAppointmentEntity(Long id);
    
}
