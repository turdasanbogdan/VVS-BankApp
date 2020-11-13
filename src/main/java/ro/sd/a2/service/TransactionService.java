package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Transaction;
import ro.sd.a2.entity.User;
import ro.sd.a2.repository.TransactionRepository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for the Transaction entity.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;


    public List<Transaction> findByAccountId(Integer accountId) {
        return transactionRepository.findByAccount_Id(accountId);
    }

    public Transaction insert(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Get all the transaction of a user in the specified interval id.
     * @param startDate start of iterval
     * @param endDate   end of interval
     * @param user  the user whose transactions we want to retrieve
     * @return The list of transactions having the presented conditions.
     */
    public List<Transaction> getFromTimeIntervalAndUser(Date startDate, Date endDate, User user) {
        List<Transaction> transactions = transactionRepository.findByAccount_User_Id(user.getId());
        //List<Transaction> transactions = new LinkedList<>();
        return transactions.stream().filter(e->e.getPaymentDate().after(startDate)&&e.getPaymentDate().before(endDate)).collect(Collectors.toList());
    }
}
