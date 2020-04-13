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
import util.exception.InvalidInputException;

/**
 *
 * @author Lenovo
 */
public class AppointmentOperationsModule {

    private PatientEntitySessionBeanRemote patientEntitySessionBean;
    private DoctorEntitySessionBeanRemote doctorEntitySessionBean;
    private AppointmentEntitySessionBeanRemote appointmentEntitySessionBean;

    public AppointmentOperationsModule() {

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
                    doViewPatientAppointments();
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

    private void doViewPatientAppointments() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CARS :: Appointment Operation :: View Patient Appointment  ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String id1 = scanner.nextLine().trim();
        System.out.println();

        try {
            PatientEntity patient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id1);
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
            System.err.println(ex.getMessage());
            System.out.println("Could not view patient's appointment details, please key in the corret input!");
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
            Long id3 = Long.valueOf(scanner.nextLine().trim());
            DoctorEntity doctorToAppoint = doctorEntitySessionBean.retrieveDoctorEntityById(id3);
            System.out.print("Enter Date> ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.length() != 10) {
                throw new InvalidInputException("Invalid date entered!");
            }
            System.out.println();

            int year = Integer.valueOf(dateInput.substring(0, 4));
            int month = Integer.valueOf(dateInput.substring(5, 7));
            int date = Integer.valueOf(dateInput.substring(8, 10));

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

            System.out.print("Enter Time> ");
            String timeInput = scanner.nextLine().trim();
            int hours = Integer.valueOf(timeInput.substring(0, 2));
            int min = Integer.valueOf(timeInput.substring(3, 5));
            Timestamp toAppoint = new Timestamp(year - 1900, month - 1, date, hours, min, 0, 0);
            AppointmentEntity newAppointment = new AppointmentEntity(toAppoint);
            System.out.print("Enter Patient Identity Number> ");
            String patientId = scanner.nextLine().trim();
            appointmentEntitySessionBean.createAppointmentEntity(patientId, id3, newAppointment);
            PatientEntity patientForAppoint = patientEntitySessionBean.retrievePatientEntityByIdentityNum(patientId);
            System.out.print(patientForAppoint.getFirstName() + " " + patientForAppoint.getLastName() + " appointment with " + doctorToAppoint.getFullName() + " at " + toAppoint.toString().substring(11, 16) + " on " + toAppoint.toString().substring(0, 10) + " has been added.\n");
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (NumberFormatException ex) {
            System.out.println("Invalid date/time inputted!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Appointment not booked!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doCancelAppointment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CARS :: Appointment Operation :: Cancel Appointment  ***\n");
        System.out.print("Enter Patient Identity Number> ");
        String id2 = scanner.nextLine().trim();
        System.out.println();

        try {
            PatientEntity patient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id2);
            List<AppointmentEntity> appointments = patientEntitySessionBean.viewAppointmentmentByPatientId(id2);
            System.out.println("Appointments:");
            System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", "ID", " | ", "Date", " | ", "Time", " | ", "Doctor");
            for (AppointmentEntity appointment : appointments) {
                System.out.printf("%8s%2s%20s%2s%20s%2s%15s\n", appointment.getAppointmentId().toString(), " | ", appointment.getAppointmentTimestamp().toString().substring(0, 10), " | ", appointment.getAppointmentTimestamp().toString().substring(11, 16), " | ", appointment.getDoctor().getFullName());
            }
            System.out.println();

            System.out.print("Enter Appointment Id> ");
            Long idToDelete = Long.valueOf(scanner.nextLine().trim());
            AppointmentEntity appointmentToDelete = appointmentEntitySessionBean.retrieveAppointmentEntityById(idToDelete);
            Timestamp toDeleteTimestamp = appointmentToDelete.getAppointmentTimestamp();
            //patientEntitySessionBean.cancelAppointment(idToDelete, id2);
            appointmentEntitySessionBean.deleteAppointmentEntity(idToDelete);
            System.out.println(patient.getFirstName() + " " + patient.getLastName() + " appointment with " + appointmentToDelete.getDoctor().getFullName() + " at " + toDeleteTimestamp.toString().substring(11, 16) + " on " + toDeleteTimestamp.toString().substring(0, 10) + " has been cancelled.");

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println("Could not delete appointment for given id!");
            System.out.print("Press any key to continue...> ");
            System.out.println();
            scanner.nextLine();
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

    public Long dayDiff(Date d1, Date d2) {
        return (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
    }
}
