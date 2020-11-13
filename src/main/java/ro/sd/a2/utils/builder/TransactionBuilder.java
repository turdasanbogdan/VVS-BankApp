package ro.sd.a2.utils.builder;

import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Transaction;

import java.util.Date;
import java.util.UUID;

/**
 * Class used in order to create transactions.
 */
public class TransactionBuilder {
    /**
     * Builds a new transaction by paying a bill using an account, having also an exchange ratio.
     * @param bill The bill to be paid
     * @param account The account to be used in the transaction
     * @param ratio The exchange ratio. It is 1 if we have no exchange. 1unit = 1unit. 1billUnit = ration AccountUnit.
     * @return The newly created transaction.
     */
    public static Transaction buildTransaction(Bill bill, Account account, float ratio)
    {
        Transaction transaction = new Transaction();
        float convertedSum = bill.getSum() * ratio;
        if(!account.pay(convertedSum).equals("OK")) return null;
        transaction.setAccount(account);
        transaction.setBill(bill);
        transaction.setPaymentDate(new Date());
        transaction.setId(UUID.randomUUID().toString());
        transaction.setSum(convertedSum);
        return transaction;
    }
}
