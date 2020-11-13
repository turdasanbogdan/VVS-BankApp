package ro.sd.a2.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO for user login.
 */
public class UserLoginDto {
    @NotNull(message = "Can't be null")
    String email;
    @NotNull(message = "Can't be null")
    String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
