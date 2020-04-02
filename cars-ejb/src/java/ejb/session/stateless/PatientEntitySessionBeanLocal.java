/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.PatientEntity;



public interface PatientEntitySessionBeanLocal {
    public String createPatientEntity(PatientEntity patientEntity);
    public PatientEntity retrievePatientEntityById(Long id);
    public void updatePatientEntity (PatientEntity patientEntity);
    public void deletePatientEntity (Long id);
    
}
