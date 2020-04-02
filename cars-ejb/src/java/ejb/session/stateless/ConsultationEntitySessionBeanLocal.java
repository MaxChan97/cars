/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ConsultationEntity;



/**
 *
 * @author Lenovo
 */

public interface ConsultationEntitySessionBeanLocal {
     public Long createConsultationEntity(ConsultationEntity consultationEntity);
     public ConsultationEntity retrieveConsultationEntityById(Long consultationId);
     public void updateConsultationEntity(ConsultationEntity consultation);
     public void deleteConsultation(Long id);
    
}
