/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.ConsultationEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.AppointmentNotFoundException;
import util.exception.ConsultationNotFoundException;

/**
 *
 * @author Lenovo
 */
@Remote
public interface ConsultationEntitySessionBeanRemote {

    public Long createConsultationEntity(Long appointmentId, Integer duration) throws AppointmentNotFoundException;

    public List<ConsultationEntity> retrieveAllConsultationEntities();

    public ConsultationEntity retrieveConsultationEntityById(Long consultationId) throws ConsultationNotFoundException;

    public void updateConsultationEntity(ConsultationEntity consultation) throws ConsultationNotFoundException;

    public void deleteConsultationEntity(Long consultationId) throws ConsultationNotFoundException;
}
