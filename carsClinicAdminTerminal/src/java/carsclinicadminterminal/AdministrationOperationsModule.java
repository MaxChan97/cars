/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsclinicadminterminal;

import ejb.session.stateless.DoctorEntitySessionBeanRemote;
import ejb.session.stateless.PatientEntitySessionBeanRemote;
import ejb.session.stateless.StaffEntitySessionBeanRemote;
import entity.DoctorEntity;
import entity.PatientEntity;
import entity.StaffEntity;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

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
                    addPatient();
                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View Patient Details ***\n");
                    System.out.print("Enter patient identity number to view details> ");
                    String id = scanner.nextLine().trim();
                    try {
                        PatientEntity patient = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id);
                        System.out.println(patient);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct id");
                    }
                } else if (response == 3) {
                    updatePatient();
                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Delete Patient ***\n");
                    System.out.println("Enter identity number of patient to delete> ");
                    String id = scanner.nextLine().trim();
                    try {
                        patientEntitySessionBean.deletePatientEntity(id);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the patient!");
                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Patient Management ::  View All Patient ***\n");
                    List<PatientEntity> patients = patientEntitySessionBean.retrieveAllPatientEntities();
                    for (PatientEntity pe : patients) {
                        System.out.println(pe);
                    }
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
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
                    DoctorEntity newDoctor = new DoctorEntity();
                    try {
                        System.out.println("Registering new doctor...");
                        System.out.println("Enter Doctor's id> ");
                        newDoctor.setDoctorId(scanner.nextLong());
                        scanner.nextLine();
                        System.out.println("Enter Doctor's first name> ");
                        newDoctor.setFirstName(scanner.nextLine().trim());
                        System.out.println("Enter Doctor's last name> ");
                        newDoctor.setLastName(scanner.nextLine().trim());
                        System.out.println("Enter Doctor's registration number> ");
                        newDoctor.setRegistration(scanner.nextLine().trim());
                        System.out.println("Enter Doctor's qualification> ");
                        newDoctor.setQualification(scanner.nextLine().trim());
                        doctorEntitySessionBean.createDoctorEntity(newDoctor);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not register doctor, please key in the corret input!");
                    }

                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View Doctor Details ***\n");
                    System.out.print("Enter doctor id> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        DoctorEntity doctor = doctorEntitySessionBean.retrieveDoctorEntityById(id);
                        System.out.println(doctor);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct doctor id!");
                    }
                } else if (response == 3) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management :: Update Doctor ***\n");
                    System.out.print("Enter id of doctor to update> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        DoctorEntity toUpdate = doctorEntitySessionBean.retrieveDoctorEntityById(id);
                        DoctorEntity updated = new DoctorEntity();

                        updated.setDoctorId(toUpdate.getDoctorId());

                        System.out.println("Enter updated Doctor's first name> ");
                        updated.setFirstName(scanner.nextLine().trim());
                        System.out.println("Enter updated Doctor's last name> ");
                        updated.setLastName(scanner.nextLine().trim());
                        System.out.println("Enter updated Doctor's registration number> ");
                        updated.setRegistration(scanner.nextLine().trim());
                        System.out.println("Enter updated Doctor's qualification> ");
                        updated.setQualification(scanner.nextLine().trim());
                        doctorEntitySessionBean.updateDoctorEntity(updated);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Unable to update doctor!");
                    }

                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Delete Doctor ***\n");
                    System.out.println("Enter identity number of patient to delete> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        doctorEntitySessionBean.deleteDoctorEntity(id);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the doctor!");
                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  View All Doctor ***\n");
                    List<DoctorEntity> doctors = doctorEntitySessionBean.retrieveAllDoctorEntities();
                    for (DoctorEntity pe : doctors) {
                        System.out.println(pe);
                    }
                } else if (response == 6) {
                    System.out.println("*** CARS :: Administration Operation :: Doctor Management ::  Leave Management ***\n");
                    System.out.print("Enter doctor id> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    System.out.println("Enter date that doctor " + id + " wishes to apply for leaves in the following format YYYY-MM-DD>");
                    scanner.useDelimiter("-");
                    int year = scanner.nextInt();
                    int month = scanner.nextInt() - 1;
                    int dateEntered = scanner.nextInt();

                    try {
                        DoctorEntity doctorToUpdateLeave = doctorEntitySessionBean.retrieveDoctorEntityById(id);
                        Date date = new Date(year, month, dateEntered);
                        doctorToUpdateLeave.getLeaves().add(date);
                        doctorEntitySessionBean.updateDoctorEntity(doctorToUpdateLeave);
                        System.out.println("Leave successfully updated");

                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());

                    }
                } else if (response == 7) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
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
                    StaffEntity newStaff = new StaffEntity();
                    try {
                        System.out.println("Registering new staff...");
                        System.out.println("Enter Staff id> ");
                        newStaff.setStaffId(scanner.nextLong());
                        scanner.nextLine();
                        System.out.println("Enter Staff's first name> ");
                        newStaff.setFirstName(scanner.nextLine().trim());
                        System.out.println("Enter Staff's last name> ");
                        newStaff.setLastName(scanner.nextLine().trim());
                        System.out.println("Enter new username for staff> ");
                        newStaff.setUserName(scanner.nextLine().trim());
                        System.out.println("Enter new password for staff> ");
                        newStaff.setPassword(scanner.nextLine().trim());
                        staffEntitySessionBean.createStaffEntity(newStaff);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not register staff, please key in the corret input!");
                    }

                } else if (response == 2) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View Staff Details ***\n");
                    System.out.print("Enter staff id to view details> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        StaffEntity staff = staffEntitySessionBean.retrieveStaffEntityById(id);
                        System.out.println(staff);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Please key in the correct id");
                    }
                } else if (response == 3) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management :: Update Staff ***\n");
                    System.out.print("Enter id of staff to update> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        StaffEntity toUpdate = staffEntitySessionBean.retrieveStaffEntityById(id);
                        StaffEntity updated = new StaffEntity();

                        updated.setStaffId(toUpdate.getStaffId());

                        System.out.println("Enter updated Staff's first name> ");
                        updated.setFirstName(scanner.nextLine().trim());
                        System.out.println("Enter updated Staff's last name> ");
                        updated.setLastName(scanner.nextLine().trim());
                        System.out.println("Enter updated Staff username> ");
                        updated.setUserName(scanner.nextLine().trim());
                        System.out.println("Enter updated Staff password> ");
                        updated.setPassword(scanner.nextLine().trim());
                        staffEntitySessionBean.updateStaffEntity(updated);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Unable to update staff!");
                    }

                } else if (response == 4) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  Delete Staff ***\n");
                    System.out.println("Enter id of staff to delete> ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();
                    try {
                        staffEntitySessionBean.deleteStaffEntity(id);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                        System.out.println("Could not delete the patient!");
                    }
                } else if (response == 5) {
                    System.out.println("*** CARS :: Administration Operation :: Staff Management ::  View All Staff ***\n");
                    List<StaffEntity> staffs = staffEntitySessionBean.retrieveAllStaffEntities();
                    for (StaffEntity pe : staffs) {
                        System.out.println(pe);
                    }
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

        }
    }

    public void addPatient() {
        Scanner scanner = new Scanner(System.in);
        try {
            PatientEntity newPatient = new PatientEntity();
            System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Add Patient ***\n");
            System.out.print("Enter Identity number> ");
            newPatient.setIdentityNum(scanner.nextLine().trim());
            System.out.print("Enter password> ");
            newPatient.setPassword(scanner.nextLine().trim());
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
            System.out.println("New patient has been added successfully");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Patient not added !");
        }
    }

    public void updatePatient() { // is this merged with the persistence context? 
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** CARS :: Administration Operation :: Patient Management ::  Update Patient Details ***\n");
        System.out.print("Enter patient identity number to update> ");
        String id = scanner.nextLine().trim();
        try {
            PatientEntity updating = patientEntitySessionBean.retrievePatientEntityByIdentityNum(id);
            PatientEntity toUpdate = new PatientEntity();
            System.out.println("Updating patient with identity number of " + id);
            System.out.print("Enter Identity number> ");
            toUpdate.setIdentityNum(scanner.nextLine().trim());
            System.out.print("Enter password> ");
            toUpdate.setPassword(scanner.nextLine().trim());
            System.out.print("Enter First Name> ");
            toUpdate.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            toUpdate.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            toUpdate.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            toUpdate.setAge(new Integer(scanner.nextLine().trim()));// if users inputs a alphabet?
            System.out.print("Enter Phone Number> ");
            toUpdate.setPhoneNumber(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            toUpdate.setAddress(scanner.nextLine().trim());
            toUpdate.setPassword(updating.getPassword());
            patientEntitySessionBean.updatePatientEntity(toUpdate);//for merging with the persistence context,better to use .merge()?

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println("Please key in the correct id!");
        }
    }

}
