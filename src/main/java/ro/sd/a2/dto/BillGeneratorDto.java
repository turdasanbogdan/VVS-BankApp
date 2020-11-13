package ro.sd.a2.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO to generate the bills. The admin will chose the fields that are specific for the bills to be generated.
 */
public class BillGeneratorDto {
    @NotNull
    private String username;

    @NotNull
    private int value;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
