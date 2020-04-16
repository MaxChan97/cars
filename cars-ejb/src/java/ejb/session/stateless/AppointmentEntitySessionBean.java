/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AppointmentEntity;
import entity.ConsultationEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.EJB;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidInputException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Max
 */
@Stateless
@Local(AppointmentEntitySessionBeanLocal.class)
@Remote(AppointmentEntitySessionBeanRemote.class)
public class AppointmentEntitySessionBean implements AppointmentEntitySessionBeanRemote, AppointmentEntitySessionBeanLocal {

    @PersistenceContext(unitName = "cars-ejbPU")
    private EntityManager em;

    @EJB
    private PatientEntitySessionBeanLocal patientEntitySessionBean;
    @EJB
    private DoctorEntitySessionBeanLocal doctorEntitySessionBean;

    public AppointmentEntitySessionBean() {
    }

    @Override
    public Long createAppointmentEntity(String patientIdentityNum, Long doctorId, AppointmentEntity appointmentEntity) throws PatientNotFoundException, DoctorNotFoundException, InvalidInputException {
        PatientEntity patientEntity = patientEntitySessionBean.retrievePatientEntityByIdentityNum(patientIdentityNum);
        appointmentEntity.setPatient(patientEntity);
        DoctorEntity doctorEntity = doctorEntitySessionBean.retrieveDoctorEntityById(doctorId);
        appointmentEntity.setDoctor(doctorEntity);

        patientEntity.getAppointments().add(appointmentEntity);
        doctorEntity.getAppointments().add(appointmentEntity);
        doctorEntity.getNotAvail().add(appointmentEntity.getAppointmentTimestamp());

        try {
            em.persist(appointmentEntity);
            em.flush();
        } catch (javax.ejb.EJBException ex) {
            throw new InvalidInputException("You already have an existing appointment with another doctor at this time and date!\nUnable to book appointment!");
        }

        return appointmentEntity.getAppointmentId();
    }

    @Override
    public List<AppointmentEntity> retrieveAllAppointmentEntities() {
        Query query = em.createQuery("SELECT a FROM AppointmentEntity a");

        return query.getResultList();
    }

    @Override
    public AppointmentEntity retrieveAppointmentEntityById(Long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity appointment = em.find(AppointmentEntity.class, appointmentId);

        if (appointment != null) {
            return appointment;
        } else {
            throw new AppointmentNotFoundException("Appointment ID " + appointmentId + " does not exist!");
        }
    }

    @Override
    public AppointmentEntity retrieveAppointmentEntityByIdWebService(Long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity appointment = em.find(AppointmentEntity.class, appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment ID " + appointmentId + " does not exist!");
        }

        em.detach(appointment);
        em.detach(appointment.getPatient());
        em.detach(appointment.getDoctor());
        if (appointment.getConsultation() != null) {
            em.detach(appointment.getConsultation());
        }

        if (appointment != null) {
            return appointment;
        } else {
            throw new AppointmentNotFoundException("Appointment ID " + appointmentId + " does not exist!");
        }
    }

    @Override
    public void updateAppointmentEntity(AppointmentEntity newAppointment) throws AppointmentNotFoundException {
        AppointmentEntity ae = retrieveAppointmentEntityById(newAppointment.getAppointmentId());

        ae.setAppointmentTimestamp(newAppointment.getAppointmentTimestamp());
    }

    //delete by id, passing in an entity from the client will be unmanaged
    @Override
    public void deleteAppointmentEntity(Long id) throws AppointmentNotFoundException, SQLIntegrityConstraintViolationException {
        AppointmentEntity appointment = retrieveAppointmentEntityById(id);
        ConsultationEntity ce = appointment.getConsultation();
        if (ce != null) {
            appointment.setConsultation(null);
            em.remove(ce);
        }
        PatientEntity pe = appointment.getPatient();
        pe.getAppointments().remove(appointment);
        DoctorEntity de = appointment.getDoctor();
        de.getAppointments().remove(appointment);
        de.getNotAvail().remove(appointment.getAppointmentTimestamp());
        em.remove(appointment);
    }
}
