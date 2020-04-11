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
import entity.AppointmentEntity;
import entity.PatientEntity;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Lenovo
 */
public class AppointmentOperationsModule {
    
    private PatientEntitySessionBeanRemote patientEntitySessionBean;
    private DoctorEntitySessionBeanRemote doctorEntitySessionBean;
    private AppointmentEntitySessionBeanRemote appointmentEntitySessionBean;

    private static final Scanner scanner = new Scanner(System.in);

    public AppointmentOperationsModule(){
        
    }

    public AppointmentOperationsModule(PatientEntitySessionBeanRemote patientEntitySessionBean, DoctorEntitySessionBeanRemote doctorEntitySessionBean, AppointmentEntitySessionBeanRemote appointmentEntitySessionBean) {
        this.patientEntitySessionBean = patientEntitySessionBean;
        this.doctorEntitySessionBean = doctorEntitySessionBean;
        this.appointmentEntitySessionBean = appointmentEntitySessionBean;
    }

    public void menuAppointmentOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Appointment Operation ***\n");
            System.out.println("1: View Patient Appointments");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    System.out.println("*** CARS :: Appointment Operation :: View Patient Appointment  ***\n");
                    scanner.nextLine();
                    System.out.print("Enter patient identity number to view appointments> ");
                    String id1 = scanner.nextLine().trim();
                    try{
                        List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(id1);
                        System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID"," | ", "Date"," | ", "Time"," | ", "Doctor");
                        for(AppointmentEntity appointment : appointments){
                            System.out.printf("%8s%2s%20d%2s%20d%2s%15s\n",appointment.getAppointmentId().toString()," | ", appointment.getAppointmentTimestamp().getDate()," | ", appointment.getAppointmentTimestamp().getHours(), " | ", appointment.getDoctor());
                
                        }
                        
                        System.out.print("Press any key to continue...> ");
                        System.out.println();
                        scanner.nextLine();

                    }catch(Exception ex){
                        System.err.println(ex.getMessage());
                        System.out.println("Could not view patient's appointment details, please key in the corret input!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 2) {
                    addAppointment();
                } else if (response == 3) {
                    System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment  ***\n");
                    scanner.nextLine();
                    System.out.print("Enter patient identity number to view appointments> ");
                    String id2 = scanner.nextLine().trim();
                  
                    try{
                        PatientEntity patient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id2);
                        List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(id2);
                        System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID"," | ", "Date"," | ", "Time"," | ", "Doctor");
                        for(AppointmentEntity appointment : appointments){
                            System.out.printf("%8s%2s%20d%2s%20d%2s%15s\n",appointment.getAppointmentId().toString()," | ", appointment.getAppointmentTimestamp().getDate()," | ", appointment.getAppointmentTimestamp().getTime(), " | ", appointment.getDoctor());
                        }
                        System.out.println("Enter Appointment Id> ");
                        Long idToDelete = scanner.nextLong();
                        AppointmentEntity appointmentToDelete = appointmentEntitySessionBean.retrieveAppointmentEntityById(idToDelete);
                        patientEntitySessionBean.cancelAppointment(idToDelete, id2);
                        //appointmentEntitySessionBean.deleteAppointmentEntity(idToDelete);
                        System.out.print(patient.getFirstName() + " " + patient.getLastName() + " appointment with " + appointmentToDelete.getDoctor() + " at " + appointmentToDelete.getAppointmentTimestamp().getHours() + " on " +appointmentToDelete.getAppointmentTimestamp().getDate() + " has been cancelled." );
                        
                        System.out.println("Press any key to continue...> ");
                        System.out.println();
                        scanner.nextLine();
                    }catch(Exception ex){
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete appointment for given id!");
                        System.out.print("Press any key to continue...> ");
                        System.out.println();
                        scanner.nextLine();
                    }
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }
    
    public void viewPatientAppointment(){
        
        
      
        
    }
    
    public void addAppointment(){}
    
    
    public void cancelAppointment(){}
    
}
