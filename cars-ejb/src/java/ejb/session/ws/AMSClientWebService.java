/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.AppointmentEntitySessionBeanLocal;
import ejb.session.stateless.DoctorEntitySessionBeanLocal;
import ejb.session.stateless.PatientEntitySessionBeanLocal;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Max
 */
@WebService(serviceName = "AMSClientWebService")
@Stateless

public class AMSClientWebService {

    @EJB
    private AppointmentEntitySessionBeanLocal appointmentEntitySessionBean;

    @EJB
    private DoctorEntitySessionBeanLocal doctorEntitySessionBean;

    @EJB
    private PatientEntitySessionBeanLocal patientEntitySessionBean;

    @WebMethod
    public String createPatientEntity(@WebParam PatientEntity patientEntity) throws InvalidInputException {
        return patientEntitySessionBean.createPatientEntity(patientEntity);
    }

    @WebMethod
    public PatientEntity retrievePatientEntityByIdentityNumWebService(@WebParam String id) throws PatientNotFoundException {
        PatientEntity pe = patientEntitySessionBean.retrievePatientEntityByIdentityNumWebService(id);

        for (AppointmentEntity ae : pe.getAppointments()) {
            ae.setPatient(null);
            ae.setDoctor(null);
            ae.setConsultation(null);
        }

        return pe;
    }

    @WebMethod
    public PatientEntity patientLoginWebService(@WebParam String identityNum, @WebParam String password) throws InvalidLoginException {
        PatientEntity pe = patientEntitySessionBean.patientLoginWebService(identityNum, password);

        for (AppointmentEntity ae : pe.getAppointments()) {
            ae.setPatient(null);
            ae.setDoctor(null);
            ae.setConsultation(null);
        }

        return pe;
    }

    @WebMethod
    public List<DoctorEntity> retrieveAllDoctorEntitiesWebService() {
        List<DoctorEntity> doctorEntities = doctorEntitySessionBean.retrieveAllDoctorEntitiesWebService();

        for (DoctorEntity de : doctorEntities) {
            for (AppointmentEntity ae : de.getAppointments()) {
                ae.setPatient(null);
                ae.setDoctor(null);
                ae.setConsultation(null);
            }
        }

        return doctorEntities;
    }

    @WebMethod
    public DoctorEntity retrieveDoctorEntityByIdWebService(@WebParam Long id) throws DoctorNotFoundException {
        DoctorEntity de = doctorEntitySessionBean.retrieveDoctorEntityByIdWebService(id);

        for (AppointmentEntity ae : de.getAppointments()) {
            ae.setPatient(null);
            ae.setDoctor(null);
            ae.setConsultation(null);
        }

        return de;
    }

    @WebMethod
    public Long createAppointmentEntity(@WebParam String patientIdentityNum, @WebParam Long doctorId, @WebParam Integer year, @WebParam Integer month, @WebParam Integer date, @WebParam Integer hour, @WebParam Integer min) throws PatientNotFoundException, DoctorNotFoundException, InvalidInputException {
        AppointmentEntity ae = new AppointmentEntity(new Timestamp(year-1900,month-1,date,hour,min,0,0));
        return appointmentEntitySessionBean.createAppointmentEntity(patientIdentityNum, doctorId, ae);
    }

    @WebMethod
    public AppointmentEntity retrieveAppointmentEntityByIdWebService(@WebParam Long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity ae = appointmentEntitySessionBean.retrieveAppointmentEntityByIdWebService(appointmentId);

        ae.getPatient().setAppointments(null);
        ae.getDoctor().setAppointments(null);
        if (ae.getConsultation() != null) {
            ae.getConsultation().setAppointment(null);
        }

        return ae;
    }

    @WebMethod
    public void deleteAppointmentEntity(@WebParam Long id) throws AppointmentNotFoundException, SQLIntegrityConstraintViolationException {
        appointmentEntitySessionBean.deleteAppointmentEntity(id);
    }

    @WebMethod
    public boolean after(@WebParam Integer year1, @WebParam Integer month1, @WebParam Integer date1, @WebParam Integer hour1, @WebParam Integer min1, @WebParam Integer second1, @WebParam Integer nano1, @WebParam Integer year2, @WebParam Integer month2, @WebParam Integer date2, @WebParam Integer hour2, @WebParam Integer min2, @WebParam Integer second2, @WebParam Integer nano2) {
        Timestamp ts1 = new Timestamp(year1,month1,date1,hour1,min1,second1,nano1);
        if (ts1.after(new Timestamp(year2,month2,date2,hour2,min2,second2,nano2))) {
            return true;
        } else {
            return false;
        }
    }

}
