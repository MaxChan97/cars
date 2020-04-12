/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsclinicadminterminal;

import ejb.session.stateless.AppointmentEntitySessionBeanRemote;
import ejb.session.stateless.DoctorEntitySessionBeanRemote;
import ejb.session.stateless.PatientEntitySessionBeanRemote;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
                        PatientEntity patient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id1);
                        List<AppointmentEntity> appointments = patient.getAppointments();
                        //List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(id1);
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
                    System.out.println("*** CARS :: Appointment Operation :: Add Appointment  ***\n");
                    scanner.nextLine();
                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                    List<DoctorEntity> doctorEntities = doctorEntitySessionBean.retrieveAllDoctorEntities();
                    List<DoctorEntity> availableDoctors = new ArrayList<>();
                    for (DoctorEntity de : doctorEntities) {
                        if (!de.getLeaves().contains(new Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate()))) {
                            availableDoctors.add(de);
                        }
                    }
                   System.out.println("Id |Name");
                    for (DoctorEntity de : availableDoctors) {
                        if (de.getDoctorId() >= 100) {
                            System.out.println(de.getDoctorId() + "|" + de.getFullName());
                        } else if (de.getDoctorId() >= 10) {
                            System.out.println(de.getDoctorId() + " |" + de.getFullName());
                        } else {
                            System.out.println(de.getDoctorId() + "  |" + de.getFullName());
                        }
                    }
                    System.out.println();
                    System.out.print("Enter Doctor Id> ");
                    Long id3 = scanner.nextLong();
                    try{
                    DoctorEntity doctorToAppoint = doctorEntitySessionBean.retrieveDoctorEntityById(id3);
                    System.out.print("Enter Date> ");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    String dateInput = scanner.nextLine().trim();
                    Date appointmentDate = sdf.parse(dateInput);
                    
                  
                     
      
                    int year = appointmentDate.getYear();
                    int month = appointmentDate.getMonth();
                    int date = appointmentDate.getDate();
                 
                    
                    List<Time> allTimeSlots = getAllTimeSlots();
                    List<Time> availableTimeslot = new ArrayList<>();
                    HashSet<Timestamp> notAvail = doctorToAppoint.getNotAvail();
                        for(Time timings:allTimeSlots){
                        for(int i=0; i<notAvail.size(); i++){
                            if(!notAvail.contains(new Timestamp(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate(), timings.getHours(), timings.getMinutes(), timings.getSeconds(),0))){
                                availableTimeslot.add(timings);
                            }
                        }
                    }
                    System.out.print("Availability for Tan Ming on " + (year+1900) + "-" + (month+1) +"-" + date);
                    System.out.println();
                    for(Time timings:availableTimeslot){
                        System.out.print(timings.toString().substring(0,5) +" ");
                        
                    }
                    
                    
                    System.out.println("Enter Time> ");
                    int hours = scanner.nextInt();
                    scanner.next();
                    int min = scanner.nextInt();
                    scanner.nextLine();
                    Timestamp toAppoint = new Timestamp(year,month,date,hours,min,0,0);
                    AppointmentEntity newAppointment = new AppointmentEntity(toAppoint);
                    System.out.print("Enter Patient Identity Number> ");
                    String patientId = scanner.nextLine();
                    appointmentEntitySessionBean.createAppointmentEntity(patientId, id3, newAppointment);
                    PatientEntity patientForAppoint = patientEntitySessionBean.retrievePatientEntityByIdentityNum(patientId);
                    patientForAppoint.getAppointments().add(newAppointment);
                    patientEntitySessionBean.updatePatientEntity(patientForAppoint);
                    System.out.print(patientForAppoint.getFirstName() + " " + patientForAppoint.getLastName() + " appointment with " + doctorToAppoint.getFullName() + " at " + hours +":" + min + " on " + year +"-" + month + "-" + date  + " has been added." );
                    
                    System.out.println("Press any key to continue...> ");
                    System.out.println();
                    scanner.nextLine();
                    }catch(Exception ex){
                        System.err.println(ex);
                    }
     
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
                        System.out.print(patient.getFirstName() + " " + patient.getLastName() + " appointment with " + appointmentToDelete.getDoctor().getFullName() + " at " + appointmentToDelete.getAppointmentTimestamp().getHours() + " on " +appointmentToDelete.getAppointmentTimestamp().getDate() + " has been cancelled." );
                        
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
      private List<Time> getAllTimeSlots() {
        List<Time> allTimeSlots = new ArrayList<>();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.getDay() == 1 || currentTimestamp.getDay() == 2 || currentTimestamp.getDay() == 3) {
            allTimeSlots.add(new Time(8,30,0));
            allTimeSlots.add(new Time(9,0,0));
            allTimeSlots.add(new Time(9,30,0));
            allTimeSlots.add(new Time(10,0,0));
            allTimeSlots.add(new Time(10,30,0));
            allTimeSlots.add(new Time(11,0,0));
            allTimeSlots.add(new Time(11,30,0));
            allTimeSlots.add(new Time(12,0,0));
            allTimeSlots.add(new Time(13,30,0));
            allTimeSlots.add(new Time(14,0,0));
            allTimeSlots.add(new Time(14,30,0));
            allTimeSlots.add(new Time(15,0,0));
            allTimeSlots.add(new Time(15,30,0));
            allTimeSlots.add(new Time(16,0,0));
            allTimeSlots.add(new Time(16,30,0));
            allTimeSlots.add(new Time(17,0,0));
            allTimeSlots.add(new Time(17,30,0));
        } else if (currentTimestamp.getDay() == 4) {
            allTimeSlots.add(new Time(8,30,0));
            allTimeSlots.add(new Time(9,0,0));
            allTimeSlots.add(new Time(9,30,0));
            allTimeSlots.add(new Time(10,0,0));
            allTimeSlots.add(new Time(10,30,0));
            allTimeSlots.add(new Time(11,0,0));
            allTimeSlots.add(new Time(11,30,0));
            allTimeSlots.add(new Time(12,0,0));
            allTimeSlots.add(new Time(13,30,0));
            allTimeSlots.add(new Time(14,0,0));
            allTimeSlots.add(new Time(14,30,0));
            allTimeSlots.add(new Time(15,0,0));
            allTimeSlots.add(new Time(15,30,0));
            allTimeSlots.add(new Time(16,0,0));
            allTimeSlots.add(new Time(16,30,0));
        } else if (currentTimestamp.getDay() == 5) {
            allTimeSlots.add(new Time(8,30,0));
            allTimeSlots.add(new Time(9,0,0));
            allTimeSlots.add(new Time(9,30,0));
            allTimeSlots.add(new Time(10,0,0));
            allTimeSlots.add(new Time(10,30,0));
            allTimeSlots.add(new Time(11,0,0));
            allTimeSlots.add(new Time(11,30,0));
            allTimeSlots.add(new Time(12,0,0));
            allTimeSlots.add(new Time(13,30,0));
            allTimeSlots.add(new Time(14,0,0));
            allTimeSlots.add(new Time(14,30,0));
            allTimeSlots.add(new Time(15,0,0));
            allTimeSlots.add(new Time(15,30,0));
            allTimeSlots.add(new Time(16,0,0));
            allTimeSlots.add(new Time(16,30,0));
            allTimeSlots.add(new Time(17,0,0));
        }
        return allTimeSlots;
    }
       public Long dayDiff(java.sql.Date d1, java.sql.Date d2){
        return (d1.getTime()- d2.getTime())/(24*60*60*1000);
    }
}

    
    
    

