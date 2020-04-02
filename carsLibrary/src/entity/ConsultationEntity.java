/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Lenovo
 */
@Entity
public class ConsultationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;
    private Integer queueNumber;
    private Date consultationDate;
    private Time consulatationTime;
    private Integer duration;
    
    @ManyToOne
    private PatientEntity patient;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AppointmentEntity appointment;

    public ConsultationEntity() {
    }

    public ConsultationEntity(Integer queueNumber, Date consultationDate, Time consulatationTime, Integer duration) {
        this();
        
        this.queueNumber = queueNumber;
        this.consultationDate = consultationDate;
        this.consulatationTime = consulatationTime;
        this.duration = duration;
    }

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultationId != null ? consultationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsultationEntity)) {
            return false;
        }
        ConsultationEntity other = (ConsultationEntity) object;
        if ((this.consultationId == null && other.consultationId != null) || (this.consultationId != null && !this.consultationId.equals(other.consultationId))) {
            return false;
        }
        return true;
    }

    public Integer getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Integer queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Date getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(Date consultationDate) {
        this.consultationDate = consultationDate;
    }

    public Time getConsulatationTime() {
        return consulatationTime;
    }

    public void setConsulatationTime(Time consulatationTime) {
        this.consulatationTime = consulatationTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }
    

    @Override
    public String toString() {
        return "entity.ConsultationEntity[ id=" + consultationId + " ]";
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
    
}
