package ro.sd.a2.dto;

/**
 * DTO with the IDs of the bill and the account iban. Necesar for the user to pay a bill.
 */
public class PaymentDetailsDto {
    String billId;
    String accountIban;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }
}
