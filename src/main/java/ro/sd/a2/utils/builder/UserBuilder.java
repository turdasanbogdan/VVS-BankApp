package ro.sd.a2.utils.builder;

import ro.sd.a2.entity.User;

import java.util.Date;
import java.util.UUID;

/**
 * Builder class used in orded to create a new User. It has a user instance. It has method to construct this user, part by part.
 * By calling the method getResult, it will retrieve the user. For each time the method is called, a new user will be built. Hence we can
 * implement immutability of users.
 */
public class UserBuilder {
    private User user;

    /**
     * Constructor. Create the user object.
     */
    public UserBuilder()
    {
        this.user = new User();
    }

    /**
     * It assigns the user a new UUID and a registration date. Then it will be returned.
     * @return
     */
    public User getResult(){
        this.user.setId(UUID.randomUUID().toString());
        this.user.setRegistrationDate(new Date());
        return this.user;
    }

    /**
     * Build a user using his login credentials.
     * @param email
     * @param password
     * @param username
     */
    public void addUserLoginDetails(String email, String password, String username){
        this.user.setPassword(password);
        this.user.setEmail(email);
        this.user.setUsername(username);
    }

    /**
     * Build a user using his names.
     * @param firstName
     * @param lastName
     */
    public void addUserNames(String firstName, String lastName)
    {
        this.user.setFirstName(firstName);
        this.user.setLastName(lastName);
    }

    /**
     * Building a user using some nonmilitant data.
     * @param address
     * @param date
     */
    public void addAddressAndBirthDate(String address, Date date)
    {
        this.user.setAddress(address);
        this.user.setBirthDate(date);
    }

}
