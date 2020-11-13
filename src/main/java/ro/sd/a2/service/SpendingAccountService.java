package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.SpendingAccount;
import ro.sd.a2.repository.SpendingAccountRepository;

import java.util.List;

/**
 * Service for spending account entity.
 */
@Service
public class SpendingAccountService {

    @Autowired
    private SpendingAccountRepository spendingAccountRepository;

    public List<SpendingAccount> getAll() {
        return spendingAccountRepository.findAll();
    }

    public List<SpendingAccount> findByUserId(String id) {
        return spendingAccountRepository.findByUser_id(id);
    }
}
