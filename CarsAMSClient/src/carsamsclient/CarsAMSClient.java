/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsamsclient;

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
import util.exception.InvalidInputException;
import util.exception.InvalidLoginException;
import util.exception.NoAppointmentBookedException;
import ws.client.AppointmentNotFoundException_Exception;
import ws.client.DoctorNotFoundException_Exception;
import ws.client.InvalidInputException_Exception;
import ws.client.InvalidLoginException_Exception;
import ws.client.PatientNotFoundException_Exception;
import ws.client.SQLIntegrityConstraintViolationException_Exception;

/**
 *
 * @author Max
 */
public class CarsAMSClient {

    private static ws.client.PatientEntity currentPatientEntity;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to AMS Client ***\n");
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
                    } catch (InvalidLoginException_Exception ex) {
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

    private static void doRegisterNewPatient() {
        Scanner scanner = new Scanner(System.in);
        try {
            ws.client.PatientEntity newPatientEntity = new ws.client.PatientEntity();

            System.out.println("*** AMS Client :: Register ***\n");
            
            System.out.print("Enter Identity Number> ");
            String identityNum = scanner.nextLine().trim();
            if (identityNum.length() != 9) {
                throw new InvalidInputException("Invalid identity number\nPlease input valid NRIC/Passport number");
            }
            Character firstChar = identityNum.charAt(0);
            Character lastChar = identityNum.charAt(8);
            if (!Character.isUpperCase(firstChar) || !Character.isUpperCase(lastChar)) {
                throw new InvalidInputException("Invalid identity number\nPlease input valid NRIC/Passport number");
            }
            try {
                int checkIdentityNum = Integer.valueOf(identityNum.substring(1, 8));
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid identity number!\nPlease input valid NRIC/Passport number");
            }
            newPatientEntity.setIdentityNum(identityNum);
            
            System.out.print("Enter Password> ");
            String password = scanner.nextLine().trim();
            if (password.length() != 6) {
                throw new InvalidInputException("Invalid Password!\nPassword must be exactly 6 digits long and numeric!");
            }
            try {
                int checkPassword = Integer.valueOf(password);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid Password\nPassword must be exactly 6 digits long and numeric!");
            }
            newPatientEntity.setPassword(password);
            
            System.out.print("Enter First Name> ");
            String firstName = scanner.nextLine().trim();
            if (firstName.equals("") || !Character.isUpperCase(firstName.charAt(0))) {
                throw new InvalidInputException("Invalid first name input!\nFirst names must not be empty and First names must start with an uppercase character!");
            }
            newPatientEntity.setFirstName(firstName);
            
            System.out.print("Enter Last Name> ");
            String lastName = scanner.nextLine().trim();
            if (lastName.equals("") || !Character.isUpperCase(lastName.charAt(0))) {
                throw new InvalidInputException("Invalid last name input\nLast names must not be empty and Last names must start with an uppercase character!");
            }
            newPatientEntity.setLastName(lastName);
            
            System.out.print("Enter Gender> ");
            String gender = scanner.nextLine().trim();
            if (gender.equals("M") || gender.equals("m") || gender.equals("male") || gender.equals("Male")) {
                newPatientEntity.setGender("M");
            } else if (gender.equals("F") || gender.equals("f") || gender.equals("female") || gender.equals("Female")) {
                newPatientEntity.setGender("F");
            } else {
                throw new InvalidInputException("Gender entered is not valid!\nPlease enter either M or F!");
            }
            
            System.out.print("Enter Age> ");
            String age = scanner.nextLine().trim();
            if (age.equals("")) {
                throw new InvalidInputException("Age entered is not valid!\nPlease enter a number!");
            }
            try {
                long checkAge = Long.valueOf(age);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Age entered is not valid!\nPlease enter a number!");
            }
            newPatientEntity.setAge(age);
            
            System.out.print("Enter Phone> ");
            String phoneNumber = scanner.nextLine().trim();
            if (phoneNumber.equals("")) {
                throw new InvalidInputException("Phone Number entered is not valid\nPlease enter phone number without area or country code!");
            }
            try {
                long checkPhoneNumber = Long.valueOf(phoneNumber);
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Phone Number entered is not valid\nPlease enter phone number without area or country code!");
            }
            newPatientEntity.setPhoneNumber(phoneNumber);
            
            System.out.print("Enter Address> ");
            String address = scanner.nextLine().trim();
            if (address.equals("")) {
                throw new InvalidInputException("Address entered is not valid!\nAddress entered must not be empty!");
            }
            newPatientEntity.setAddress(address);

            createPatientEntity(newPatientEntity);
            System.out.println("Patient has been registered successfully!\n");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } /*catch (javax.ejb.EJBException ex) {
                System.out.println();
                System.out.println("There already exists a patient record with entered Identity Number!");
                System.out.println("Patient not added!");
                System.out.print("Press any key to continue...> ");
                scanner.nextLine();
            }*/ catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Patient not registered!\n");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private static void doLogin() throws InvalidLoginException, InvalidLoginException_Exception {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** AMS Client :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter Password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            CarsAMSClient.currentPatientEntity = patientLoginWebService(username, password);
        } else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }

    private static void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** AMS Client :: Main ***\n");
            System.out.println("You are logged in as " + currentPatientEntity.getFirstName() + " " + currentPatientEntity.getLastName() + "\n");
            System.out.println("1: View Appointments");
            System.out.println("2: Add Appointment");
            System.out.println("3: Cancel Appointment");
            System.out.println("4: Logout\n");

            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doViewAppointments();
                } else if (response == 2) {
                    doAddAppointment();
                } else if (response == 3) {
                    doCancelAppointment();
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

    private static void doViewAppointments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** AMS Client :: View Appointments  ***\n");
        System.out.println();

        try {
            currentPatientEntity = retrievePatientEntityByIdentityNumWebService(currentPatientEntity.getIdentityNum());
            List<ws.client.AppointmentEntity> appointments = currentPatientEntity.getAppointments();
            System.out.println("Appointments:");
            System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID", " | ", "Date", " | ", "Time", " | ", "Doctor");
            for (ws.client.AppointmentEntity appt : appointments) {
                ws.client.AppointmentEntity appointment = retrieveAppointmentEntityByIdWebService(appt.getAppointmentId());
                System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", appointment.getAppointmentId().toString(), " | ", appointment.getTimestamp().substring(0, 10), " | ", appointment.getTimestamp().substring(11, 16), " | ", appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
            }
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Could not view your appointment details!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private static void doAddAppointment() {
        Scanner scanner = new Scanner(System.in);
        try {
            currentPatientEntity = retrievePatientEntityByIdentityNumWebService(currentPatientEntity.getIdentityNum());
            System.out.println("*** AMS Client :: Add Appointment  ***\n");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            List<ws.client.DoctorEntity> doctorEntities = retrieveAllDoctorEntitiesWebService();
            List<ws.client.DoctorEntity> availableDoctors = new ArrayList<>();
            for (ws.client.DoctorEntity d : doctorEntities) {
                availableDoctors.add(d);
            }
            System.out.println("Id |Name");
            for (ws.client.DoctorEntity de : availableDoctors) {
                if (de.getDoctorId() >= 100) {
                    System.out.println(de.getDoctorId() + "|" + de.getFirstName() + " " + de.getLastName());
                } else if (de.getDoctorId() >= 10) {
                    System.out.println(de.getDoctorId() + " |" + de.getFirstName() + " " + de.getLastName());
                } else {
                    System.out.println(de.getDoctorId() + "  |" + de.getFirstName() + " " + de.getLastName());
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
            ws.client.DoctorEntity doctorToAppoint = retrieveDoctorEntityByIdWebService(id3);
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
            HashSet<Date> doctorLeaves = new HashSet(doctorToAppoint.getLeaves());
            for (ws.client.Date d : doctorToAppoint.getLeaves()) {
                System.out.println("1");
            }
            if (doctorLeaves.contains(apptDate)) {
                throw new InvalidInputException("Doctor is on leave on entered date!\nPlease select another doctor!");
            }

            List<Time> allTimeSlots = getAllTimeSlots();
            List<Time> availableTimeslot = new ArrayList<>();
            HashSet<ws.client.Timestamp> notAvail = new HashSet(doctorToAppoint.getNotAvail());
            for (Time timings : allTimeSlots) {
                if (!notAvail.contains(new Timestamp(year - 1900, month - 1, date, timings.getHours(), timings.getMinutes(), timings.getSeconds(), 0))) {
                    availableTimeslot.add(timings);
                }
            }
            System.out.print("Availability for " + doctorToAppoint.getFirstName() + " " + doctorToAppoint.getLastName() + " on " + dateInput);
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

            //ws.client.Timestamp toAppoint = new ws.client.Timestamp(year - 1900, month - 1, date, hours, min, 0, 0);
            /*ws.client.AppointmentEntity newAppointment = new ws.client.AppointmentEntity();
            newAppointment.setYear(year);
            newAppointment.setMonth(month);
            newAppointment.setDate(date);
            newAppointment.setHour(hour);
            newAppointment.setMin(min);*/
            Long appointmentId = createAppointmentEntity(currentPatientEntity.getIdentityNum(), id3, year, month, date, hours, min);
            ws.client.PatientEntity patientForAppoint = retrievePatientEntityByIdentityNumWebService(currentPatientEntity.getIdentityNum());
            System.out.print(patientForAppoint.getFirstName() + " " + patientForAppoint.getLastName() + " appointment with " + doctorToAppoint.getFirstName() + " " + doctorToAppoint.getLastName() + " at " + String.format("%02d", hours) + ":" + String.format("%02d", min) + " on " + String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", date) + " has been added.\n");
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } /*catch (javax.ejb.EJBException ex) {
            //try to give details about booking that has already been placed
            System.out.println();
            System.out.println("You already have an existing appointment with another doctor at this time and date!\nUnable to book appointment!");
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }*/ catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private static void doCancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("*** AMS Client :: Cancel Appointment  ***\n");
        System.out.println();

        try {
            currentPatientEntity = retrievePatientEntityByIdentityNumWebService(currentPatientEntity.getIdentityNum());
            ws.client.PatientEntity patient = currentPatientEntity;
            List<ws.client.AppointmentEntity> appointments = patient.getAppointments();
            System.out.println("Appointments:");
            System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID", " | ", "Date", " | ", "Time", " | ", "Doctor");
            if (appointments.size() == 0) {
                throw new NoAppointmentBookedException(patient.getFirstName() + " " + patient.getLastName() + " does not have any appointments to cancel!");
            }

            List<ws.client.AppointmentEntity> cancellableAppointments = new ArrayList<>();
            for (ws.client.AppointmentEntity appt : appointments) {
                if (after(Integer.valueOf(appt.getTimestamp().substring(0, 4)), Integer.valueOf(appt.getTimestamp().substring(5, 7)), Integer.valueOf(appt.getTimestamp().substring(8, 10)), Integer.valueOf(appt.getTimestamp().substring(11, 13)), Integer.valueOf(appt.getTimestamp().substring(14, 16)), Integer.valueOf(appt.getTimestamp().substring(17, 19)), Integer.valueOf(appt.getTimestamp().substring(20, 21)), currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate(), currentTimestamp.getHours(), currentTimestamp.getMinutes(), currentTimestamp.getSeconds(), currentTimestamp.getNanos()) || appt.getAppointmentTimestamp().equals(currentTimestamp)) {
                    ws.client.AppointmentEntity appointment = retrieveAppointmentEntityByIdWebService(appt.getAppointmentId());
                    cancellableAppointments.add(appointment);
                    System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", appointment.getAppointmentId().toString(), " | ", appointment.getTimestamp().substring(0, 10), " | ", appointment.getTimestamp().substring(11, 16), " | ", appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
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
            for (ws.client.AppointmentEntity ae : cancellableAppointments) {
                if (ae.getAppointmentId().equals(idToDelete)) {
                    validInput = true;
                }
            }
            if (!validInput) {
                throw new InvalidInputException("Appointment Id entered is not valid!\nPlease enter one of the Appointment Ids shown");
            }

            ws.client.AppointmentEntity appointmentToDelete = retrieveAppointmentEntityByIdWebService(idToDelete);
            ws.client.Timestamp toDeleteTimestamp = appointmentToDelete.getAppointmentTimestamp();
            deleteAppointmentEntity(idToDelete);
            System.out.println(patient.getFirstName() + " " + patient.getLastName() + " appointment with " + appointmentToDelete.getDoctor().getFirstName() + " " + appointmentToDelete.getDoctor().getLastName() + " at " + appointmentToDelete.getTimestamp().substring(11, 16) + " on " + appointmentToDelete.getTimestamp().substring(0, 10) + " has been cancelled.");

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("No appointment cancelled!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private static List<Time> getAllTimeSlots() {
        List<Time> allTimeSlots = new ArrayList<>();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
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

    private static Long dayDiff(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
    }

    private static String createPatientEntity(ws.client.PatientEntity arg0) throws InvalidInputException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.createPatientEntity(arg0);
    }

    private static void deleteAppointmentEntity(java.lang.Long arg0) throws AppointmentNotFoundException_Exception, SQLIntegrityConstraintViolationException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        port.deleteAppointmentEntity(arg0);
    }

    private static ws.client.PatientEntity patientLoginWebService(java.lang.String arg0, java.lang.String arg1) throws InvalidLoginException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.patientLoginWebService(arg0, arg1);
    }

    private static java.util.List<ws.client.DoctorEntity> retrieveAllDoctorEntitiesWebService() {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.retrieveAllDoctorEntitiesWebService();
    }

    private static ws.client.AppointmentEntity retrieveAppointmentEntityByIdWebService(java.lang.Long arg0) throws AppointmentNotFoundException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.retrieveAppointmentEntityByIdWebService(arg0);
    }

    private static ws.client.DoctorEntity retrieveDoctorEntityByIdWebService(java.lang.Long arg0) throws DoctorNotFoundException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.retrieveDoctorEntityByIdWebService(arg0);
    }

    private static ws.client.PatientEntity retrievePatientEntityByIdentityNumWebService(java.lang.String arg0) throws PatientNotFoundException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.retrievePatientEntityByIdentityNumWebService(arg0);
    }

    private static Long createAppointmentEntity(java.lang.String arg0, java.lang.Long arg1, java.lang.Integer arg2, java.lang.Integer arg3, java.lang.Integer arg4, java.lang.Integer arg5, java.lang.Integer arg6) throws DoctorNotFoundException_Exception, InvalidInputException_Exception, PatientNotFoundException_Exception {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.createAppointmentEntity(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }

    private static boolean after(java.lang.Integer arg0, java.lang.Integer arg1, java.lang.Integer arg2, java.lang.Integer arg3, java.lang.Integer arg4, java.lang.Integer arg5, java.lang.Integer arg6, java.lang.Integer arg7, java.lang.Integer arg8, java.lang.Integer arg9, java.lang.Integer arg10, java.lang.Integer arg11, java.lang.Integer arg12, java.lang.Integer arg13) {
        ws.client.AMSClientWebService_Service service = new ws.client.AMSClientWebService_Service();
        ws.client.AMSClientWebService port = service.getAMSClientWebServicePort();
        return port.after(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
    }
}
