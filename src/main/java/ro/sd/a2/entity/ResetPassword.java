package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "resetPassword")
public class ResetPassword {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Date expirationDate;

    public ResetPassword()
    {

    }

    public ResetPassword(User user) {
        this.id = UUID.randomUUID().toString();
        this.user = user;

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        this.expirationDate = new Date(t + (5*60000));
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
