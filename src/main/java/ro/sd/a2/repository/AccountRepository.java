package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Account;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUser_Id(String id);
    Account findByIban(String iban);
}
