package ro.sd.a2.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class SavingAccount extends Account {

    @Override
    public String deposit(float sum, int months) {
        this.setSum(this.getSum() + months * sum / 10.0f + sum);
        this.setLastModificationDate(new Date());
        return "OK";
    }

    @Override
    public String pay(float sum) {
        float currentSum = this.getSum();
        currentSum-=sum;
        if(currentSum>=0) {this.setLastModificationDate(new Date()); this.setSum(currentSum); return "OK";}
        else return "Not OK";
    }
}
