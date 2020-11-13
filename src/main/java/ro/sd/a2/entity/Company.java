package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "company")
public class Company {
    @Id
    private String id;

    @Column
    private String name;


    @OneToMany(mappedBy = "company", orphanRemoval = true)
    private Set<Bill> bills;

    @ManyToOne
    @JoinColumn(name = "valute_id")
    private Valute valute;

    public Company()
    {
        this.id = UUID.randomUUID().toString();
    }

    public Valute getValute() {
        return valute;
    }

    public void setValute(Valute valute) {
        this.valute = valute;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
