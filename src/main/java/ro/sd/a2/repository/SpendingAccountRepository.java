package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.SavingAccount;
import ro.sd.a2.entity.SpendingAccount;

import java.util.List;

@Repository
public interface SpendingAccountRepository extends JpaRepository<SpendingAccount, Integer> {
    List<SpendingAccount> findByUser_id(String id);
}
