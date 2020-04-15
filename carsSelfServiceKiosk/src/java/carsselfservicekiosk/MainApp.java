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
import util.exception.NoAppointmentBookedException;
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

    private PatientEntity currentPatientEntity;

    public MainApp() {
    }

    public MainApp(PatientEntitySessionBeanRemote patientEntitySessionBean, DoctorEntitySessionBeanRemote doctorEntitySessionBean, AppointmentEntitySessionBeanRemote appointmentEntitySessionBean, ConsultationEntitySessionBeanRemote consultationEntitySessionBean) {
        this.patientEntitySessionBean = patientEntitySessionBean;
        this.doctorEntitySessionBean = doctorEntitySessionBean;
        this.appointmentEntitySessionBean = appointmentEntitySessionBean;
        this.consultationEntitySessionBean = consultationEntitySessionBean;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Self-Service Kiosk ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doRegisterNewPatient();

                } else if (response == 2) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");

                        menuMain();
                    } catch (InvalidLoginException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void doRegisterNewPatient() {
        Scanner scanner = new Scanner(System.in);
        try {
            PatientEntity newPatientEntity = new PatientEntity();

            System.out.println("*** CARS :: Registration Operation :: Register New Patient ***\n");
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
            System.out.println("Patient has been registered successfully!\n");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (javax.ejb.EJBException ex) {
            System.out.println();
            System.out.println("There already exists a patient record with entered Identity Number!");
            System.out.println("Patient not added!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Patient not registered!\n");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
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

        if (username.length() > 0 && password.length() > 0) {
            currentPatientEntity = patientEntitySessionBean.patientLogin(username, password);
        } else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Self-Service Kiosk :: Main ***\n");
            System.out.println("You are logged in as " + currentPatientEntity.getFullName() + "\n");
            System.out.println("1: Register Walk-In Consultation");
            System.out.println("2: Register Consultation By Appointment");
            System.out.println("3: View Appointments");
            System.out.println("4: Add Appointment");
            System.out.println("5: Cancel Appointment");
            System.out.println("6: Logout\n");

            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doRegisterWalkInConsultation();
                } else if (response == 2) {
                    doRegisterConsultationByAppointment();
                } else if (response == 3) {
                    doViewAppointments();
                } else if (response == 4) {
                    doAddAppointment();
                } else if (response == 5) {
                    doCancelAppointment();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6) {
                break;
            }
        }
    }

    private void doRegisterWalkInConsultation() {
        Scanner scanner = new Scanner(System.in);
        try {
            Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 15, 0, 0);

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
                if (de.getDoctorId() >= 10) {
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
            Long doctorId = Long.valueOf("0");
            try {
                doctorId = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Doctor ID inputted is not valid\nPlease choose one of the displayed doctors!");
            }
            boolean noneMatch = false;
            for (DoctorEntity de : availableDoctors) {
                if (de.getDoctorId().equals(doctorId)) {
                    noneMatch = true;
                }
            }
            if (noneMatch == false) {
                throw new InvalidInputException("Doctor ID inputted is not valid!\nPlease choose one of the displayed doctors!");
            }

            System.out.print("Enter Patient Identity Number> ");
            String patientIdentityNumber = scanner.nextLine().trim();

            DoctorEntity doctorEntity = doctorEntitySessionBean.retrieveDoctorEntityById(doctorId);
            PatientEntity patientEntity = currentPatientEntity;
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
            Long appointmentId = appointmentEntitySessionBean.createAppointmentEntity(patientIdentityNumber, doctorId, appointmentEntity);
            Long consultationId = consultationEntitySessionBean.createConsultationEntity(appointmentId, 30);

            String time = String.format("%02d", appointmentTimestamp.getHours()) + ":" + String.format("%02d", appointmentTimestamp.getMinutes());
            System.out.println(patientEntity.getFullName() + " appointment with Dr. " + doctorEntity.getFullName() + " has been booked at " + time + ".");
            System.out.println("Queue Number is : " + consultationEntitySessionBean.retrieveConsultationEntityById(consultationId).getQueueNumber());
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (javax.ejb.EJBException ex) {
            //try to give details about booking that has already been placed
            //put the press enter thing
            System.out.println();
            System.out.println("You have an existing appointment with another doctor at this time and date!\nUnable to book appointment!");
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Consultation not registered");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doRegisterConsultationByAppointment() {
        Scanner scanner = new Scanner(System.in);
        try {
            Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 15, 16, 15, 0, 0);

            System.out.println("*** CARS :: Registration Operation :: Register Consultation By Appointment ***\n");

            PatientEntity patientEntity = currentPatientEntity;
            System.out.println();

            List<AppointmentEntity> allPatientAppointments = patientEntity.getAppointments();
            List<AppointmentEntity> appointments = new ArrayList<>();
            boolean hasAppointment = false;
            for (AppointmentEntity ae : allPatientAppointments) {
                if (sameDay(ae.getAppointmentTimestamp(), currentTimestamp)
                        && (ae.getAppointmentTimestamp().after(currentTimestamp) || ae.getAppointmentTimestamp().equals(currentTimestamp))
                        && ae.getConsultation() == null) {
                    appointments.add(ae);
                    hasAppointment = true;
                }
            }

            System.out.println("Appointments:");
            System.out.println("Id |Date       |Time  |Doctor");
            if (hasAppointment == false) {
                throw new NoAppointmentBookedException(patientEntity.getFullName() + " does not have any appointments booked for today!");
            }
            HashSet<Long> appointmentIds = new HashSet<>();
            for (AppointmentEntity ae : appointments) {
                appointmentIds.add(ae.getAppointmentId());
                String date = String.valueOf(ae.getAppointmentTimestamp().getYear() + 1900) + "-" + String.format("%02d", ae.getAppointmentTimestamp().getMonth() + 1) + "-" + String.format("%02d", ae.getAppointmentTimestamp().getDate());
                String time = String.format("%02d", ae.getAppointmentTimestamp().getHours()) + ":" + String.format("%02d", ae.getAppointmentTimestamp().getMinutes());
                if (ae.getAppointmentId() < 100) {
                    System.out.println(String.format("%02d", ae.getAppointmentId()) + " |" + date + " |" + time + " |" + ae.getDoctor().getFullName());
                } else {
                    System.out.println(String.format("%02d", ae.getAppointmentId()) + "|" + date + " |" + time + " |" + ae.getDoctor().getFullName());
                }
            }
            System.out.println();

            System.out.print("Enter Appointment Id> ");
            Long appointmentId = Long.valueOf("0");
            try {
                appointmentId = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Appointment ID entered is not valid!\nPlease enter one of the appointment IDs displayed!");
            }
            System.out.println();
            if (!appointmentIds.contains(appointmentId)) {
                throw new InvalidInputException("Appointment ID entered is not valid\nPlease enter one of the appointment IDs displayed!");
            }

            AppointmentEntity appointmentEntity = appointmentEntitySessionBean.retrieveAppointmentEntityById(appointmentId);
            DoctorEntity doctorEntity = appointmentEntity.getDoctor();
            Long consultationId = consultationEntitySessionBean.createConsultationEntity(appointmentId, 30);

            String time = String.format("%02d", appointmentEntity.getAppointmentTimestamp().getHours()) + ":" + String.format("%02d", appointmentEntity.getAppointmentTimestamp().getMinutes());
            System.out.println(patientEntity.getFullName() + " appointment is confirmed with Dr. " + doctorEntity.getFullName() + " at " + time + ".");
            System.out.println("Queue Number is : " + consultationEntitySessionBean.retrieveConsultationEntityById(consultationId).getQueueNumber());
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Consultation not registered");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }
    
    private void doViewAppointments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CARS :: Appointment Operation :: View Patient Appointment  ***\n");
        System.out.println();

        try {
            PatientEntity patient = currentPatientEntity;
            List<AppointmentEntity> appointments = patient.getAppointments();
            //List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(id1);
            System.out.println("Appointments:");
            System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID", " | ", "Date", " | ", "Time", " | ", "Doctor");
            for (AppointmentEntity appointment : appointments) {
                System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", appointment.getAppointmentId().toString(), " | ", appointment.getAppointmentTimestamp().toString().substring(0, 10), " | ", appointment.getAppointmentTimestamp().toString().substring(11, 16), " | ", appointment.getDoctor().getFullName());
            }
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not view your appointment details!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }
    
    private void doAddAppointment() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("*** CARS :: Appointment Operation :: Add Appointment  ***\n");
            Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 15, 0, 0);
            List<DoctorEntity> doctorEntities = doctorEntitySessionBean.retrieveAllDoctorEntities();
            List<DoctorEntity> availableDoctors = new ArrayList<>();
            for (DoctorEntity de : doctorEntities) {
                if (!de.getLeaves().contains(new java.util.Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate()))) {
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
            Long id3 = Long.valueOf("0");
            try {
                id3 = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid doctor ID entered!\nPlease select one of doctor ID shown!");
            }
            DoctorEntity doctorToAppoint = doctorEntitySessionBean.retrieveDoctorEntityById(id3);
            System.out.print("Enter Date> ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.length() != 10) {
                throw new InvalidInputException("Invalid date entered!\nPlease enter date with format YYYY-MM-DD");
            }
            if (dateInput.charAt(4) != '-' || dateInput.charAt(7) != '-') {
                throw new InvalidInputException("Invalid date entered!\nPlease enter date with format YYYY-MM-DD");
            }
            System.out.println();

            int year = -1;
            int month = -1;
            int date = -1;

            try {
                year = Integer.valueOf(dateInput.substring(0, 4));
                month = Integer.valueOf(dateInput.substring(5, 7));
                date = Integer.valueOf(dateInput.substring(8, 10));
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid date entered!\nPlease enter date with format YYYY-MM-DD");
            }

            Date apptDate = new Date(year - 1900, month - 1, date);
            Date currDate = new Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate());
            if (dayDiff(apptDate, currDate) < 2) {
                throw new InvalidInputException("You need to book an appointment at least 2 days in advance!");
            }

            List<Time> allTimeSlots = getAllTimeSlots();
            List<Time> availableTimeslot = new ArrayList<>();
            HashSet<Timestamp> notAvail = doctorToAppoint.getNotAvail();
            for (Time timings : allTimeSlots) {
                if (!notAvail.contains(new Timestamp(year - 1900, month - 1, date, timings.getHours(), timings.getMinutes(), timings.getSeconds(), 0))) {
                    availableTimeslot.add(timings);
                }
            }
            System.out.print("Availability for " + doctorToAppoint.getFullName() + " on " + dateInput);
            System.out.println();
            for (Time timings : availableTimeslot) {
                System.out.print(timings.toString().substring(0, 5) + " ");
            }
            System.out.println();
            System.out.println();

            System.out.print("Enter Time with format HH:MM> ");

            String timeInput = scanner.nextLine().trim();
            if (timeInput.length() != 5) {
                throw new InvalidInputException("Invalid time entered!\nPlease enter time with format HH:MM");
            }
            if (timeInput.charAt(2) != ':') {
                throw new InvalidInputException("Invalid time entered!\nPlease enter time with format HH:MM");
            }
            int hours = -1;
            int min = -1;
            try {
                hours = Integer.valueOf(timeInput.substring(0, 2));
                min = Integer.valueOf(timeInput.substring(3, 5));
                if (hours > 23 || min > 59) {
                    throw new InvalidInputException("Invalid time entered!\nPlease enter one of the time displayed!");
                }
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid time entered!\nPlease enter time with format HH:MM");
            }

            if (!availableTimeslot.contains(new Time(hours, min, 0))) {
                throw new InvalidInputException("Invalid time entered!\nPlease enter one of the time displayed!");
            }

            Timestamp toAppoint = new Timestamp(year - 1900, month - 1, date, hours, min, 0, 0);
            AppointmentEntity newAppointment = new AppointmentEntity(toAppoint);
            appointmentEntitySessionBean.createAppointmentEntity(currentPatientEntity.getIdentityNum(), id3, newAppointment);
            PatientEntity patientForAppoint = patientEntitySessionBean.retrievePatientEntityByIdentityNum(currentPatientEntity.getIdentityNum());
            System.out.print(patientForAppoint.getFirstName() + " " + patientForAppoint.getLastName() + " appointment with " + doctorToAppoint.getFullName() + " at " + toAppoint.toString().substring(11, 16) + " on " + toAppoint.toString().substring(0, 10) + " has been added.\n");
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (javax.ejb.EJBException ex) {
            //try to give details about booking that has already been placed
            System.out.println();
            System.out.println("You already have an existing appointment with another doctor at this time and date!\nUnable to book appointment!");
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }
    
    private void doCancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 31, 0, 0);
        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment  ***\n");
        System.out.println();

        try {
            PatientEntity patient = currentPatientEntity;
            List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(currentPatientEntity.getIdentityNum());
            System.out.println("Appointments:");
            System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID", " | ", "Date", " | ", "Time", " | ", "Doctor");
            if (appointments.size() == 0) {
                throw new NoAppointmentBookedException(patient.getFullName() + " does not have any appointments to cancel!");
            }

            List<AppointmentEntity> cancellableAppointments = new ArrayList<>();
            for (AppointmentEntity appointment : appointments) {
                if (appointment.getAppointmentTimestamp().after(currentTimestamp) || appointment.getAppointmentTimestamp().equals(currentTimestamp)) {
                    cancellableAppointments.add(appointment);
                    System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", appointment.getAppointmentId().toString(), " | ", appointment.getAppointmentTimestamp().toString().substring(0, 10), " | ", appointment.getAppointmentTimestamp().toString().substring(11, 16), " | ", appointment.getDoctor().getFullName());
                }
            }
            System.out.println();

            System.out.print("Enter Appointment Id> ");
            Long idToDelete = Long.valueOf(0);

            try {
                idToDelete = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Appointment Id entered is not valid!\nPlease enter one of the Appointment Ids shown");
            }

            boolean validInput = false;
            for (AppointmentEntity ae : cancellableAppointments) {
                if (ae.getAppointmentId().equals(idToDelete)) {
                    validInput = true;
                }
            }
            if (!validInput) {
                throw new InvalidInputException("Appointment Id entered is not valid!\nPlease enter one of the Appointment Ids shown");
            }

            AppointmentEntity appointmentToDelete = appointmentEntitySessionBean.retrieveAppointmentEntityById(idToDelete);
            Timestamp toDeleteTimestamp = appointmentToDelete.getAppointmentTimestamp();
            appointmentEntitySessionBean.deleteAppointmentEntity(idToDelete);
            System.out.println(patient.getFirstName() + " " + patient.getLastName() + " appointment with " + appointmentToDelete.getDoctor().getFullName() + " at " + toDeleteTimestamp.toString().substring(11, 16) + " on " + toDeleteTimestamp.toString().substring(0, 10) + " has been cancelled.");

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("No appointment cancelled!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
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
        Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 15, 0, 0);
        if (currentTimestamp.getDay() == 1 || currentTimestamp.getDay() == 2 || currentTimestamp.getDay() == 3) {
            allTimeSlots.add(new Time(8, 30, 0));
            allTimeSlots.add(new Time(9, 0, 0));
            allTimeSlots.add(new Time(9, 30, 0));
            allTimeSlots.add(new Time(10, 0, 0));
            allTimeSlots.add(new Time(10, 30, 0));
            allTimeSlots.add(new Time(11, 0, 0));
            allTimeSlots.add(new Time(11, 30, 0));
            allTimeSlots.add(new Time(12, 0, 0));
            allTimeSlots.add(new Time(13, 30, 0));
            allTimeSlots.add(new Time(14, 0, 0));
            allTimeSlots.add(new Time(14, 30, 0));
            allTimeSlots.add(new Time(15, 0, 0));
            allTimeSlots.add(new Time(15, 30, 0));
            allTimeSlots.add(new Time(16, 0, 0));
            allTimeSlots.add(new Time(16, 30, 0));
            allTimeSlots.add(new Time(17, 0, 0));
            allTimeSlots.add(new Time(17, 30, 0));
        } else if (currentTimestamp.getDay() == 4) {
            allTimeSlots.add(new Time(8, 30, 0));
            allTimeSlots.add(new Time(9, 0, 0));
            allTimeSlots.add(new Time(9, 30, 0));
            allTimeSlots.add(new Time(10, 0, 0));
            allTimeSlots.add(new Time(10, 30, 0));
            allTimeSlots.add(new Time(11, 0, 0));
            allTimeSlots.add(new Time(11, 30, 0));
            allTimeSlots.add(new Time(12, 0, 0));
            allTimeSlots.add(new Time(13, 30, 0));
            allTimeSlots.add(new Time(14, 0, 0));
            allTimeSlots.add(new Time(14, 30, 0));
            allTimeSlots.add(new Time(15, 0, 0));
            allTimeSlots.add(new Time(15, 30, 0));
            allTimeSlots.add(new Time(16, 0, 0));
            allTimeSlots.add(new Time(16, 30, 0));
        } else if (currentTimestamp.getDay() == 5) {
            allTimeSlots.add(new Time(8, 30, 0));
            allTimeSlots.add(new Time(9, 0, 0));
            allTimeSlots.add(new Time(9, 30, 0));
            allTimeSlots.add(new Time(10, 0, 0));
            allTimeSlots.add(new Time(10, 30, 0));
            allTimeSlots.add(new Time(11, 0, 0));
            allTimeSlots.add(new Time(11, 30, 0));
            allTimeSlots.add(new Time(12, 0, 0));
            allTimeSlots.add(new Time(13, 30, 0));
            allTimeSlots.add(new Time(14, 0, 0));
            allTimeSlots.add(new Time(14, 30, 0));
            allTimeSlots.add(new Time(15, 0, 0));
            allTimeSlots.add(new Time(15, 30, 0));
            allTimeSlots.add(new Time(16, 0, 0));
            allTimeSlots.add(new Time(16, 30, 0));
            allTimeSlots.add(new Time(17, 0, 0));
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
    
    private Long dayDiff(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
    }

}
