package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Integer id;

    @Column
    private String iban;

    @Column
    private Date creationDate;

    @Column
    private Date lastModificationDate;

    @ManyToOne
    @JoinColumn(name = "valute_id")
    private Valute valute;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private float sum;

    @OneToMany(mappedBy = "account", orphanRemoval = true)
    private Set<Transaction> transactions;

    @Column
    private String type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Valute getValute() {
        return valute;
    }

    public void setValute(Valute valute) {
        this.valute = valute;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public abstract String deposit(float sum, int months);
    public abstract String pay(float sum);


}
