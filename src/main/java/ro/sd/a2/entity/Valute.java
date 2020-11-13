package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "valute")
public class Valute {
    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String symbol;

    @OneToMany(mappedBy = "valute", orphanRemoval = true)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "valute")
    private Set<Company> company;


    public Valute()
    {
        this.id = UUID.randomUUID().toString();
    }

    public Set<Company> getCompany() {
        return company;
    }

    public void setCompany(Set<Company> company) {
        this.company = company;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "name='" + name + '\'' +
                '}';
    }
}
