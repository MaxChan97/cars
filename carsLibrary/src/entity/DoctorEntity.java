/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.CascadeType;
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
public class DoctorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String registration;
    @Column(nullable = false)
    private String qualification;
    @Column(nullable = false)
    private HashSet<Timestamp> notAvail;
    @Column(nullable = false)
    private HashSet<Date> leaves;
    
    @Column(nullable = false)
    private ArrayList<Date> datesAppliedForLeaves;
    
    @OneToMany(mappedBy = "doctor",cascade= CascadeType.REMOVE,orphanRemoval=true)
    private List<AppointmentEntity> appointments;
    

    public DoctorEntity() {
        this.appointments = new ArrayList<AppointmentEntity>();
        this.notAvail = new HashSet<>();
        this.leaves = new HashSet<>();
        datesAppliedForLeaves =  new ArrayList<>();
    }

    public DoctorEntity(String firstName, String lastName, String registration, String qualification) {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.registration = registration;
        this.qualification = qualification;
    }
    
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctorId != null ? doctorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the doctorId fields are not set
        if (!(object instanceof DoctorEntity)) {
            return false;
        }
        DoctorEntity other = (DoctorEntity) object;
        if ((this.doctorId == null && other.doctorId != null) || (this.doctorId != null && !this.doctorId.equals(other.doctorId))) {
            return false;
        }
        return true;
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

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public HashSet<Timestamp> getNotAvail() {
        return notAvail;
    }

    public void setNotAvail(HashSet<Timestamp> notAvail) {
        this.notAvail = notAvail;
    }

    public HashSet<Date> getLeaves() {
        return leaves;
    }

    public void setLeaves(HashSet<Date> leaves) {
        this.leaves = leaves;
    }

    public List<AppointmentEntity> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentEntity> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Date> getDatesAppliedForLeaves() {
        return datesAppliedForLeaves;
    }

    public void setDatesAppliedForLeaves(ArrayList<Date> datesAppliedForLeaves) {
        this.datesAppliedForLeaves = datesAppliedForLeaves;
    }

    @Override
    public String toString() {
        return "Doctor[ id=" + doctorId + " first name =" + this.firstName + " last name=" + this.lastName + " registration number =" + this.registration + " qualification=" + this.qualification +" ]";
    }
    
}
