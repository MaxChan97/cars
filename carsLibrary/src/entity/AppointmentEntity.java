/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Max
 */
@Entity
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    private Timestamp appointmentTimestamp;
    
    @ManyToOne
    private PatientEntity patient;
    @ManyToOne
    private DoctorEntity doctor;
    @OneToOne(mappedBy="appointment")
    private ConsultationEntity consultation;
    

    public AppointmentEntity() {
    }

    public AppointmentEntity(Timestamp appointmentTimeStamp) {
        this();
        
        this.appointmentTimestamp = appointmentTimeStamp;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentId != null ? appointmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the appointmentId fields are not set
        if (!(object instanceof AppointmentEntity)) {
            return false;
        }
        AppointmentEntity other = (AppointmentEntity) object;
        if ((this.appointmentId == null && other.appointmentId != null) || (this.appointmentId != null && !this.appointmentId.equals(other.appointmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.AppointmentEntity[ id=" + appointmentId + " ]";
    }

    /**
     * @return the patient
     */
    public PatientEntity getPatient() {
        return patient;
    }

    /**
     * @param patient the patient to set
     */
    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }

    /**
     * @return the doctor
     */
    public DoctorEntity getDoctor() {
        return doctor;
    }

    /**
     * @param doctor the doctor to set
     */
    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    /**
     * @return the consultation
     */
    public ConsultationEntity getConsultation() {
        return consultation;
    }

    /**
     * @param consultation the consultation to set
     */
    public void setConsultation(ConsultationEntity consultation) {
        this.consultation = consultation;
    }

    /**
     * @return the appointmentTimestamp
     */
    public Timestamp getAppointmentTimestamp() {
        return appointmentTimestamp;
    }

    /**
     * @param appointmentTimestamp the appointmentTimestamp to set
     */
    public void setAppointmentTimestamp(Timestamp appointmentTimestamp) {
        this.appointmentTimestamp = appointmentTimestamp;
    }
    
    
    
}
