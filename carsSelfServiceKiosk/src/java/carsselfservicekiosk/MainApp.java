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
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import util.exception.DoctorNotAvailableForWalkInException;
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.NoAvailableDoctorsException;

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
    
    private PatientEntity currentPatientEntity;

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
            System.out.println("*** Welcome to Self-Service Kiosk ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;
            
            while(response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doRegisterNewPatient();
                    
                }
                else if(response == 2) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        
                        menuMain();
                    }
                    catch(InvalidLoginException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                }
                else if (response == 3) {
                    break;
                }
                else {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 3) {
                break;
            }
        }
    }
    
    private void doRegisterNewPatient() {
        try {
            Scanner scanner = new Scanner(System.in);
            PatientEntity newPatientEntity = new PatientEntity();

            System.out.println("*** Self-Service Kiosk :: Register ***\n");
            System.out.print("Enter Identity Number> ");
            newPatientEntity.setIdentityNum(scanner.nextLine().trim());
            System.out.print("Enter Password> ");
            newPatientEntity.setPassword(scanner.nextLine().trim());
            System.out.print("Enter First Name> ");
            newPatientEntity.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            newPatientEntity.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            newPatientEntity.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            newPatientEntity.setAge(scanner.nextLine().trim());
            System.out.print("Enter Phone> ");
            newPatientEntity.setPhoneNumber(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            newPatientEntity.setAddress(scanner.nextLine().trim());
            
            patientEntitySessionBean.createPatientEntity(newPatientEntity);
            System.out.println("Registration is successful!\n"); 
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Registration is unsuccessful!\n");
        }
    }
    
    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** Self-Service Kiosk :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0) {
            currentPatientEntity = patientEntitySessionBean.patientLogin(username, password);      
        }
        else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }
    
    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true) {
            System.out.println("*** Self-Service Kiosk :: Main ***\n");
            System.out.println("You are logged in as " + currentPatientEntity.getFullName() + "\n");
            System.out.println("1: Register Walk-In Consultation");
            System.out.println("2: Register Consultation By Appointment");
            System.out.println("3: View Appointments");
            System.out.println("4: Add Appointment");
            System.out.println("5: Cancel Appointment");
            System.out.println("6: Logout\n");
            
            response = 0;
            
            while(response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doRegisterWalkInConsultation();
                }
                else if (response == 2) {
                    doRegisterConsultationByAppointment();
                }
                else if (response == 3) {
                    
                }
                else if (response == 4) {
                    
                }
                else if (response == 5) {
                    
                } else if (response == 6) {
                    
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 6) {
                break;
            }
        }
    }
    
    private void doRegisterWalkInConsultation() {
        try {
            Scanner scanner = new Scanner(System.in);
            Timestamp currentTimestamp = new Timestamp(2020-1900,6,5,13,35,0,0);
            
            List<DoctorEntity> doctorEntities = doctorEntitySessionBean.retrieveAllDoctorEntities();
            List<DoctorEntity> availableDoctors = new ArrayList<>();
            for (DoctorEntity de : doctorEntities) {
                if (!de.getLeaves().contains(new Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate()))) {
                    availableDoctors.add(de);
                }
            }
            
            List<Time> allTimeSlots = getAllTimeSlots();
            Time lowerBound = new Time(currentTimestamp.getHours(), currentTimestamp.getMinutes(), currentTimestamp.getSeconds());
            Time upperBound = new Time(currentTimestamp.getHours() + 3, currentTimestamp.getMinutes(), currentTimestamp.getSeconds());
            List<Time> availableForWalkIn = new ArrayList<>();
            for (Time timeSlot : allTimeSlots) {
                if ((compareTime(lowerBound, timeSlot) < 0 || compareTime(lowerBound, timeSlot) == 0)
                        && (compareTime(timeSlot, upperBound) < 0 || compareTime(timeSlot, upperBound) == 0)) {
                    availableForWalkIn.add(timeSlot);
                }
            }
            
            System.out.println("*** CARS :: Registration Operation :: Register Walk-In Consultation ***\n");
            System.out.println("Doctor:");
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
            
            System.out.println("Availability:");
            System.out.print("Time  |");
            for (DoctorEntity de : availableDoctors) {
                if ( de.getDoctorId() >= 10) {
                    System.out.print(de.getDoctorId() + "|");
                } else {
                    System.out.print(de.getDoctorId() + " |");
                }
            }
            System.out.println();
            boolean atLeastOneAvailableSlot = false;
            for (Time timeSlot : availableForWalkIn) {
                System.out.print(String.valueOf(timeSlot).substring(0, 5) + " |");
                for (DoctorEntity de : availableDoctors) {
                    if (de.getNotAvail().contains(new Timestamp(currentTimestamp.getYear(), 
                            currentTimestamp.getMonth(), currentTimestamp.getDate(), timeSlot.getHours(), 
                            timeSlot.getMinutes(), timeSlot.getSeconds(), 0))) {
                        System.out.print("X |");
                    } else {
                        System.out.print("O |");
                        atLeastOneAvailableSlot = true;
                    }
                }
                System.out.println();
            }
            System.out.println();
            if (atLeastOneAvailableSlot == false) {
                throw new NoAvailableDoctorsException("No doctor is available for walk-in consultation!");
            }
            
            System.out.print("Enter Doctor Id> ");
            Long doctorId = Long.valueOf(scanner.nextLine().trim());
            boolean noneMatch = false;
            for (DoctorEntity de : availableDoctors) {
                if (de.getDoctorId().equals(doctorId)) {
                    noneMatch = true;
                }
            }
            if (noneMatch == false) {
                throw new InvalidInputException("Doctor ID inputted is not valid!");
            }
            
            DoctorEntity doctorEntity = doctorEntitySessionBean.retrieveDoctorEntityById(doctorId);
            PatientEntity patientEntity = currentPatientEntity;
            //appointmentTimeStamp is initialized to a nonsense value first to prevent error
            Timestamp appointmentTimestamp = new Timestamp(0);
            boolean doctorIsFreeForWalkIn = false;
            for (Time timeSlot : availableForWalkIn) {
                if (!doctorEntity.getNotAvail().contains(new Timestamp(currentTimestamp.getYear(), 
                            currentTimestamp.getMonth(), currentTimestamp.getDate(), timeSlot.getHours(), 
                            timeSlot.getMinutes(), timeSlot.getSeconds(), 0)) && doctorIsFreeForWalkIn == false) {
                    appointmentTimestamp = new Timestamp(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate(), 
                            timeSlot.getHours(), timeSlot.getMinutes(), timeSlot.getSeconds(), 0); 
                    doctorIsFreeForWalkIn = true;
                } else if (!doctorEntity.getNotAvail().contains(new Timestamp(currentTimestamp.getYear(), 
                            currentTimestamp.getMonth(), currentTimestamp.getDate(), timeSlot.getHours(), 
                            timeSlot.getMinutes(), timeSlot.getSeconds(), 0)) && doctorIsFreeForWalkIn == true
                            && appointmentTimestamp.after(new Timestamp(currentTimestamp.getYear(), 
                            currentTimestamp.getMonth(), currentTimestamp.getDate(), timeSlot.getHours(), 
                            timeSlot.getMinutes(), timeSlot.getSeconds(), 0))) {
                    appointmentTimestamp = new Timestamp(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate(), 
                            timeSlot.getHours(), timeSlot.getMinutes(), timeSlot.getSeconds(), 0);
                }
            }
            if (doctorIsFreeForWalkIn == false) {
                throw new DoctorNotAvailableForWalkInException("Doctor chosen is not available for walk-in consultation");
            }
            
            AppointmentEntity appointmentEntity = new AppointmentEntity(appointmentTimestamp);
            Long appointmentId = appointmentEntitySessionBean.createAppointmentEntity(currentPatientEntity.getIdentityNum(), doctorId, appointmentEntity);
            Long consultationId = consultationEntitySessionBean.createConsultationEntity(appointmentId, 30);
            
            String time = String.format("%02d", appointmentTimestamp.getHours()) + ":" + String.format("%02d", appointmentTimestamp.getMinutes());
            System.out.println(patientEntity.getFullName() + " appointment with Dr. " + doctorEntity.getFullName() + " has been booked at " + time + ".");
            System.out.println("Queue Number is : " + consultationEntitySessionBean.retrieveConsultationEntityById(consultationId).getQueueNumber());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Consultation not registered");
        }
    }
    
    private void doRegisterConsultationByAppointment() {
        try {
            Scanner scanner = new Scanner(System.in);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            
            System.out.println("*** CARS :: Registration Operation :: Register Consultation By Appointment ***\n");
            PatientEntity patientEntity = currentPatientEntity;

            List<AppointmentEntity> allPatientAppointments = patientEntity.getAppointments();
            List<AppointmentEntity> appointments = new ArrayList<>();
            for (AppointmentEntity ae : allPatientAppointments) {
                if (sameDay(ae.getAppointmentTimestamp(), currentTimestamp) 
                        && (ae.getAppointmentTimestamp().after(currentTimestamp) || ae.getAppointmentTimestamp().equals(currentTimestamp))
                        && ae.getConsultation() == null) {
                    appointments.add(ae);
                }
            }
            
            System.out.println("Appointments:");
            System.out.println("Id |Date       |Time  |Doctor");
            HashSet<Long> appointmentIds = new HashSet<>();
            for (AppointmentEntity ae : appointments) {
                appointmentIds.add(ae.getAppointmentId());
                String date = String.valueOf(ae.getAppointmentTimestamp().getYear() + 1900) + "-" + String.format("%02d", ae.getAppointmentTimestamp().getMonth()) + "-" + String.format("%02d", ae.getAppointmentTimestamp().getDay());
                String time = String.format("%02d", ae.getAppointmentTimestamp().getHours()) + ":" + String.format("%02d", ae.getAppointmentTimestamp().getMinutes());
                if (ae.getAppointmentId() < 100) {
                    System.out.println(String.format("%02d",ae.getAppointmentId()) + " |" + date + " |" + time + " |" + ae.getDoctor().getFullName());
                } else {
                    System.out.println(String.format("%02d",ae.getAppointmentId()) + "|" + date + " |" + time + " |" + ae.getDoctor().getFullName());
                }
            }
            System.out.println();
            
            System.out.print("Enter Appointment Id> ");
            Long appointmentId = Long.valueOf(scanner.nextLine().trim());
            if (!appointmentIds.contains(appointmentId)) {
                throw new InvalidInputException("Appointment ID entered is not valid!");
            }
            
            AppointmentEntity appointmentEntity = appointmentEntitySessionBean.retrieveAppointmentEntityById(appointmentId);
            DoctorEntity doctorEntity = appointmentEntity.getDoctor();
            Long consultationId = consultationEntitySessionBean.createConsultationEntity(appointmentId, 30);
            
            String time = String.format("%02d", appointmentEntity.getAppointmentTimestamp().getHours()) + ":" + String.format("%02d", appointmentEntity.getAppointmentTimestamp().getMinutes());
            System.out.println(patientEntity.getFullName() + " appointment with Dr. " + doctorEntity.getFullName() + " has been booked at " + time + ".");
            System.out.println("Queue Number is : " + consultationEntitySessionBean.retrieveConsultationEntityById(consultationId).getQueueNumber());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Consultation not registered");
        }
    }
    
    private boolean sameDay(Timestamp left, Timestamp right) {
        if (left.getYear() == right.getYear() && left.getMonth() == right.getMonth() && left.getDate() == right.getDate()) {
            return true; 
        } else {
            return false;
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
    
    private int compareTime(Time left, Time right) {
        if (left.getHours() < right.getHours()) {
            return -1;
        } else if (left.getHours() > right.getHours()) {
            return 1;
        } else {
            if (left.getMinutes() < right.getMinutes()) {
                return -1;
            } else if (left.getMinutes() > right.getMinutes()) {
                return 1;
            } else {
                if (left.getSeconds() < right.getSeconds()) {
                    return -1;
                } else if (left.getSeconds() > right.getSeconds()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
        
}
