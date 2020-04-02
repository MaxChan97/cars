/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Lenovo
 */
@Entity
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String identityNum;
    private String password;
    @Column(length = 255)
    private String firstName;
    @Column(length = 255)
    private String lastName;
    
    //@Enumerated(EnumType.STRING)
    private String gender; // wanna use enum?
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false, length = 30)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    
    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;
    @OneToMany(mappedBy = "patient")
    private List<ConsultationEntity> consultations;

    public PatientEntity() {
    }

    public PatientEntity(String identityNum, String password, String firstName, String lastName, String gender, Integer age, String phoneNumber, String address) {
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

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public List<ConsultationEntity> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<ConsultationEntity> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "entity.PatientEntity[ id=" + identityNum + " ]";
    }
    
}
