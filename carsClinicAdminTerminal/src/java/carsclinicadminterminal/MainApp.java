/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsclinicadminterminal;

import ejb.session.stateless.AppointmentEntitySessionBeanRemote;
import ejb.session.stateless.ConsultationEntitySessionBeanRemote;
import ejb.session.stateless.DoctorEntitySessionBeanRemote;
import ejb.session.stateless.PatientEntitySessionBeanRemote;
import ejb.session.stateless.StaffEntitySessionBeanRemote;
import entity.StaffEntity;
import java.util.Scanner;
import util.exception.InvalidLoginException;

/**
 *
 * @author Max
 */
public class MainApp {

    private PatientEntitySessionBeanRemote patientEntitySessionBean;
    private DoctorEntitySessionBeanRemote doctorEntitySessionBean;
    private AppointmentEntitySessionBeanRemote appointmentEntitySessionBean;
    private ConsultationEntitySessionBeanRemote consultationEntitySessionBean;
    private StaffEntitySessionBeanRemote staffEntitySessionBean;
    
    private RegistrationOperationModule registrationOperationModule;
    
    private StaffEntity currentStaffEntity;

    public MainApp() {
    }

    public MainApp(PatientEntitySessionBeanRemote patientEntitySessionBean, DoctorEntitySessionBeanRemote doctorEntitySessionBean, AppointmentEntitySessionBeanRemote appointmentEntitySessionBean, ConsultationEntitySessionBeanRemote consultationEntitySessionBean, StaffEntitySessionBeanRemote staffEntitySessionBean) {
        this.patientEntitySessionBean = patientEntitySessionBean;
        this.doctorEntitySessionBean = doctorEntitySessionBean;
        this.appointmentEntitySessionBean = appointmentEntitySessionBean;
        this.consultationEntitySessionBean = consultationEntitySessionBean;
        this.staffEntitySessionBean = staffEntitySessionBean;
    }
    
    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Welcome to Clinic Appointment Registration System (CARS) ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if(response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        
                        registrationOperationModule = new RegistrationOperationModule(patientEntitySessionBean,doctorEntitySessionBean,appointmentEntitySessionBean,consultationEntitySessionBean);
                        
                        menuMain();
                    }
                    catch(InvalidLoginException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 2) {
                    break;
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2) {
                break;
            }
        }
    }
    
    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** CARS :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0) {
            currentStaffEntity = staffEntitySessionBean.staffLogin(username, password);      
        }
        else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }
    
    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** CARS :: Main ***\n");
            System.out.println("You are login as " + currentStaffEntity.getFullName() + "\n");
            System.out.println("1: Registration Operation");
            System.out.println("2: Appointment Operation");
            System.out.println("3: Administration Operation");
            System.out.println("4: Logout\n");
            response = 0;
            
            while(response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    registrationOperationModule.menuRegistrationOperation();
                }
                else if (response == 2) {
                    //appointmentOperationModule.menuAppointmentOperation();
                }
                else if (response == 3) {
                    //administrationOperationModule.menuAdministrationOperation();
                }
                else if (response == 4) {
                    break;
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2) {
                break;
            }
        }
    }
}
