/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.ws;

import ejb.session.stateless.AppointmentEntitySessionBeanLocal;
import entity.AppointmentEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.AppointmentNotFoundException;
import util.exception.DoctorNotFoundException;
import util.exception.PatientNotFoundException;

/**
 *
 * @author Max
 */
@WebService(serviceName = "AppointmentEntityWebService")
@Stateless()
public class AppointmentEntityWebService {

    @EJB
    private AppointmentEntitySessionBeanLocal appointmentEntitySessionBean;

    @WebMethod
    public Long createAppointmentEntity(@WebParam String patientIdentityNum, @WebParam Long doctorId, @WebParam AppointmentEntity appointmentEntity) throws PatientNotFoundException, DoctorNotFoundException {
        return appointmentEntitySessionBean.createAppointmentEntity(patientIdentityNum, doctorId, appointmentEntity);
    }
    
    @WebMethod
    public List<AppointmentEntity> retrieveAllAppointmentEntities() {
        return appointmentEntitySessionBean.retrieveAllAppointmentEntities();
    }
    
    @WebMethod
    public AppointmentEntity retrieveAppointmentEntityById(@WebParam Long appointmentId) throws AppointmentNotFoundException {
        return appointmentEntitySessionBean.retrieveAppointmentEntityById(appointmentId);
    }
}
