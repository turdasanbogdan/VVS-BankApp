package ro.sd.a2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * DTO for user details. Used for /home profile messages.
 */
public class UserDetailsDto {
    private String address;
    private String username;
    private String email;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateOfBirth;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date registrationDate;
    private String firstName;
    private String lastName;


    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
}
