package ro.sd.a2.utils.generators;

import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Company;
import ro.sd.a2.entity.User;

import java.util.*;

/**
 * Bill generator class used by the admin when he generates bills for users.
 */
public class BillGenerator {

    /**
     * Generate a list value number of bills for the User user. The companies will be randomly chosen from the companies list.
     * The sum of each bill is random from 1 to 2000. The emission date is today.
     * @param user user to be the bills assigned to
     * @param value the number of bills
     * @param companies the list of possible companies to choose.
     * @return List of newly created bills.
     */
    public static List<Bill> generate(User user, int value, List<Company> companies) {
        if(companies==null || user==null || value<0) return null;
        if(companies.size()==0) return null;
        Random random = new Random();
        List<Bill> bills = new LinkedList<>();
        for(int i=0; i<value; i++)
        {
            Bill bill = new Bill();
            bill.setId(UUID.randomUUID().toString());
            bill.setUser(user);
            bill.setEmissionDate(new Date());
            bill.setSum(1+random.nextInt(2000));
            bill.setCompany(companies.get(random.nextInt(companies.size())));
            bills.add(bill);
        }
        return bills;
    }
}
