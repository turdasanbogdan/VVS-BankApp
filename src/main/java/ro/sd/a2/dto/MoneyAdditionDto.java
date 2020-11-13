package ro.sd.a2.dto;

/**
 * DTO for adding money to the user account. The user will fill these fields to put money in his account.
 */
public class MoneyAdditionDto {
    private int numberOfMonths;
    private float sum;

    public int getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(int numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
