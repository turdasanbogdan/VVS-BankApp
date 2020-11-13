package ro.sd.a2.utils.generators;

import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.User;

import java.util.Calendar;
import java.util.Random;

/**
 * Class used to generate a new account iban.
 */
public class IbanGenerator {

    /**
     * Generate a new correct iban for the account account.
     * @param account The account for which we need to generate the iban.
     * @return The newly generated iban.
     */
    public static String generate(Account account)
    {
        User user = account.getUser();
        Random random = new Random();
        String[] list= {"RO", "EN", "GB", "HU", "IT"};
        StringBuilder stringBuilder = new StringBuilder("HNG");
        if(account.getType().equals("Spending")) stringBuilder.append("#");
        else if(account.getType().equals("Saving")) stringBuilder.append("*");
        stringBuilder.append(list[random.nextInt(5)]);
        for(int i=0; i<5; i++) stringBuilder.append(random.nextInt(10));
        stringBuilder.append(user.getFirstName().charAt(0));
        stringBuilder.append(user.getFirstName().charAt(1));
        stringBuilder.append(user.getLastName().charAt(user.getLastName().length()-2));
        stringBuilder.append(user.getLastName().charAt(user.getLastName().length()-1));
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        int minute = rightNow.get(Calendar.MINUTE);
        if(hour+minute < 10) stringBuilder.append("0").append((hour+minute));
        else stringBuilder.append((hour+minute));
        stringBuilder.append(account.getValute().getSymbol());
        for(int i=0; i<6; i++) stringBuilder.append(random.nextInt(10));
        stringBuilder.append(user.getAddress().charAt(0));
        stringBuilder.append(user.getAddress().charAt(1));

        return stringBuilder.toString().toUpperCase();
    }
}
