package ro.sd.a2.utils.factory;

import ro.sd.a2.entity.*;

import java.util.Date;

/**
 * Concrete factory that creates Accounts.
 */
public class AccountFactory implements AbstractFactory<Account> {
    /**
     * Create a new Account
     * @param type The type of account
     * @return The newly created account
     */
    @Override
    public Account create(String type) {
        if(type == null) return null;
        if(type.equals("Spending")){
            return createSpendingAccount();
        }
        if(type.equals("Saving")){
            return createSavingAccount();
        }
        return null;
    }

    /**
     * Create a new Spending account.
     * @return Newly created spending account.
     */
    private Account createSpendingAccount()
    {
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setUser(new User());
        spendingAccount.setValute(new Valute());
        spendingAccount.setCreationDate(new Date());
        spendingAccount.setLastModificationDate(new Date());
        spendingAccount.setSum(0);
        spendingAccount.setType("Spending");
        return spendingAccount;
    }

    /**
     * Create a new Saving account.
     * @return The newly created saving account.
     */
    private Account createSavingAccount()
    {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setUser(new User());
        savingAccount.setValute(new Valute());
        savingAccount.setCreationDate(new Date());
        savingAccount.setLastModificationDate(new Date());
        savingAccount.setSum(0);
        savingAccount.setType("Saving");
        return savingAccount;
    }
}
