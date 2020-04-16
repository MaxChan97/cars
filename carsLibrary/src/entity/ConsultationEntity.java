/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@XmlRootElement
public class ConsultationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Integer queueNumberGenerator = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultationId;
    @Column(nullable = false)
    private Integer queueNumber;
    @Column(nullable = false)
    private Integer duration;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AppointmentEntity appointment;

    public ConsultationEntity() {
    }

    public ConsultationEntity(Integer duration) {
        this();

        this.queueNumber = ++queueNumberGenerator;
        this.duration = duration;
    }

    public static void resetQueueNumberGenerator() {
        queueNumberGenerator = 0;
    }
    
    public static void incrementQueueNumber() {
        queueNumberGenerator++;
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
}
