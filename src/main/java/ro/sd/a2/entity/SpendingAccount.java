package ro.sd.a2.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class SpendingAccount extends Account {

    @Override
    public String deposit(float sum, int months) {
        return this.deposit(sum);
    }

    public String deposit(float sum)
    {
        this.setLastModificationDate(new Date());
        this.setSum(this.getSum() + sum);
        return "OK";
    }

    @Override
    public String pay(float sum) {
        float currentSum = this.getSum();
        sum=sum+3.0f/100.0f*sum;
        currentSum-=sum;
        if(currentSum>=-2000) {this.setLastModificationDate(new Date()); this.setSum(currentSum); return "OK";}
        else return "Not OK";
    }

}
