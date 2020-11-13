package ro.sd.a2.dto;

import ro.sd.a2.entity.Valute;

/**
 * DTO for creating a new account. Contains the values chose by the user.
 */
public class AccountFormDto {
    private String valute;
    private String type;

    public String getValute() {
        return valute;
    }

    public void setValute(String valute) {
        this.valute = valute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
