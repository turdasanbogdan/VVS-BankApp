package ro.sd.a2.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlInlineBinaryData;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private Date paymentDate;

    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Column
    private float sum;

    public Transaction(){
        this.id = UUID.randomUUID().toString();
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
