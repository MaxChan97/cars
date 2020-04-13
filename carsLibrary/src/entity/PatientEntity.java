/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import util.exception.InvalidInputException;

/**
 *
 * @author Lenovo
 */
@Entity
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique = true, nullable = false, length = 9)
    private String identityNum;

    @Column(nullable = false, length = 6)
    private String password;
    @Column(nullable = false, length = 255)
    private String firstName;
    @Column(nullable = false, length = 255)
    private String lastName;
    @Column(nullable = false, length = 1)
    private String gender;
    @Column(nullable = false)
    private String age;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;

    public PatientEntity() {
        this.appointments = new ArrayList<AppointmentEntity>();
    }

    public PatientEntity(String identityNum, String password, String firstName, String lastName, String gender, String age, String phoneNumber, String address) {
        this();

        this.identityNum = identityNum;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identityNum != null ? identityNum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientEntity)) {
            return false;
        }
        PatientEntity other = (PatientEntity) object;
        if ((this.identityNum == null && other.identityNum != null) || (this.identityNum != null && !this.identityNum.equals(other.identityNum))) {
            return false;
        }
        return true;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setIdentityNum(String identityNum) throws InvalidInputException {
        Character firstChar = identityNum.charAt(0);
        Character lastChar = identityNum.charAt(8);
        if (identityNum.length() != 9 || !Character.isUpperCase(firstChar) || !Character.isUpperCase(lastChar)) {
            throw new InvalidInputException("Invalid identity number, please input valid NRIC/Passport number");
        }
        try {
            int checkIdentityNum = Integer.valueOf(identityNum.substring(1, 8));
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Invalid identity number, please input valid NRIC/Passport number");
        }

        this.identityNum = identityNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidInputException {
        if (password.length() != 6) {
            throw new InvalidInputException("Password must be 6 digits long!");
        }
        try {
            int checkPassword = Integer.valueOf(password);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Password must numeric!");
        }
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidInputException {
        if (firstName.equals("") || !Character.isUpperCase(firstName.charAt(0))) {
            throw new InvalidInputException("Invalid first name input, first names must not be empty and first names must start with an uppercase character!");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidInputException {
        if (lastName.equals("") || !Character.isUpperCase(lastName.charAt(0))) {
            throw new InvalidInputException("Invalid last name input, last names must not be empty and last names must start with an uppercase character!");
        }
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setGender(String gender) throws InvalidInputException {
        if (gender.equals("M") || gender.equals("m") || gender.equals("male") || gender.equals("Male")) {
            this.gender = "M";
        } else if (gender.equals("F") || gender.equals("f") || gender.equals("female") || gender.equals("Female")) {
            this.gender = "F";
        } else {
            throw new InvalidInputException("Gender entered is not valid!");
        }
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws InvalidInputException {
        try {
            long checkAge = Long.valueOf(age);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Age entered is not valid, please enter a number!");
        }
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws InvalidInputException {
        try {
            long checkPhoneNumber = Long.valueOf(phoneNumber);
        } catch (NumberFormatException ex) {
            throw new InvalidInputException("Phone Number entered is not valid, please enter phone number without area or country code!");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Patient[ identity number =" + identityNum + "name =  " + this.firstName + " " + this.lastName + " gender= " + this.gender + " age= " + this.age + " phone =" + this.phoneNumber + "address =" + this.address + " ]";
    }

}
