/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import util.exception.InvalidInputException;

/**
 *
 * @author Lenovo
 */
@Entity
public class StaffEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    @Column(length = 255, nullable = false)
    private String firstName;
    @Column (length =255, nullable= false)
    private String lastName;
    @Column (nullable = false, unique = true)
    private String userName;
    @Column (nullable = false)
    private String password;

    public StaffEntity() {
    }

    public StaffEntity(String firstName, String lastName, String userName, String password) {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffId != null ? staffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the staffId fields are not set
        if (!(object instanceof StaffEntity)) {
            return false;
        }
        StaffEntity other = (StaffEntity) object;
        if ((this.staffId == null && other.staffId != null) || (this.staffId != null && !this.staffId.equals(other.staffId))) {
            return false;
        }
        return true;
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
    
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidInputException {
        if (password.length() < 6) {
            throw new InvalidInputException("Password must be at least 6 characters long!");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "StaffEntity[ id=" + staffId + " name= " + this.firstName + " " + this.lastName +" ]";
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) throws InvalidInputException {
        if (userName.equals("")) {
            throw new InvalidInputException("Username cannot be empty!");
        }
        this.userName = userName;
    }
    
}
