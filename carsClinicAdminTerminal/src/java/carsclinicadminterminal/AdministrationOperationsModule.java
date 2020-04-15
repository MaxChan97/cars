/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsclinicadminterminal;

import ejb.session.stateless.DoctorEntitySessionBeanRemote;
import ejb.session.stateless.PatientEntitySessionBeanRemote;
import ejb.session.stateless.StaffEntitySessionBeanRemote;
import entity.AppointmentEntity;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import util.exception.InvalidInputException;

/**
 *
 * @author Lenovo
 */
public class AdministrationOperationsModule {

    private PatientEntitySessionBeanRemote patientEntitySessionBean;
    private DoctorEntitySessionBeanRemote doctorEntitySessionBean;
    private StaffEntitySessionBeanRemote staffEntitySessionBean;

    public AdministrationOperationsModule() {

    }

    public AdministrationOperationsModule(PatientEntitySessionBeanRemote patientEntitySessionBean, DoctorEntitySessionBeanRemote doctorEntitySessionBean, StaffEntitySessionBeanRemote staffEntitySessionBean) {
        this.patientEntitySessionBean = patientEntitySessionBean;
        this.doctorEntitySessionBean = doctorEntitySessionBean;
        this.staffEntitySessionBean = staffEntitySessionBean;

    }

    public void menuAdministrationOperation() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** CARS :: Administration Operation ***\n");
            System.out.println("1: Patient Management");
            System.out.println("2: Doctor Management");
            System.out.println("3: Staff Management");
            System.out.println("4: Back\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    patientManagementMenu();
                } else if (response == 2) {
                    doctorManagementMenu();
                } else if (response == 3) {
                    staffManagementMenu();
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

    public void patientManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ***\n");
            System.out.println("1: Add Patient");
            System.out.println("2: View Patient Details");
            System.out.println("3: Update Patient");
            System.out.println("4: Delete Patient");
            System.out.println("5: View All Patients");
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doAddPatient();
                } else if (response == 2) {
                    doViewPatientDetails();
                } else if (response == 3) {
                    doUpdatePatient();
                } else if (response == 4) {
                    doDeletePatient();
                } else if (response == 5) {
                    doViewAllPatients();
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

    public void doctorManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ***\n");
            System.out.println("1: Add Doctor");
            System.out.println("2: View Doctor Details");
            System.out.println("3: Update Doctor");
            System.out.println("4: Delete Doctor");
            System.out.println("5: View All Doctor");
            System.out.println("6: Leave Management");
            System.out.println("7: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doAddDoctor();
                } else if (response == 2) {
                    doViewDoctorDetails();
                } else if (response == 3) {
                    doUpdateDoctor();
                } else if (response == 4) {
                    doDeleteDoctor();
                } else if (response == 5) {
                    doViewAllDoctors();
                } else if (response == 6) {
                    doLeaveManagement();
                } else if (response == 7) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 7) {
                break;
            }
        }
    }

    public void staffManagementMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response;
        while (true) {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ***\n");
            System.out.println("1: Add Staff");
            System.out.println("2: View Staff Details");
            System.out.println("3: Update Staff");
            System.out.println("4: Delete Staff");
            System.out.println("5: View All Staff");
            System.out.println("6: Back\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    doAddStaff();
                } else if (response == 2) {
                    doViewStaffDetails();
                } else if (response == 3) {
                    doUpdateStaff();
                } else if (response == 4) {
                    doDeleteStaff();
                } else if (response == 5) {
                    doViewAllStaff();
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

    private void doAddPatient() {
        Scanner scanner = new Scanner(System.in);
        try {
            PatientEntity newPatient = new PatientEntity();
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Add Patient ***\n");
            System.out.print("Enter Identity number> ");
            newPatient.setIdentityNum(scanner.nextLine().trim());

            System.out.print("Enter password> ");
            newPatient.setPassword(String.valueOf(Integer.valueOf(scanner.nextLine().trim())));
            System.out.print("Enter First Name> ");
            newPatient.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            newPatient.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            newPatient.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            newPatient.setAge(scanner.nextLine().trim());
            System.out.print("Enter Phone Number> ");
            newPatient.setPhoneNumber(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            newPatient.setAddress(scanner.nextLine().trim());

            patientEntitySessionBean.createPatientEntity(newPatient);
            System.out.println("New patient has been added successfully\n");
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
            System.out.println("Patient not added!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewPatientDetails() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View Patient Details ***\n");
            System.out.print("Enter patient identity number to view details> ");
            String id1 = scanner.nextLine().trim();
            System.out.println();
            PatientEntity currPatient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id1);
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Patient ID", "First Name", "Last Name", "Gender", "Age", "Phone", "Address");
            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-30s\n", currPatient.getIdentityNum(), currPatient.getFirstName(), currPatient.getLastName(), currPatient.getGender(), currPatient.getAge().toString(), currPatient.getPhoneNumber(), currPatient.getAddress());
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Could not view patient details!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doUpdatePatient() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Update Patient ***\n");
            System.out.print("Enter patient identity number to update> ");
            String id = scanner.nextLine().trim();
            PatientEntity updating = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id);
            System.out.println("Updating patient with identity number of " + id);
            System.out.print("Enter Password> ");
            updating.setPassword(scanner.nextLine().trim());
            System.out.print("Enter First Name> ");
            updating.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            updating.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            updating.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            updating.setAge(scanner.nextLine().trim());
            System.out.print("Enter Phone Number> ");
            updating.setPhoneNumber(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            updating.setAddress(scanner.nextLine().trim());
            patientEntitySessionBean.updatePatientEntity(updating);
            System.out.println("Patient successfully updated");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Could not update patient!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doDeletePatient() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Delete Patient ***\n");
            System.out.print("Enter identity number of patient to delete> ");
            String id = scanner.nextLine().trim();
            patientEntitySessionBean.deletePatientEntity(id);
            System.out.println("Patient successfully deleted");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Could not delete the patient!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewAllPatients() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View All Patients ***\n");
            List<PatientEntity> patients = patientEntitySessionBean.retrieveAllPatientEntities();

            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Patient ID", "First Name", "Last Name", "Gender", "Age", "Phone", "Address");

            for (PatientEntity patient : patients) {
                System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-30s\n", patient.getIdentityNum(), patient.getFirstName(), patient.getLastName(), patient.getGender(), patient.getAge().toString(), patient.getPhoneNumber(), patient.getAddress());
            }
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println("Could not view all patients!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doAddDoctor() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Add Doctor ***\n");
            DoctorEntity newDoctor = new DoctorEntity();
            System.out.print("Enter Doctor's first name> ");
            newDoctor.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Doctor's last name> ");
            newDoctor.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Doctor's registration number> ");
            newDoctor.setRegistration(scanner.nextLine().trim());
            System.out.print("Enter Doctor's qualification> ");
            newDoctor.setQualification(scanner.nextLine().trim());
            System.out.println();

            Long doctorId = doctorEntitySessionBean.createDoctorEntity(newDoctor);
            System.out.println("Doctor with ID number " + doctorId + " successfully created");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Doctor not added!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewDoctorDetails() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View Doctor Details ***\n");
            System.out.print("Enter doctor Id> ");
            Long id = Long.valueOf("0");
            try {
                id = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Id entered not valid!\nPlease enter a valid doctor id!");
            }
            DoctorEntity doctor = doctorEntitySessionBean.retrieveDoctorEntityById(id);
            System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            System.out.printf("%8s%20s%20s%20s%20s\n", doctor.getDoctorId().toString(), doctor.getFirstName(), doctor.getLastName(), doctor.getRegistration(), doctor.getQualification());
            System.out.println();
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Could not view doctor details!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doUpdateDoctor() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Update Doctor ***\n");
            System.out.print("Enter Id of doctor to update> ");
            Long id4 = Long.valueOf("0");
            try {
                id4 = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Id entered not valid!\nPlease enter a valid doctor id!");
            }
            DoctorEntity toUpdate = doctorEntitySessionBean.retrieveDoctorEntityById(id4);
            DoctorEntity updated = new DoctorEntity();

            updated.setDoctorId(toUpdate.getDoctorId());

            System.out.print("Enter updated Doctor's first name> ");
            updated.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter updated Doctor's last name> ");
            updated.setLastName(scanner.nextLine().trim());
            System.out.print("Enter updated Doctor's registration number> ");
            updated.setRegistration(scanner.nextLine().trim());
            System.out.print("Enter updated Doctor's qualification> ");
            updated.setQualification(scanner.nextLine().trim());
            doctorEntitySessionBean.updateDoctorEntity(updated);
            System.out.println();

            System.out.println("Doctor successfully updated");

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();

        } catch (Exception ex) {
            System.out.println();
            System.err.println(ex.getMessage());
            System.out.println("Doctor not updated!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doDeleteDoctor() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Delete Doctor ***\n");
            System.out.print("Enter identity number of doctor to delete> ");
            Long id = Long.valueOf("0");
            try {
                id = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Id entered not valid!\nPlease enter a valid doctor id!");
            }
            doctorEntitySessionBean.deleteDoctorEntity(id);
            System.out.println();
            System.out.println("Doctor successfully deleted");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Doctor not deleted!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewAllDoctors() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View All Doctors ***\n");
            System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
            List<DoctorEntity> doctors = doctorEntitySessionBean.retrieveAllDoctorEntities();
            for (DoctorEntity pe : doctors) {
                System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", pe.getDoctorId().toString(), pe.getFirstName(), pe.getLastName(), pe.getRegistration(), pe.getQualification());
            }
            System.out.println();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not view all doctors!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();
    }

    private void doLeaveManagement() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Leave Management ***\n");
            System.out.print("Enter doctor id> ");

            Long idLeave = Long.valueOf("0");
            try {
                idLeave = Long.valueOf(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Id entered not valid!\nPlease enter a valid doctor id!");
            }

            System.out.print("Enter date that doctor " + idLeave + " wishes to apply for leaves in the following format YYYY-MM-DD> ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.length() != 10) {
                throw new InvalidInputException("Invalid date input!\nPlease enter a valid date in following format YYYY-MM-DD");
            }
            if (dateInput.charAt(4) != '-' || dateInput.charAt(7) != '-') {
                throw new InvalidInputException("Invalid date entered!\nPlease enter date with format YYYY-MM-DD");
            }

            int year = -1;
            int month = -1;
            int date = -1;
            try {
                year = Integer.valueOf(dateInput.substring(0, 4));
                month = Integer.valueOf(dateInput.substring(5, 7));
                date = Integer.valueOf(dateInput.substring(8, 10));
            } catch (NumberFormatException ex) {
                throw new InvalidInputException("Invalid date input!\nPlease enter a valid date in following format YYYY-MM-DD");
            }

            DoctorEntity doctorToUpdateLeave = doctorEntitySessionBean.retrieveDoctorEntityById(idLeave);
            ArrayList<Date> datesAppliedForLeaves = doctorToUpdateLeave.getDatesAppliedForLeaves();
            //To change
            Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 15, 0, 0);

            Date currentDate = new Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate());
            Date dateToApplyLeave = new Date(year - 1900, month - 1, date);
            Long dayDifference = (dateToApplyLeave.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000);

            //constraints
            // one leave a week and  1 week in advance
            // if doctor has apointments on that day,he cannot apply for leave
            if (dayDifference <= 0) {
                throw new InvalidInputException("Given date has already passed!\nDoctor cannot apply for leave on given date " + dateToApplyLeave);
            } else if (dateToApplyLeave.getDay() == 0 || dateToApplyLeave.getDay() == 6) {
                throw new InvalidInputException("Clinic not open on given date!\nDoctor cannot apply for leave on given date " + dateToApplyLeave);
            } else if (dayDifference < 7) {
                throw new InvalidInputException("Leave is less than a week from today!\nDoctor cannot apply for leave on given date " + dateToApplyLeave);
            } else {
                if (doctorHasAppointmentOnLeaveDate(doctorToUpdateLeave, dateToApplyLeave)) {
                    throw new InvalidInputException("Doctor have appointment on given date!\nDoctor cannot apply for leave on given date " + dateToApplyLeave);
                } else if (doctorToUpdateLeave.getLeaves().contains(dateToApplyLeave)) {
                    throw new InvalidInputException("Already applied for leave on the given date!\nDoctor cannot apply for leave on given date " + dateToApplyLeave + " again");
                } else {
                    for (int i = 0; i < datesAppliedForLeaves.size(); i++) {
                        Date temp = datesAppliedForLeaves.get(i);
                        if (dayDiff(dateToApplyLeave, temp) < 7) {
                            throw new InvalidInputException("Doctor already has a leave on given week!\nDoctor cannot apply for leave on given date " + dateToApplyLeave);
                        }
                    }
                    doctorToUpdateLeave.getLeaves().add(dateToApplyLeave);
                    doctorToUpdateLeave.getDatesAppliedForLeaves().add(dateToApplyLeave);
                    doctorEntitySessionBean.updateDoctorEntity(doctorToUpdateLeave);
                    System.out.println("Leave successfully applied on " + dateToApplyLeave);
                    System.out.println();
                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();
                }
            }

        } catch (Exception ex) {
            System.out.println();
            System.out.println(ex.getMessage());
            System.out.println("Leave not applied!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doAddStaff() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ::  Add Staff ***\n");
            StaffEntity newStaff = new StaffEntity();
            System.out.print("Enter Staff's first name> ");
            newStaff.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Staff's last name> ");
            newStaff.setLastName(scanner.nextLine().trim());
            System.out.print("Enter new username for staff> ");
            newStaff.setUserName(scanner.nextLine().trim());
            System.out.print("Enter new password for staff> ");
            newStaff.setPassword(scanner.nextLine().trim());
            staffEntitySessionBean.createStaffEntity(newStaff);
            System.out.println("Staff successfully created");
            System.out.println();
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (javax.ejb.EJBException ex) {
            System.out.println();
            System.out.println("There already exists a staff record with entered Username!\nPlease try another Username!");
            System.out.println("Patient not added!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Staff not registered!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewStaffDetails() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View Staff Details ***\n");
            System.out.print("Enter staff username to view details> ");
            String id3 = scanner.nextLine().trim();
            StaffEntity staff = staffEntitySessionBean.retrieveStaffEntityByUsername(id3);
            System.out.printf("%-20s%-20s%-20s%-20s\n", "Staff ID", "First Name", "Last Name", "Username");
            System.out.printf("%-20s%-20s%-20s%-20s\n", staff.getStaffId().toString(), staff.getFirstName(), staff.getLastName(), staff.getUserName());
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not view staff details!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doUpdateStaff() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Staff ***\n");
            System.out.print("Enter Username of staff to update> ");
            String id = scanner.nextLine().trim();
            StaffEntity toUpdate = staffEntitySessionBean.retrieveStaffEntityByUsername(id);

            System.out.print("Enter updated Staff's first name> ");
            toUpdate.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter updated Staff's last name> ");
            toUpdate.setLastName(scanner.nextLine().trim());
            System.out.print("Enter updated Staff username> ");
            toUpdate.setUserName(scanner.nextLine().trim());
            System.out.print("Enter updated Staff password> ");
            toUpdate.setPassword(scanner.nextLine().trim());
            staffEntitySessionBean.updateStaffEntity(toUpdate);
            System.out.println();

            System.out.println("Staff successfully updated");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Staff not updated!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doDeleteStaff() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ::  Delete Staff ***\n");
            System.out.print("Enter Username of staff to delete> ");
            String id = scanner.nextLine().trim();
            staffEntitySessionBean.deleteStaffEntity(staffEntitySessionBean.retrieveStaffEntityByUsername(id).getStaffId());
            System.out.println("Staff successfully deleted");
            System.out.println();

            System.out.print("Press any key to continue...> ");
            scanner.nextLine();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Staff not deleted!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private void doViewAllStaff() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View All Staff ***\n");
            List<StaffEntity> staffs = staffEntitySessionBean.retrieveAllStaffEntities();
            System.out.printf("%-20s%-20s%-20s%-20s\n", "Staff ID", "First Name", "Last Name", "Username");

            for (StaffEntity se : staffs) {
                System.out.printf("%-20s%-20s%-20s%-20s\n", se.getStaffId().toString(), se.getFirstName(), se.getLastName(), se.getUserName());
            }
            System.out.println();
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Could not view all staff!");
            System.out.print("Press any key to continue...> ");
            scanner.nextLine();
        }
    }

    private boolean doctorHasAppointmentOnLeaveDate(DoctorEntity doctor, Date date) {
        List<AppointmentEntity> appointmentEntities = doctor.getAppointments();
        for (AppointmentEntity ae : appointmentEntities) {
            if (date.getYear() == ae.getAppointmentTimestamp().getYear()
                    && date.getMonth() == ae.getAppointmentTimestamp().getMonth()
                    && date.getDate() == ae.getAppointmentTimestamp().getDate()) {
                return true;
            }
        }
        return false;
    }

    private Long dayDiff(Date d1, Date d2) {
        return Math.abs((d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000));
    }
}
