package ro.sd.a2.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * DTO for the user to change his password.
 */
public class PasswordFormDto {
    @NotEmpty
    @Min(value = 8, message = "Minimum 8 characters")
    private String password;
    @NotEmpty
    @Min(value = 8, message = "Minimum 8 characters")
    private String passwordConfirmation;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
