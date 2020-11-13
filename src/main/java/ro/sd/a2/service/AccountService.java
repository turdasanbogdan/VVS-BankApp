package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Account;
import ro.sd.a2.mapper.UserDetailsMapper;
import ro.sd.a2.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The service class for entity Account
 * Contains autowired accountRepository object.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    /**
     * Method to insert a new user account.
     * @param account Account to be inserted. Can't be @null
     * @return @null if the user has too many accounts of one type
     *          the newly created account if everything successful
     */
    public Account insert(Account account) {
        List<Account> accounts = findByUserId(account.getUser().getId());
        long numberOfSameAccounts = accounts.stream()
                .filter(e->e.getType().equals(account.getType()) && e.getValute().getId().equals(account.getValute().getId()) )
                .count();
        if(numberOfSameAccounts < 2) return accountRepository.save(account);
        else return null;
    }

    /**
     *
     * @param accountId The id of the account
     * @return account s.t account.getId.equals(accountId) or null
     */
    public Optional<Account> findById(Integer accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     *
     * @param id The id of the user of the account. @NotNull
     * @return account s.t account.getUser().getId().equals(id)
     */
    public List<Account> findByUserId(String id)
    {
        return accountRepository.findByUser_Id(id);
    }

    /**
     *
     * @param account Updates the accountBase with account provided that accountBase.getId().equals(account.getId()).
     */
    public void update(Account account)
    {
        accountRepository.save(account);
    }

    /**
     *
     * @param accountIban @NotNull. Iban of the searched account
     * @return account s.t. account.getIban().equals(accountIban).
     */
    public Account findByIban(String accountIban) {
        return accountRepository.findByIban(accountIban);
    }
}
