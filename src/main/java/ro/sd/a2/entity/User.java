package ro.sd.a2.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String address;

    @Column
    private String username;

    @Column
    private Date registrationDate;

    @Column
    private String email;

    @Column
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Account> accounts;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Bill> bills;

    public User() {
        this.id = UUID.randomUUID().toString();
        this.registrationDate = new Date();

    }

    public int getAge()
    {
        Period diff=Period.between(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return diff.getYears();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + username + '\'' +
                '}';
    }
}
