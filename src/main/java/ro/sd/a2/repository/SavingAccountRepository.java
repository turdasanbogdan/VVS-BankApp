package ro.sd.a2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.SavingAccount;

import java.util.List;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Integer> {
    List<SavingAccount> findByUser_id(String id);
}
