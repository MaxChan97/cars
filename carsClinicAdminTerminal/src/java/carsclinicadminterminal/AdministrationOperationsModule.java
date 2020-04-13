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
                    try {
                        PatientEntity newPatient = new PatientEntity();
                        System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Add Patient ***\n");
                        scanner.nextLine();
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
                        newPatient.setAge(new Integer(scanner.nextLine().trim()));// if users inputs a alphabet?
                        System.out.print("Enter Phone Number> ");
                        newPatient.setPhoneNumber(scanner.nextLine().trim());
                        System.out.print("Enter Address> ");
                        newPatient.setAddress(scanner.nextLine().trim());

                        patientEntitySessionBean.createPatientEntity(newPatient);
                        System.out.println("New patient has been added successfully\n");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (NumberFormatException ex) {
                        System.out.println("password must be numeric!");
                        System.out.println("Patient not registered!\n");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Patient not added !\n");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View Patient Details ***\n");
                    scanner.nextLine();
                    System.out.print("Enter patient identity number to view details> ");

                    try {
                        String id1 = scanner.nextLine().trim();
                        System.out.println();
                        PatientEntity currPatient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id1);
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Patient ID", "First Name", "Last Name", "Gender", "Age", "Phone", "Address");
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-30s\n", currPatient.getIdentityNum(), currPatient.getFirstName(), currPatient.getLastName(), currPatient.getGender(), currPatient.getAge().toString(), currPatient.getPhoneNumber(), currPatient.getAddress());
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct id");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }
                } else if (response == 3) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Update Patient Details ***\n");
                    scanner.nextLine();
                    System.out.print("Enter patient identity number to update> ");
                    String id = scanner.nextLine().trim();
                    try {
                        PatientEntity updating = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id);
                        PatientEntity toUpdate = new PatientEntity();
                        System.out.println("Updating patient with identity number of " + id);
                        System.out.print("Enter Identity Number> ");
                        toUpdate.setIdentityNum(scanner.nextLine().trim());
                        System.out.print("Enter Password> ");
                        toUpdate.setPassword(scanner.nextLine().trim());
                        System.out.print("Enter First Name> ");
                        toUpdate.setFirstName(scanner.nextLine().trim());
                        System.out.print("Enter Last Name> ");
                        toUpdate.setLastName(scanner.nextLine().trim());
                        System.out.print("Enter Gender> ");
                        toUpdate.setGender(scanner.nextLine().trim());
                        System.out.print("Enter Age> ");
                        toUpdate.setAge(new Integer(scanner.nextLine().trim()));
                        System.out.print("Enter Phone Number> ");
                        toUpdate.setPhoneNumber(scanner.nextLine().trim());
                        System.out.print("Enter Address> ");
                        toUpdate.setAddress(scanner.nextLine().trim());
                        toUpdate.setPassword(updating.getPassword());
                        patientEntitySessionBean.updatePatientEntity(toUpdate);
                        System.out.println("Patient successfully updated");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();

                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct id!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Delete Patient ***\n");
                    scanner.nextLine();
                    System.out.print("Enter identity number of patient to delete> ");
                    String id = scanner.nextLine().trim();
                    try {
                        patientEntitySessionBean.deletePatientEntity(id);
                        System.out.println("Patient successfully deleted");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the patient!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View All Patient ***\n");
                    scanner.nextLine();
                    List<PatientEntity> patients = patientEntitySessionBean.retrieveAllPatientEntities();

                    System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", "Patient ID", "First Name", "Last Name", "Gender", "Age", "Phone", "Address");

                    for (PatientEntity patient : patients) {

                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-30s\n", patient.getIdentityNum(), patient.getFirstName(), patient.getLastName(), patient.getGender(), patient.getAge().toString(), patient.getPhoneNumber(), patient.getAddress());
                    }
                    System.out.println();

                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();

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
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Add Doctor ***\n");
                    scanner.nextLine();
                    DoctorEntity newDoctor = new DoctorEntity();
                    try {
                        /*
                        System.out.print("Enter Doctor's id> ");
                        newDoctor.setDoctorId(Long.valueOf(scanner.nextLine()));
                         */
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
                        System.err.println(ex.getMessage());
                        System.out.println("Could not register doctor, please key in the corret input!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View Doctor Details ***\n");
                    scanner.nextLine();
                    System.out.print("Enter doctor id> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        DoctorEntity doctor = doctorEntitySessionBean.retrieveDoctorEntityById(id);
                        System.out.printf("%8s%20s%20s%20s%20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
                        System.out.printf("%8s%20s%20s%20s%20s\n", doctor.getDoctorId().toString(), doctor.getFirstName(), doctor.getLastName(), doctor.getRegistration(), doctor.getQualification());
                        System.out.println();
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.out.println();
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct doctor id!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }
                } else if (response == 3) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Update Doctor ***\n");
                    scanner.nextLine();
                    System.out.print("Enter id of doctor to update> ");
                    Long id4 = scanner.nextLong();
                    scanner.nextLine();
                    try {
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
                        System.out.println("Unable to update doctor!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Delete Doctor ***\n");
                    scanner.nextLine();
                    System.out.print("Enter identity number of doctor to delete> ");
                    Long id = Long.valueOf(scanner.nextLine().trim());
                    try {
                        doctorEntitySessionBean.deleteDoctorEntity(id);
                        System.out.println();
                        System.out.println("Doctor successfully deleted");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the doctor!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View All Doctor ***\n");
                    scanner.nextLine();

                    System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", "Doctor ID", "First Name", "Last Name", "Registration", "Qualification");
                    List<DoctorEntity> doctors = doctorEntitySessionBean.retrieveAllDoctorEntities();
                    for (DoctorEntity pe : doctors) {
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s\n", pe.getDoctorId().toString(), pe.getFirstName(), pe.getLastName(), pe.getRegistration(), pe.getQualification());
                    }
                    System.out.println();

                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();
                } else if (response == 6) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Leave Management ***\n");
                    scanner.nextLine();
                    System.out.print("Enter doctor id> ");
                    Long idLeave = Long.valueOf(scanner.nextLine().trim());

                    System.out.print("Enter date that doctor " + idLeave + " wishes to apply for leaves in the following format YYYY-MM-DD> ");

                    String dateInput = scanner.nextLine().trim();

                    int year = Integer.valueOf(dateInput.substring(0, 4));
                    int month = Integer.valueOf(dateInput.substring(5, 7));
                    int date = Integer.valueOf(dateInput.substring(8, 10));

                    try {
                        DoctorEntity doctorToUpdateLeave = doctorEntitySessionBean.retrieveDoctorEntityById(idLeave);
                        boolean canApply;
                        ArrayList<Date> datesAppliedForLeaves = doctorToUpdateLeave.getDatesAppliedForLeaves();
                        //To change
                        Timestamp currentTimestamp = new Timestamp(2020 - 1900, 4 - 1, 13, 16, 15, 0, 0);

                        Date currentDate = new Date(currentTimestamp.getYear(), currentTimestamp.getMonth(), currentTimestamp.getDate());
                        //Date currentDate = new Date(currentTimestamp.getYear()-1900,currentTimestamp.getMonth()-1,currentTimestamp.getDate());
                        Date dateToApplyLeave = new Date(year - 1900, month - 1, date);
                        Long dayDifference = (dateToApplyLeave.getTime() - currentDate.getTime()) / (24 * 60 * 60 * 1000);

                        if (dateToApplyLeave.getDay() == 0 || dateToApplyLeave.getDay() == 6) {
                            throw new InvalidInputException("Clinic not open on given date!");
                        }

                        if (datesAppliedForLeaves.isEmpty()) {//never apply for any leaves
                            canApply = true;
                        } else {
                            for (int i = 0; i < datesAppliedForLeaves.size(); i++) {
                                Date temp = datesAppliedForLeaves.get(i);
                                if (dayDiff(currentDate, temp) < 7) {
                                    canApply = false;
                                    throw new IllegalArgumentException("Doctor will have to wait " + (7 - dayDiff(currentDate, temp)) + " days to apply for a leave");
                                } else {
                                    canApply = true;
                                    break;
                                }
                            }
                            canApply = false;
                        }

                        //constraints
                        // one leave a week and  1 week in advance
                        // if doctor has apointments on that day,he cannot apply for leave
                        if (dayDifference <= 0) {
                            throw new IllegalArgumentException("Invalid date entered! Given date has already passed!");
                        } else if (dayDifference < 7) {
                            throw new IllegalArgumentException("Leave is less than a week from today. Doctor cannot apply for leave on given date " + dateToApplyLeave);
                        } else {
                            if (doctorHasAppointmentOnLeaveDate(doctorToUpdateLeave, dateToApplyLeave)) {
                                throw new IllegalArgumentException("Doctor have appointment on given date!");
                            } else if (doctorToUpdateLeave.getLeaves().contains(dateToApplyLeave)) {
                                throw new IllegalArgumentException("Already applied for leave on the given date ");
                            } else if (canApply) {
                                doctorToUpdateLeave.getLeaves().add(dateToApplyLeave);
                                doctorToUpdateLeave.getDatesAppliedForLeaves().add(currentDate);
                                doctorEntitySessionBean.updateDoctorEntity(doctorToUpdateLeave);
                                System.out.println("Leave successfully applied on " + dateToApplyLeave + " on " + currentDate);
                                System.out.println();
                                System.out.print("Press any key to continue...> ");
                                scanner.nextLine();
                            } else {
                                throw new InvalidInputException("Leave dates has to be at least one week apart!");
                            }
                        }
                     }catch (NumberFormatException ex) {
                            System.out.println("Invalid date/time inputted!");
                            System.out.print("Press any key to continue...> ");
                            scanner.nextLine();
                    } catch (Exception ex) {
                        System.out.println("Leave not applied!");
                        System.out.println(ex.getMessage());
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

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
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  Add Staff ***\n");
                    scanner.nextLine();
                    StaffEntity newStaff = new StaffEntity();
                    try {
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
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not register staff, please key in the correct input!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View Staff Details ***\n");
                    scanner.nextLine();
                    System.out.print("Enter staff username to view details> ");
                    String id3 = scanner.nextLine().trim();
                    try {
                        StaffEntity staff = staffEntitySessionBean.retrieveStaffEntityByUsername(id3);
                        System.out.printf("%-20s%-20s%-20s%-20s\n", "Staff ID", "First Name", "Last Name", "Username");
                        System.out.printf("%-20s%-20s%-20s%-20s\n", staff.getStaffId().toString(), staff.getFirstName(), staff.getLastName(), staff.getUserName());
                        System.out.println();

                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct id");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }
                } else if (response == 3) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Staff ***\n");
                    scanner.nextLine();
                    System.out.print("Enter Username of staff to update> ");
                    String id = scanner.nextLine().trim();

                    try {
                        StaffEntity toUpdate = staffEntitySessionBean.retrieveStaffEntityByUsername(id);
                        StaffEntity updated = new StaffEntity();

                        updated.setStaffId(toUpdate.getStaffId());

                        System.out.print("Enter updated Staff's first name> ");
                        updated.setFirstName(scanner.nextLine().trim());
                        System.out.print("Enter updated Staff's last name> ");
                        updated.setLastName(scanner.nextLine().trim());
                        System.out.print("Enter updated Staff username> ");
                        updated.setUserName(scanner.nextLine().trim());
                        System.out.print("Enter updated Staff password> ");
                        updated.setPassword(scanner.nextLine().trim());
                        staffEntitySessionBean.updateStaffEntity(updated);
                        System.out.println();

                        System.out.println("Staff successfully updated");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();

                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Unable to update staff!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();
                    }

                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  Delete Staff ***\n");
                    scanner.nextLine();
                    System.out.print("Enter Username of staff to delete> ");
                    String id = scanner.nextLine().trim();

                    try {
                        staffEntitySessionBean.deleteStaffEntity(staffEntitySessionBean.retrieveStaffEntityByUsername(id).getStaffId());
                        System.out.println("Staff successfully deleted");
                        System.out.println();

                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();

                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the patient!");
                        System.out.print("Press any key to continue...> ");
                        scanner.nextLine();

                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View All Staff ***\n");
                    scanner.nextLine();
                    List<StaffEntity> staffs = staffEntitySessionBean.retrieveAllStaffEntities();
                    System.out.printf("%-20s%-20s%-20s%-20s\n", "Staff ID", "First Name", "Last Name", "Username");

                    for (StaffEntity se : staffs) {
                        System.out.printf("%-20s%-20s%-20s%-20s\n", se.getStaffId().toString(), se.getFirstName(), se.getLastName(), se.getUserName());
                    }
                    System.out.println();

                    System.out.print("Press any key to continue...> ");
                    scanner.nextLine();
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
        return (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
    }
}
