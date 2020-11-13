package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.SavingAccount;
import ro.sd.a2.repository.SavingAccountRepository;

import java.util.List;

/**
 * Service for Saving account Entity.
 */
@Service
public class SavingAccountService {
    @Autowired
    private SavingAccountRepository savingAccountRepository;


    public List<SavingAccount> getAll() {
        return savingAccountRepository.findAll();
    }

    public List<SavingAccount> findByUserId(String id) {
        return savingAccountRepository.findByUser_id(id);
    }
}
