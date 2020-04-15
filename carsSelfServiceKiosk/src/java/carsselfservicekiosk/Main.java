/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsselfservicekiosk;

import ejb.session.stateless.AppointmentEntitySessionBeanRemote;
import ejb.session.stateless.ConsultationEntitySessionBeanRemote;
import ejb.session.stateless.DoctorEntitySessionBeanRemote;
import ejb.session.stateless.PatientEntitySessionBeanRemote;
import ejb.session.stateless.StaffEntitySessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author Max
 */
public class Main {
    
    @EJB
    private static PatientEntitySessionBeanRemote patientEntitySessionBean;
    @EJB
    private static DoctorEntitySessionBeanRemote doctorEntitySessionBean;
    @EJB
    private static AppointmentEntitySessionBeanRemote appointmentEntitySessionBean;
    @EJB
    private static ConsultationEntitySessionBeanRemote consultationEntitySessionBean;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(patientEntitySessionBean, doctorEntitySessionBean, appointmentEntitySessionBean, consultationEntitySessionBean);
        mainApp.runApp();
    }
    
}
