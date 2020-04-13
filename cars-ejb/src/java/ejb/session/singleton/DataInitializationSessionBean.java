/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentEntitySessionBeanLocal;
import ejb.session.stateless.ConsultationEntitySessionBeanLocal;
import ejb.session.stateless.DoctorEntitySessionBeanLocal;
import ejb.session.stateless.PatientEntitySessionBeanLocal;
import ejb.session.stateless.StaffEntitySessionBeanLocal;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.exception.StaffNotFoundException;

/**
 *
 * @author Max
 */
@Singleton
@LocalBean
@Startup

public class DataInitializationSessionBean {
    @EJB
    PatientEntitySessionBeanLocal patientEntitySessionBean;
    @EJB
    DoctorEntitySessionBeanLocal doctorEntitySessionBean;
    @EJB
    AppointmentEntitySessionBeanLocal appointmentEntitySessionBean;
    @EJB
    ConsultationEntitySessionBeanLocal consultationEntitySessionBean;
    @EJB
    StaffEntitySessionBeanLocal staffEntitySessionBean;

    public DataInitializationSessionBean() {
    }
    
    @PostConstruct
    public void postConstruct() {
        try {
            staffEntitySessionBean.retrieveStaffEntityByUsername("manager");
        }
        catch(StaffNotFoundException ex) {
            initializeData();
        }
    }
    
    private void initializeData() {
        staffEntitySessionBean.createStaffEntity(new StaffEntity("Eric", "Some", "manager", "password"));
        staffEntitySessionBean.createStaffEntity(new StaffEntity("Victoria", "Newton", "nurse", "password"));
        
        doctorEntitySessionBean.createDoctorEntity(new DoctorEntity("Tan", "Ming", "S10011", "BMBS"));
        doctorEntitySessionBean.createDoctorEntity(new DoctorEntity("Clair", "Hahn", "S41221", "MBBCh"));
        doctorEntitySessionBean.createDoctorEntity(new DoctorEntity("Robert", "Blake", "S58201", "MBBS"));
        
        patientEntitySessionBean.createPatientEntity(new PatientEntity("S987027A", "123456", "Sarah", "Yi", "F", "22", "93718799", "13, Clementi Road"));
        patientEntitySessionBean.createPatientEntity(new PatientEntity("G1314207T", "123456", "Rajesh", "Singh", "M", "36", "93506839", "15, Mountbatten Road"));
    }
}
