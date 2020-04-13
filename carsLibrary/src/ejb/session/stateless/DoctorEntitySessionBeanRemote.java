/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DoctorEntity;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.Remote;
import util.exception.DoctorNotFoundException;
import util.exception.InvalidInputException;

/**
 *
 * @author Lenovo
 */
@Remote
public interface DoctorEntitySessionBeanRemote {

    public long createDoctorEntity(DoctorEntity doctorEntity);

    public List<DoctorEntity> retrieveAllDoctorEntities();

    public DoctorEntity retrieveDoctorEntityById(Long id) throws DoctorNotFoundException;

    public void updateDoctorEntity(DoctorEntity doctorEntity) throws DoctorNotFoundException, InvalidInputException;

    public void deleteDoctorEntity(Long id) throws DoctorNotFoundException, SQLIntegrityConstraintViolationException;
}
